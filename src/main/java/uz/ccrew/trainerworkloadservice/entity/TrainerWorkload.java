package uz.ccrew.trainerworkloadservice.entity;

import uz.ccrew.trainerworkloadservice.entity.base.BaseEntity;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainer_workload")
public class TrainerWorkload extends BaseEntity {
    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    @Column(name = "is_active")
    private boolean isActive;
    private LocalDateTime trainingDate;
    private Double trainingDuration;
}
