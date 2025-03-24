package uz.ccrew.trainerworkloadservice.repository;

import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerWorkLoadRepositoryTest {

    @Mock
    private TrainerWorkLoadRepository trainerWorkLoadRepository;

    private final String TRAINER_USERNAME = "testTrainer";
    private final LocalDateTime TRAINING_DATE = LocalDateTime.of(2025, 3, 21, 1, 1);

    @Test
    void findLatestByTrainerUsernameAndTrainingDate_ShouldReturnWorkload() {
        trainerWorkLoadRepository.deleteByTrainerUsernameAndTrainingDate(TRAINER_USERNAME, TRAINING_DATE);
        verify(trainerWorkLoadRepository, times(1))
                .deleteByTrainerUsernameAndTrainingDate(TRAINER_USERNAME, TRAINING_DATE);
    }

    @Test
    void getMonthlySummary() {
        trainerWorkLoadRepository.getMonthlySummary(TRAINER_USERNAME);
        verify(trainerWorkLoadRepository, times(1)).getMonthlySummary(TRAINER_USERNAME);
    }
}
