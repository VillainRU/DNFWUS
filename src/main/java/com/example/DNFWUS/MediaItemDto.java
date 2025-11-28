package com.example.DNFWUS;

import com.example.DNFWUS.MediaCategory;
import com.example.DNFWUS.WatchStatus;
import lombok.Data; // Генерирует Геттеры, Сеттеры, toString и т.д.
import jakarta.validation.constraints.NotBlank; // Важный импорт
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data // Самая популярная аннотация Lombok для DTO
public class MediaItemDto {
    private Long id;
    @NotBlank(message = "Название не может быть пустым") // Не null и не "" и не "   "
    @Size(min = 2, max = 100, message = "Название должно быть от 2 до 100 символов")

    private String title;

    @NotBlank(message = "Жанр обязателен")
    private String genre;

    @NotNull(message = "Категория обязательна") // Для Enum и объектов используем @NotNull
    private MediaCategory category;

    @NotNull(message = "Статус обязателен")
    private WatchStatus status;
}