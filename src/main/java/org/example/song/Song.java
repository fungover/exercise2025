package org.example.song;

import jakarta.persistence.*;
import org.example.artist.Artist;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }


    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
