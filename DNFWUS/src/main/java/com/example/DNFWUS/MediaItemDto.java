package com.example.DNFWUS;

import com.example.DNFWUS.MediaCategory;
import com.example.DNFWUS.WatchStatus;
import lombok.Data; // Генерирует Геттеры, Сеттеры, toString и т.д.

@Data // Самая популярная аннотация Lombok для DTO
public class MediaItemDto {
    private Long id;
    private String title;
    private String genre;
    private MediaCategory category;
    private WatchStatus status;
}