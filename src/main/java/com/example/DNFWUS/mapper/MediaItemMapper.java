package com.example.DNFWUS.mapper;

import com.example.DNFWUS.dto.MediaItemDto;
import com.example.DNFWUS.entity.MediaItem;
import org.springframework.stereotype.Component;

@Component // Делаем его бином, чтобы можно было внедрить в Контроллер
public class MediaItemMapper {

    // Из Сущности в DTO (для ответа клиенту)
    public MediaItemDto toDto(MediaItem entity) {
        MediaItemDto dto = new MediaItemDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setGenre(entity.getGenre());
        dto.setCategory(entity.getCategory());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    // Из DTO в Сущность (для сохранения в БД)
    public MediaItem toEntity(MediaItemDto dto) {
        MediaItem entity = new MediaItem();
        // ID мы обычно не маппим при создании, но для обновления он может пригодиться.
        // Пока оставим пустым или заполним, если нужно.
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setGenre(dto.getGenre());
        entity.setCategory(dto.getCategory());
        entity.setStatus(dto.getStatus());
        return entity;
    }
}