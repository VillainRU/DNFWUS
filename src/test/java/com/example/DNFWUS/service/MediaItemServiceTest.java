package com.example.DNFWUS.service;

import com.example.DNFWUS.entity.MediaItem;
import com.example.DNFWUS.exception.ResourceNotFoundException;
import com.example.DNFWUS.repository.MediaItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.print.attribute.standard.Media;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 1. Включаем Mockito
public class MediaItemServiceTest {

    @Mock // 2. Создаем "дублера" репозитория
    private MediaItemRepository repository;

    @InjectMocks // 3. Вставляем дублера в настоящий сервис
    private MediaItemService service;

    @Test
    void createItem_ShouldSaveAndReturnItem() {
        // --- ARRANGE (Подготовка) ---
        // Создаем тестовые данные
        MediaItem inputItem = new MediaItem();
        inputItem.setTitle("Test Movie");

        MediaItem savedItem = new MediaItem();
        savedItem.setId(1L);
        savedItem.setTitle("Test Movie");

        // Учим дублера: "Если кто-то вызовет save(), верни savedItem"
        when(repository.save(any(MediaItem.class))).thenReturn(savedItem);

        // --- ACT (Действие) ---
        // Вызываем реальный метод сервиса
        MediaItem result = service.createItem(inputItem);

        // --- ASSERT (Проверка) ---
        // Проверяем, что результат не null и ID равен 1
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Movie", result.getTitle());

        // Проверяем, что сервис действительно вызывал репозиторий
        verify(repository).save(any(MediaItem.class));
    }

    @Test
    void getItemById_WhenNotFound_ShouldThrowException() {
        // --- ARRANGE (Подготовка) ---
        Long nonExistendId = 999L;

        // Учим дублера: "Если спросят ID 999, верни ПУСТОТУ (Optional.empty)"
        when(repository.findById(nonExistendId)).thenReturn(Optional.empty());

        // --- ACT & ASSERT (Действие и Проверка) ---
        // Мы используем assertThrows. Он говорит:
        // "Я ожидаю, что следующий код взорвется с ошибкой ResourceNotFoundException.
        // Если код выполнится без ошибок - тест провален!"
        assertThrows(ResourceNotFoundException.class, () -> service.getItemById(nonExistendId));

        // Убеждаемся, что сервис действительно пытался искать
        verify(repository).findById(nonExistendId);

    }
}
