package be.rombit.tdd.tabs.services;

import be.rombit.tdd.tabs.models.entities.Artist;
import be.rombit.tdd.tabs.models.repositories.ArtistRepository;
import be.rombit.tdd.tabs.services.exceptions.BusinessValidationException;
import be.rombit.tdd.tabs.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final Validator validator;

    public List<Artist> findAll() {
        return this.artistRepository.findAll();
    }

    @Transactional
    public void create(Artist artist) {
        Set<ConstraintViolation<Artist>> violations = this.validator.validate(artist);

        if (violations.size() > 0) {
            throw new BusinessValidationException(violations);
        }

        this.artistRepository.save(artist);
    }

    @Transactional
    public void update(long id, Artist newArtist) {
        Artist foundArtist = this.artistRepository.findById(id).orElseThrow(NotFoundException::new);

        foundArtist.setName(newArtist.getName());
        foundArtist.setStartedAt(newArtist.getStartedAt());

        Set<ConstraintViolation<Artist>> violations = this.validator.validate(foundArtist);

        if (violations.size() > 0) {
            throw new BusinessValidationException(violations);
        }

        this.artistRepository.save(foundArtist);
    }
}
