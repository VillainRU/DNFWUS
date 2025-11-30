package com.example.DNFWUS.service;

import com.example.DNFWUS.entity.MediaItem;
import com.example.DNFWUS.exception.ResourceNotFoundException;
import com.example.DNFWUS.repository.MediaItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

// 1. Говорим Spring, что это "Сервис - компонент с бизнес-логикой.
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MediaItemService {

    // 2. Нам нужен "библиотекарь", поэтому мы просим его у Spring.
    private final MediaItemRepository repository;

    // 3. Spring "внедряет" (inject) репозиторий через конструктор.
    // Это и есть Dependency Injection.

    // --- Методы бизнес-логики (наш CRUD) ---

    // CREATE (Создание)
    @Transactional
    public MediaItem createItem (MediaItem item) {
        // Мы просто передаем "книгу" "библиотекарю", что бы он ее сохранил.
        log.info("Creating new media item: {}", item.getTitle());
        return repository.save(item);
    }

    // READ (Получение всех)
    public List<MediaItem> getAllItems() {
        // Просим у "библиотекаря" все "книги".
        log.info("Requesting all items");
        return repository.findAll();
    }

    // READ (Получение одного по ID)
    public MediaItem getItemById (Long id) {
        // findById возвращает "Optional", который может быть пустым.
        // .orElseThrow() - это элегантный способ вернуть ошибку,
        // если "книга" с таким ID не найдена.
        log.info("Searching for item with id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
    }

    // UPDATE (Обновление)
    @Transactional
    public MediaItem updateItem (Long id, MediaItem itemDetails) {
        // 1. Находим существующий объект
        MediaItem existingItem = getItemById(id); // Мы используем наш же метод!

        // 2. Обновляем его поля данными из запроса
        existingItem.setTitle(itemDetails.getTitle());
        existingItem.setGenre(itemDetails.getGenre());
        existingItem.setCategory(itemDetails.getCategory());
        existingItem.setStatus(itemDetails.getStatus());

        // 3. Сохраняем обновленный объект.
        // Метод save() работает и для создания, и для обновления.
        return repository.save(existingItem);
    }

    // DELETE (Удаление)
    @Transactional
    public void deleteItem (Long id) {
        // 1. Проверяем, существует ли запись, что бы не получить ошибку
        log.info("Deleting item with id: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Item not found with id: " + id);
        }
        // 2. Удаляем
        repository.deleteById(id);
    }
}
