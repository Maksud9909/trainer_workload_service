package uz.ccrew.trainerworkloadservice.repository;

import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerWorkLoadRepositoryTest {

    @Mock
    private TrainerWorkLoadRepository trainerWorkLoadRepository;

    private final String TRAINER_USERNAME = "testTrainer";
    private final LocalDate TRAINING_DATE = LocalDate.of(2025, 3, 21);

    @Test
    void findLatestByTrainerUsernameAndTrainingDate_ShouldReturnWorkload() {
        trainerWorkLoadRepository.deleteByTrainerUsernameAndTrainingDate(TRAINER_USERNAME, TRAINING_DATE);
        verify(trainerWorkLoadRepository, times(1))
                .deleteByTrainerUsernameAndTrainingDate(TRAINER_USERNAME, TRAINING_DATE);
    }
}
