package com.example.DNFWUS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 1. Говорим Spring, что это "Сервис - компонент с бизнес-логикой.
@Service
public class MediaItemService {

    // 2. Нам нужен "библиотекарь", поэтому мы просим его у Spring.
    private final MediaItemRepository repository;

    // 3. Spring "внедряет" (inject) репозиторий через конструктор.
    // Это и есть Dependency Injection.
    @Autowired
    public MediaItemService (MediaItemRepository repository) {
        this.repository = repository;
    }

    // --- Методы бизнес-логики (наш CRUD) ---

    // CREATE (Создание)
    public  MediaItem createItem (MediaItem item) {
        // Мы просто передаем "книгу" "библиотекарю", что бы он ее сохранил.
        return repository.save(item);
    }

    // READ (Получение всех)
    public List<MediaItem> getAllItems() {
        // Просим у "библиотекаря" все "книги".
        return repository.findAll();
    }
}
