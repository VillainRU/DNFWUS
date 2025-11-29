package com.example.DNFWUS.repository;

import com.example.DNFWUS.entity.MediaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaItemRepository extends JpaRepository<MediaItem, Long> {
}
