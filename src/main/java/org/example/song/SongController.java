package org.example.song;

import org.example.artist.Artist;
import org.example.artist.ArtistRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongController(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    @GetMapping
    public List<Song> getAll() {
        return songRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Song create(
            @RequestParam Long artistId,
            @RequestBody Song song
    ) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow();

        song.setArtist(artist);
        return songRepository.save(song);
    }
}

