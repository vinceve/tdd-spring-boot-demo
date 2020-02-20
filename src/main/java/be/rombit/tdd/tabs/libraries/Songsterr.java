package be.rombit.tdd.tabs.libraries;

import be.rombit.tdd.tabs.models.entities.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Songsterr {

    private final RestTemplate restTemplate;

    @Value("${songsterr.url}")
    private String baseUrl;

    public List<Song> getSongs(String artist) {
        ParameterizedTypeReference<List<Song>> songTypeDefinition = new ParameterizedTypeReference<List<Song>>() {};
        ResponseEntity<List<Song>> response = this.restTemplate.exchange(baseUrl+"/songs.json?pattern="+artist, HttpMethod.GET, null, songTypeDefinition);

        return response.getBody();
    }

}
