package com.example.FullStackProjectUserService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long albumId;

    @Column(nullable=false, unique=true, name = "spotifyId")
    private String spotifyId;

    @Column(nullable=false, unique=false, name = "title")
    private String title;

    @Column(nullable=true, unique=false, name = "artist")
    private String artist;

    @Column(nullable=false, unique=false, name = "release_date")
    private String release_date;

    @Column(nullable=false, unique=false, name = "image_url")
    private String image_url;

    public Album(String title) {
        this.title = title;
    }
}
