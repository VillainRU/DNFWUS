package com.example.DNFWUS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.Media;
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

    // READ (Получение одного по ID)
    public MediaItem getItemById (Long id) {
        // findById возвращает "Optional", который может быть пустым.
        // .orElseThrow() - это элегантный способ вернуть ошибку,
        // если "книга" с таким ID не найдена.
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
    }

    // UPDATE (Обновление)
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
    public void deleteItem (Long id) {
        // 1. Проверяем, существует ли запись, что бы не получить ошибку
        if (!repository.existsById(id)) {
            throw new RuntimeException("Item not found with id: " + id);
        }
        // 2. Удаляем
        repository.deleteById(id);
    }
}
