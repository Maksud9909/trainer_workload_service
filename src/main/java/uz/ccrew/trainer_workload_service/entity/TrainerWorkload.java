package uz.ccrew.trainer_workload_service.entity;

import uz.ccrew.trainer_workload_service.entity.base.BaseEntity;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

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
    private LocalDate trainingDate;
    private Double trainingDuration;
}
