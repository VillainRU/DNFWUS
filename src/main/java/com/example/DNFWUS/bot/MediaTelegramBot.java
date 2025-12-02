package com.example.DNFWUS.bot;

import com.example.DNFWUS.entity.MediaItem;
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

            switch (messageText) {
                case "/start":
                    startCommand(chatId);
                    break;
                case "/all":
                    getAllMedia(chatId);
                    break;
                default:
                    sendMessage(chatId, "Я не знаю такой команды :( Попробуй /all");
            }
        }
    }

    // --- Логика команд ---

    private void startCommand(long chatId) {
        String text = "Привет! Я помогу тебе не забыть, что посмотреть и что ты посмотрел.\n" +
                "Напиши /all чтобы увидеть список.";
        sendMessage(chatId, text);
    }

    private void getAllMedia(long chatId) {
        // 1. Обращаемся к Сервису (как и Контроллер)
        List<MediaItem> items = mediaService.getAllItems();

        if (items.isEmpty()) {
            sendMessage(chatId, "Список пуст! Добавь что нибудь.");
            return;
        }

        // 2. Превращаем список в красивую строку
        String responceText = items.stream()
                .map(item -> String.format("\uD83D\uDCFA %s | %s (%s)", item.getTitle()
                ,item.getGenre(), item.getStatus()))
                                .collect(Collectors.joining("\n"));

        // 3. Отправляем
        sendMessage(chatId, responceText);

    }

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
}
