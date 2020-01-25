package be.rombit.tdd.tabs.models.entities;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testinfected.hamcrest.validation.ViolationMatchers.on;
import static org.testinfected.hamcrest.validation.ViolationMatchers.violates;

@ActiveProfiles(value = "test")
public class ArtistTests {
    private Validator validator;

    @BeforeEach
    public void setup() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void nameNotNull() {
        Artist artist = new Artist();
        artist.setName(null);

        final Set<ConstraintViolation<Artist>> errors = this.validator.validate(artist);

        assertThat(errors, violates(on("name")));
    }

    @Test
    public void nameNotBlank() {
        Artist artist = new Artist();
        artist.setName("");

        final Set<ConstraintViolation<Artist>> errors = this.validator.validate(artist);

        assertThat(errors, violates(on("name")));
    }

    @Test
    public void nameHasMoreThen100Characters() {
        Artist artist = new Artist();
        artist.setName(String.join("", Collections.nCopies(101, "*")));

        final Set<ConstraintViolation<Artist>> errors = this.validator.validate(artist);

        assertThat(errors, violates(on("name")));
    }

    @Test
    public void nameIsCorrect() {
        Artist artist = new Artist();
        artist.setName("Bob Marley");

        final Set<ConstraintViolation<Artist>> errors = this.validator.validate(artist);

        assertThat(errors, Matchers.not(violates(on("name"))));
    }

}
