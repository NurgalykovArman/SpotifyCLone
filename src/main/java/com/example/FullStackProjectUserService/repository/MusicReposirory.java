package com.example.FullStackProjectUserService.repository;

import com.example.FullStackProjectUserService.model.Music;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface MusicReposirory extends JpaRepository<Music, Long> {
    @Query("SELECT m FROM Music m WHERE m.spotifyId = :spotifyId")
    Music findBySpotifyId(@Param("spotifyId") String spotifyId);

    @Query("SELECT m FROM Music m WHERE m.spotifyId = :spotifyId AND m.userId = :userId")
    Music findBySpotifyIdAndUserId(@Param("spotifyId") String spotifyId, @Param("userId") long userId);
    @Query("SELECT m FROM Music m WHERE m.userId = :userId")
    List<Music> findByUserId(@Param("userId") long userId);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Music m WHERE m.spotifyId = :spotifyId AND m.userId = :userId")
    boolean existsBySpotifyIdAndUserId(@Param("spotifyId") String spotifyId, @Param("userId") Long userId);
    @Transactional
    @Modifying
    @Query("UPDATE Music m SET m.playlist = CASE WHEN m.playlist IS NULL THEN 1 ELSE NULL END WHERE m.spotifyId = :spotifyId AND m.userId = :userId")
    void changeLikeStatus(@Param("spotifyId") String spotifyId, @Param("userId") Long userId);

    @Query("SELECT m FROM Music m WHERE m.userId = :userId AND m.playlist = 1")
    List<Music> findByUserIdAndPlaylist(@Param("userId") long userId);
}
