package be.rombit.tdd.tabs.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
public class Artist extends AbstractPersistable<Long> {
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;
}
