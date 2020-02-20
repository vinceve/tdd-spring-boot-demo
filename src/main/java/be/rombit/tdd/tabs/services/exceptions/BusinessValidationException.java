package be.rombit.tdd.tabs.services.exceptions;

import be.rombit.tdd.tabs.models.entities.Artist;
import lombok.Getter;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Getter
public class BusinessValidationException extends RuntimeException {
    private Set<ConstraintViolation<Artist>> violations;

    public BusinessValidationException(Set<ConstraintViolation<Artist>> violations) {
        this.violations = violations;
    }
}
