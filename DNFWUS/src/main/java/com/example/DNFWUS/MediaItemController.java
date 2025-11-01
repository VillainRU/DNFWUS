package com.example.DNFWUS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
