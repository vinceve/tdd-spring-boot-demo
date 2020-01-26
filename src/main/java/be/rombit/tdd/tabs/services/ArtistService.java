package be.rombit.tdd.tabs.services;

import be.rombit.tdd.tabs.models.entities.Artist;
import be.rombit.tdd.tabs.models.repositories.ArtistRepository;
import be.rombit.tdd.tabs.services.exceptions.BusinessValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void create(Artist artist) {
        Set<ConstraintViolation<Artist>> violations = this.validator.validate(artist);

        if (violations.size() > 0) {
            throw new BusinessValidationException(violations);
        }

        this.artistRepository.save(artist);
    }
}
