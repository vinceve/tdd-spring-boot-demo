package be.rombit.tdd.tabs.integrations;

import be.rombit.tdd.tabs.DataFactory;
import be.rombit.tdd.tabs.models.entities.Artist;
import be.rombit.tdd.tabs.models.repositories.ArtistRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArtistResourceTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ArtistRepository artistRepository;

    @AfterEach
    public void cleanupDatabase() {
        this.artistRepository.deleteAll();
    }

    @Test
    public void listReturnsA200Status() {
        ParameterizedTypeReference<List<Artist>> artistTypeDefinition = new ParameterizedTypeReference<List<Artist>>() {};
        ResponseEntity<List<Artist>> response = this.restTemplate.exchange("/artists", HttpMethod.GET, null, artistTypeDefinition);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void listReturnsArtists() {
        Artist artist = new DataFactory().getArtist();
        this.artistRepository.save(artist);

        ParameterizedTypeReference<List<Artist>> artistTypeDefinition = new ParameterizedTypeReference<List<Artist>>() {};
        ResponseEntity<List<Artist>> response = this.restTemplate.exchange("/artists", HttpMethod.GET, null, artistTypeDefinition);

        assertEquals(1, response.getBody().size());
    }

    @Test
    public void createReturnsA201Status() {
        Artist artist = new DataFactory().getArtist();
        ResponseEntity<Artist> response = this.restTemplate.postForEntity("/artists", artist, Artist.class);

        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    public void createSavesTheArtist() {
        Artist artist = new DataFactory().getArtist();
        this.restTemplate.postForEntity("/artists", artist, Artist.class);

        Artist savedArtist = this.artistRepository.findAll().get(0);

        assertEquals(artist.getName(), savedArtist.getName());
    }

    @Test
    public void createReturnsA422StatusOnValidationErrors() {
        Artist artist = new DataFactory().getArtist();
        artist.setName(null);

        ResponseEntity<Artist> response = this.restTemplate.postForEntity("/artists", artist, Artist.class);

        assertEquals(422, response.getStatusCode().value());
    }
}
