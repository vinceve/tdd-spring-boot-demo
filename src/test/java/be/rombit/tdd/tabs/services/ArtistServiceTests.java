package be.rombit.tdd.tabs.services;

import be.rombit.tdd.tabs.DataFactory;
import be.rombit.tdd.tabs.models.entities.Artist;
import be.rombit.tdd.tabs.models.repositories.ArtistRepository;
import be.rombit.tdd.tabs.services.exceptions.BusinessValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class ArtistServiceTests {
    @Autowired
    private ArtistService artistService;
    @Autowired
    private ArtistRepository artistRepository;

    @Test
    @Transactional
    public void listArtists() {
        Artist artist = new DataFactory().getArtist();
        this.artistRepository.save(artist);

        List<Artist> artists = this.artistService.findAll();

        assertEquals(1, artists.size());
    }

    @Test
    @Transactional
    public void createSavesTheArtistToTheDb() {
        Artist artist = new DataFactory().getArtist();

        this.artistService.create(artist);
        Artist savedArtist = this.artistRepository.findAll().get(0);

        assertEquals(artist.getName(), savedArtist.getName());
    }

    @Test
    @Transactional
    public void createReturnsAListOfErrors() {
        Artist artist = new DataFactory().getArtist();
        artist.setName(null);

        assertThrows(BusinessValidationException.class, () -> {
            this.artistService.create(artist);
        });
    }
}
