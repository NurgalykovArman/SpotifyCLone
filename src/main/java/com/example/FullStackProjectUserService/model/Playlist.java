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
@Table(name="playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Long playlistId;

    @Column(nullable=false, unique=false, name = "title")
    private String title;

    @Column(nullable=true, unique=false, name = "image_url")
    private String image_url;

    /*@ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;*/

    @Override
    public String toString() {
        return "Playlist{" +
                "playlistId=" + playlistId +
                '}';
    }
}
