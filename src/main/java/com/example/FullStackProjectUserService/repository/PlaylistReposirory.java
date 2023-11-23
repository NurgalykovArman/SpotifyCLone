package com.example.FullStackProjectUserService.repository;


import com.example.FullStackProjectUserService.model.Playlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PlaylistReposirory extends JpaRepository<Playlist, Long> {


}
