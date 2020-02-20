package be.rombit.tdd.tabs.libraries;

import be.rombit.tdd.tabs.models.entities.Song;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWireMock(port = 9000)
class SongsterrTest {

    @Autowired
    private Songsterr songsterr;

    @Test
    public void getAListOfSongs() {
        List<Song> songs = this.songsterr.getSongs("Gojira");

        assertEquals(81, songs.size());
    }

}