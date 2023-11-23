package com.example.FullStackProjectUserService.repository;


import com.example.FullStackProjectUserService.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("SELECT a FROM Album a WHERE a.spotifyId = :spotifyId ORDER BY a.albumId ASC")
    Album findBySpotify_idOrderByAlbumIdAlbumIdDesc(@Param("spotifyId") String spotifyId);

    boolean existsBySpotifyId(String spotifyId);
}
