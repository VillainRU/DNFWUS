package com.example.DNFWUS;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// 1. Говорим Spring, что это Контроллер для REST API.
// Он будет принимать HTTP-запросы и возвращать JSON.
@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor// 2. Базовый URL для всех методов в этом классе
public class MediaItemController {

    // 3. Нам нужен "мозг", поэтому мы просим его у Spring.
    private final MediaItemService mediaService;
    private final MediaItemMapper mapper;

    // 4. Spring "внедряет" (inject) сервис через конструктор.


    // --- Эндпоинты (Endpoints) ---

    // CREATE (Создание)
    // 5. Обрабатывает HTTP POST запросы на /api/media
    @PostMapping
    public MediaItemDto createItem(@RequestBody MediaItemDto dto) {
        // 6. @RequestBody берет JSON из тела запроса и превращается его в объект MediaItem
        // 1. DTO -> Entity
        MediaItem entity = mapper.toEntity(dto);

        // 2. Вызываем сервис
        MediaItem createdEntity = mediaService.createItem(entity);

        // 3. Entity -> DTO
        return mapper.toDto(createdEntity);
    }

    // READ ALL (Получение всех)
    // 7. Обрабатывает HTTP GET запросы на /api/media
    @GetMapping
    public List<MediaItemDto> getAllItems() {
        return mediaService.getAllItems().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    // READ (Получение одного по ID)
    // Обрабатывает GET запросы на /api/media/1, /api/media/2 и т.д.
    @GetMapping("/{id}")
    public MediaItemDto getItemById (@PathVariable Long id) {
        // 1. @PathVariable берет "id" из URL (например, "1")
        // и передает его в метод.
        MediaItem entity = mediaService.getItemById(id);
        return mapper.toDto(entity);
    }

    // UPDATE (Обновление)
    // Обрабатывает PUT запросы на /api/media/1
    @PutMapping("/{id}")
    public MediaItemDto updateItem (@PathVariable Long id, @RequestBody MediaItemDto dto) {
        // Мы передаем и ID и URL, и JSON из тела запроса в наш сервис.
        // Превращаем входящий JSON в сущность
        MediaItem entityDetails = mapper.toEntity(dto);

        // Обновляем
        MediaItem updatedEntity = mediaService.updateItem(id, entityDetails);

        // Возвращаем DTO
        return mapper.toDto(updatedEntity);
    }

    // DELETE (Удаление)
    // Обрабатывает DELETE запросы на /api/media/1
    @DeleteMapping("/{id}")
    public void deleteItem (@PathVariable Long id) {
        mediaService.deleteItem(id);
        // При удалении мы обычно ничего не возвращаем (статус 200 ОК)
    }
}
