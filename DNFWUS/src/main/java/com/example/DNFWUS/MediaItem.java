package com.example.DNFWUS;

import jakarta.persistence.*;

@Entity
public class MediaItem {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String genre;

    @Enumerated(EnumType.STRING) // Говорит JPA хранить Enum как строку ("MOVIE"), а не как число (1)
    private MediaCategory category;

    @Enumerated(EnumType.STRING)
    private WatchStatus status;

    // --- Обязательные части для JPA и JSON ---

    // 1. Пустой конструктор для JPA
    public MediaItem() {}

    // 2. Геттеры и Сеттеры для всех полей (для JPA и JSON)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public MediaCategory getCategory() {
        return category;
    }

    public void setCategory(MediaCategory category) {
        this.category = category;
    }

    public WatchStatus getStatus() {
        return status;
    }

    public void setStatus(WatchStatus status) {
        this.status = status;
    }
}
