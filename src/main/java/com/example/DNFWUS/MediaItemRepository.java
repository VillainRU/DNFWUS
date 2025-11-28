package com.example.DNFWUS;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaItemRepository extends JpaRepository<MediaItem, Long> {
}
