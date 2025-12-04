package com.example.DNFWUS.bot;

import com.example.DNFWUS.entity.MediaItem;
import com.example.DNFWUS.enums.MediaCategory;
import com.example.DNFWUS.service.MediaItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.DNFWUS.enums.MediaCategory.*;
import static org.apache.commons.lang3.stream.LangCollectors.collect;

@Component // 1. Это бин, Spring должен его создать
@Slf4j
public class MediaTelegramBot extends TelegramLongPollingBot {

    private final MediaItemService mediaService;
    private final String botName;

    public MediaTelegramBot(MediaItemService mediaService,
                            @Value("${bot.name}") String botName,
                            @Value("${bot.token}") String botToken) {
        super(botToken);
        this.mediaService = mediaService;
        this.botName = botName;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    // Главный метод: Сюда приходят все сообщения
    @Override
    public void onUpdateReceived(Update update) {
        log.info("Получено что-то от телеграма...");
        // Проверка сообщения, есть ли в нем текст
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            log.info("Текст сообщения: {}", messageText);

            long chatId = update.getMessage().getChatId();
            String[] parts = messageText.split(" ", 2);
            String command = parts[0];

            switch (command) {
                case "/start":
                    startCommand(chatId);
                    break;
                case "/all":
                    getAllMedia(chatId);
                    break;
                case "/id":
                    if (parts.length > 1) {
                        String argument = parts[1];
                        processFindById(chatId, argument);
                    } else {
                        sendMessage(chatId, "Пожалуйста, укажите ID через пробел.\nПример: /id 5");
                    }
                    break;
                default:
                    sendMessage(chatId, "Я не знаю такой команды :( Попробуй /all");
            }
        }
    }

    // --- Логика команд ---

    private void startCommand(long chatId) {
        String text = "Привет! Я помогу тебе не забыть, что посмотреть и что ты посмотрел, " +
                "во что поиграть и что ты уже прошел из игр.\n" +
                "Напиши /all чтобы увидеть весь список или /id что бы увидеть контент по id.";
        sendMessage(chatId, text);
    }

    private void processFindById(long chatId, String idString) {
        try {
            Long id = Long.parseLong(idString);
            MediaItem item = mediaService.getItemById(id);

            String emoji = getCategoryEmoji(item.getCategory());

            String responseText = String.format("%s %s | %s (%s)",
                                emoji,              // 1. Смайлик
                                item.getTitle(),    // 2. Название
                                item.getGenre(),    // 3. Жанр
                                item.getStatus());  // 4. Статус

            sendMessage(chatId, responseText);

        } catch (NumberFormatException e) {
            sendMessage(chatId, "ID должен быть числом!");
        } catch (RuntimeException e) {
            sendMessage(chatId, "Запись с таким ID не найдена.");
        }
    }

    private void getAllMedia(long chatId) {
        // 1. Обращаемся к Сервису (как и Контроллер)
        List<MediaItem> items = mediaService.getAllItems();

        if (items.isEmpty()) {
            sendMessage(chatId, "Список пуст! Добавь что нибудь.");
            return;
        }

        // 2. Превращаем список в красивую строку
        String responseText = items.stream()
                .map(item -> {
                    String emoji = getCategoryEmoji(item.getCategory());

                    // Было: String.format("%s %s (%s)", item.getTitle()...
                    // СТАЛО: Добавили %s в начале и переменную emoji
                    return String.format("%s %s | %s (%s)",
                            emoji,              // 1. Смайлик
                            item.getTitle(),    // 2. Название
                            item.getGenre(),    // 3. Жанр
                            item.getStatus());  // 4. Статус
                })
                .collect(Collectors.joining("\n"));

        // 3. Отправляем
        sendMessage(chatId, responseText);

    }

    private void formattedMessage(String text) {}
    // --- Метод отправки сообщений ---
    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error ocurred: " + e.getMessage());
        }

    }

    private String getCategoryEmoji(MediaCategory category) {
        if (category == null) return "📁";
        switch (category) {
            case MOVIE: return "🎬";
            case SERIAL: return "📺";
            case GAME: return "🎮";
            case ANIME: return "⛩️";
            default: return "📁";
        }
    }
}
