package com.example.DNFWUS.entity;

import com.example.DNFWUS.enums.MediaCategory;
import com.example.DNFWUS.enums.WatchStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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


}
