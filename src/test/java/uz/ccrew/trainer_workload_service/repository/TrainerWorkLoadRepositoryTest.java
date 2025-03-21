package uz.ccrew.trainer_workload_service.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.ccrew.trainer_workload_service.entity.TrainerWorkload;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerWorkLoadRepositoryTest {

    @Mock
    private TrainerWorkLoadRepository trainerWorkLoadRepository;

    private final String TRAINER_USERNAME = "testTrainer";
    private final LocalDate TRAINING_DATE = LocalDate.of(2025, 3, 21);
    private TrainerWorkload workload;

    @BeforeEach
    void setUp() {
        workload = new TrainerWorkload();
        workload.setTrainerUsername(TRAINER_USERNAME);
        workload.setTrainingDate(TRAINING_DATE);
    }

    @Test
    void findLatestByTrainerUsernameAndTrainingDate_ShouldReturnWorkload() {
        when(trainerWorkLoadRepository.findLatestByTrainerUsernameAndTrainingDate(TRAINER_USERNAME, TRAINING_DATE))
                .thenReturn(Optional.of(workload));

        Optional<TrainerWorkload> result = trainerWorkLoadRepository.findLatestByTrainerUsernameAndTrainingDate(TRAINER_USERNAME, TRAINING_DATE);

        assertTrue(result.isPresent());
        assertEquals(TRAINER_USERNAME, result.get().getTrainerUsername());
        assertEquals(TRAINING_DATE, result.get().getTrainingDate());

        verify(trainerWorkLoadRepository, times(1))
                .findLatestByTrainerUsernameAndTrainingDate(TRAINER_USERNAME, TRAINING_DATE);
    }
}