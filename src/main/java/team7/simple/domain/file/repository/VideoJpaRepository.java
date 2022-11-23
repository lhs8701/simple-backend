package team7.simple.domain.file.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.file.entity.Video;

import java.util.Optional;

public interface VideoJpaRepository extends JpaRepository<Video, Long> {
    Optional<Video> findByFileName(String fileName);
}
