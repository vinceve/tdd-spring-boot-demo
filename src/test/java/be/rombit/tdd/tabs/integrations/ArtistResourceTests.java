package be.rombit.tdd.tabs.integrations;

import be.rombit.tdd.tabs.DataFactory;
import be.rombit.tdd.tabs.models.entities.Artist;
import be.rombit.tdd.tabs.models.repositories.ArtistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArtistResourceTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    @Transactional
    public void listReturnsA200Status() {
        ParameterizedTypeReference<List<Artist>> artistTypeDefinition = new ParameterizedTypeReference<List<Artist>>() {};
        ResponseEntity<List<Artist>> response = this.restTemplate.exchange("/artists", HttpMethod.GET, null, artistTypeDefinition);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @Transactional
    public void listReturnsArtists() {
        Artist artist = new DataFactory().getArtist();
        this.artistRepository.save(artist);

        ParameterizedTypeReference<List<Artist>> artistTypeDefinition = new ParameterizedTypeReference<List<Artist>>() {};
        ResponseEntity<List<Artist>> response = this.restTemplate.exchange("/artists", HttpMethod.GET, null, artistTypeDefinition);

        assertEquals(1, response.getBody().size());
    }
}
