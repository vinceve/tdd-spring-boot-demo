package be.rombit.tdd.tabs.services;

import be.rombit.tdd.tabs.DataFactory;
import be.rombit.tdd.tabs.models.entities.Artist;
import be.rombit.tdd.tabs.models.repositories.ArtistRepository;
import be.rombit.tdd.tabs.services.exceptions.BusinessValidationException;
import be.rombit.tdd.tabs.services.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @Transactional
    public void updateSavesAnUpdatedArtistToTheDb() {
        Artist artist = new DataFactory().getArtist();
        artist = this.artistRepository.save(artist);

        Artist newArtist = new Artist();
        newArtist.setName("Mob Barley");
        newArtist.setStartedAt(1973);

        this.artistService.update(artist.getId(), newArtist);

        artist = this.artistRepository.findById(artist.getId()).get();

        assertEquals("Mob Barley", artist.getName());
    }

    @Test
    @Transactional
    public void updateCopiesThePropertiesCorrectly() {
        // NOTE: For the sake of the deep dive we will keep this simple and test this here. In real life situations
        // we would use a mapper class and test that one out.

        Artist artist = new DataFactory().getArtist();
        artist = this.artistRepository.save(artist);

        Artist newArtist = new Artist();
        newArtist.setName("Mob Barley");
        newArtist.setStartedAt(1900);

        this.artistService.update(artist.getId(), newArtist);

        artist = this.artistRepository.findById(artist.getId()).get();

        assertEquals("Mob Barley", artist.getName());
        assertEquals(1900, artist.getStartedAt());
    }

    @Test
    @Transactional
    public void updateReturnsAnErrorWhenTheArtistHasNotBeenFound() {
        Artist newArtist = new Artist();
        newArtist.setName("Mob Barley");

        assertThrows(NotFoundException.class, () -> {
            this.artistService.update(-1, newArtist);
        });
    }

    @Test
    @Transactional
    public void updateReturnsAListOfErrors() {
        Artist artist = new DataFactory().getArtist();
        artist = this.artistRepository.save(artist);

        Artist newArtist = new Artist();
        newArtist.setName(null);

        Artist finalArtist = artist;
        assertThrows(BusinessValidationException.class, () -> {
            this.artistService.update(finalArtist.getId(), newArtist);
        });
    }

    @Test
    @Transactional
    public void deleteRemovesAnArtistFromTheDb() {
        Artist artist = new DataFactory().getArtist();
        artist = this.artistRepository.save(artist);

        this.artistService.delete(artist.getId());

        Optional<Artist> deletedArtist = this.artistRepository.findById(artist.getId());

        assertFalse(deletedArtist.isPresent());
    }

    @Test
    @Transactional
    public void deleteReturnsAnErrorWhenTheArtistHasNotBeenFound() {
        assertThrows(NotFoundException.class, () -> {
            this.artistService.delete(-1);
        });
    }
}
