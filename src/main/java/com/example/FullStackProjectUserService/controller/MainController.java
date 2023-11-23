package com.example.FullStackProjectUserService.controller;


import com.example.FullStackProjectUserService.model.Album;
import com.example.FullStackProjectUserService.model.Music;
import com.example.FullStackProjectUserService.model.Playlist;
import com.example.FullStackProjectUserService.repository.AlbumRepository;
import com.example.FullStackProjectUserService.repository.MusicReposirory;
import com.example.FullStackProjectUserService.repository.PlaylistReposirory;
import com.example.FullStackProjectUserService.service.SpotyfyAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    MusicReposirory musicRepository;

    private static Logger log = Logger.getAnonymousLogger();

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/saveMusicHistory")
    public ResponseEntity<String> addMusic(@RequestBody Music musicRequest) {
        try {
            boolean isExist = musicRepository.existsBySpotifyIdAndUserId(musicRequest.getSpotifyId(), musicRequest.getUserId());
            if (isExist) {
                Music music = musicRepository.findBySpotifyId(musicRequest.getSpotifyId());
                musicRepository.delete(music);
                musicRepository.save(music);
            } else musicRepository.save(musicRequest);
            log.info("New Music added in DB: " + musicRequest.getTitle());


            return ResponseEntity.status(HttpStatus.CREATED).body("Music added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add music");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getMusic")
    public ResponseEntity<List<Music>> getMusic(@RequestParam("spotify_id") String spotifyId, @RequestParam("user_id") long userId) {
        List<Music> musicList = new ArrayList<>();

        System.out.println(spotifyId);
        try {
            Music music = musicRepository.findBySpotifyIdAndUserId(spotifyId, userId);
            System.out.println(music.toString());
            if (music != null) {
                musicList.add(music);
                System.out.println(musicList.get(0).toString());
                return ResponseEntity.ok().body(musicList);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/updateLikeStatus")
    public ResponseEntity<String> updateLikeStatus(@RequestParam("spotify_id") String spotifyId, @RequestParam("user_id") long userId) {
        Music music = musicRepository.findBySpotifyIdAndUserId(spotifyId, userId);
        if (music != null) {
            musicRepository.changeLikeStatus(spotifyId, userId);
            return ResponseEntity.ok("Like status updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getListened")
    public ResponseEntity<List<Music>> getListened(@RequestParam long userId){
        List<Music> musicList = new ArrayList<>();
        musicList = musicRepository.findByUserId(userId);

        return ResponseEntity.ok().body(musicList);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getLiked")
    public ResponseEntity<List<Music>> getLiked(@RequestParam long userId){
        List<Music> musicList = new ArrayList<>();
        System.out.println(userId);
        musicList = musicRepository.findByUserIdAndPlaylist(userId);
        System.out.println(musicList.get(0).toString());
        return ResponseEntity.ok().body(musicList);
    }



}
