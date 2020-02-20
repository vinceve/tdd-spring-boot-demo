package be.rombit.tdd.tabs.controllers;

import be.rombit.tdd.tabs.models.entities.Artist;
import be.rombit.tdd.tabs.services.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<List<Artist>> list() {
        List<Artist> artists = this.artistService.findAll();
        return ResponseEntity.ok(artists);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Artist artist) {
        this.artistService.create(artist);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
