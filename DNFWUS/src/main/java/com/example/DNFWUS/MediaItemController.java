package com.example.DNFWUS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

// 1. Говорим Spring, что это Контроллер для REST API.
// Он будет принимать HTTP-запросы и возвращать JSON.
@RestController
@RequestMapping("/api/media") // 2. Базовый URL для всех методов в этом классе
public class MediaItemController {

    // 3. Нам нужен "мозг", поэтому мы просим его у Spring.
    private final MediaItemService mediaService;

    // 4. Spring "внедряет" (inject) сервис через конструктор.
    @Autowired
    public MediaItemController (MediaItemService mediaService) {
        this.mediaService = mediaService;
    }

    // --- Эндпоинты (Endpoints) ---

    // CREATE (Создание)
    // 5. Обрабатывает HTTP POST запросы на /api/media
    @PostMapping
    public MediaItem createItem(@RequestBody MediaItem item) {
        // 6. @RequestBody берет JSON из тела запроса и превращается его в объект MediaItem
        return mediaService.createItem(item);
    }

    // READ (Получение всех)
    // 7. Обрабатывает HTTP GET запросы на /api/media
    @GetMapping
    public List<MediaItem> getAllItems() {
        return mediaService.getAllItems();
    }

    // READ (Получение одного по ID)
    // Обрабатывает GET запросы на /api/media/1, /api/media/2 и т.д.
    @GetMapping("/{id}")
    public MediaItem getItemById (@PathVariable Long id) {
        // 1. @PathVariable берет "id" из URL (например, "1")
        // и передает его в метод.
        return mediaService.getItemById(id);
    }

    // UPDATE (Обновление)
    // Обрабатывает PUT запросы на /api/media/1
    @PutMapping("/{id}")
    public MediaItem updateItem (@PathVariable Long id, @RequestBody MediaItem itemDetails) {
        // Мы передаем и ID и URL, и JSON из тела запроса в наш сервис.
        return mediaService.updateItem(id, itemDetails);
    }

    // DELETE (Удаление)
    // Обрабатывает DELETE запросы на /api/media/1
    @DeleteMapping("/{id}")
    public void deleteItem (@PathVariable Long id) {
        mediaService.deleteItem(id);
        // При удалении мы обычно ничего не возвращаем (статус 200 ОК)
    }
}
