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
@Table(name="music")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long musicId;

    @Column(name = "spotifyId")
    private String spotifyId;

    @Column(name = "title")
    private String title;

    @Column(name = "artist")
    private String artist;

    @Column(name = "release_date")
    private String release_date;

    @Column(name = "duration")
    private long duration;

    @Column(name = "preview_url")
    private String preview_url;

    @Column(nullable=false, unique=false, name = "image_url")
    private String image_url;

    /*@ManyToOne
    @JoinColumn(name = "album_id", referencedColumnName = "album_id")
    private Album album;*/

    /*@ManyToOne(optional = true)
    @JoinColumn(name = "playlist_id", referencedColumnName = "playlist_id")*/
    @Column(name = "playlist_id")
    private Long playlist;

    @Column(name = "user_id")
    private Long userId;

    /*@ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;*/

    @Override
    public String toString() {
        return "Music{" +
                "musicId=" + musicId +
                ", spotifyId='" + spotifyId + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", release_date='" + release_date + '\'' +
                ", duration=" + duration +
                ", preview_url='" + preview_url + '\'' +
                ", image_url='" + image_url + '\'' +
                ", playlist=" + playlist +
                ", userId=" + userId +
                '}';
    }
}
