package be.rombit.tdd.tabs.models.repositories;

import be.rombit.tdd.tabs.models.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
