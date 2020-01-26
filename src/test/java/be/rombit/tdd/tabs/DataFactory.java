package be.rombit.tdd.tabs;

import be.rombit.tdd.tabs.models.entities.Artist;

public class DataFactory {

    public Artist getArtist() {
        Artist artist = new Artist();

        artist.setName("Bob Marley");
        artist.setStartedAt(1973);

        return artist;
    }

}
