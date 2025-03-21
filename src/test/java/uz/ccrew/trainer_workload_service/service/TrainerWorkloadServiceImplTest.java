package uz.ccrew.trainer_workload_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.ccrew.trainer_workload_service.dto.trainer_workload.TrainerWorkloadDTO;
import uz.ccrew.trainer_workload_service.entity.TrainerWorkload;
import uz.ccrew.trainer_workload_service.exp.ObjectNotProvidedException;
import uz.ccrew.trainer_workload_service.repository.TrainerWorkLoadRepository;
import uz.ccrew.trainer_workload_service.service.impl.TrainerWorkloadServiceImpl;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static uz.ccrew.trainer_workload_service.enums.ActionType.ADD;
import static uz.ccrew.trainer_workload_service.enums.ActionType.DELETE;


@ExtendWith(MockitoExtension.class)
class TrainerWorkloadServiceImplTest {

    @Mock
    private TrainerWorkLoadRepository trainerWorkLoadRepository;

    @InjectMocks
    private TrainerWorkloadServiceImpl trainerWorkloadService;

    private TrainerWorkloadDTO workloadDTO;
    private TrainerWorkload workload;

    @BeforeEach
    void setUp() {
        workloadDTO = TrainerWorkloadDTO.builder()
                .trainerUsername("testTrainer")
                .trainerFirstName("John")
                .trainerLastName("Doe")
                .trainingDate(LocalDate.of(2025, 3, 21))
                .trainingDuration(2.0)
                .actionType(ADD)
                .isActive(true)
                .build();

        workload = TrainerWorkload.builder()
                .trainerUsername(workloadDTO.getTrainerUsername())
                .trainerFirstName(workloadDTO.getTrainerFirstName())
                .trainerLastName(workloadDTO.getTrainerLastName())
                .trainingDate(workloadDTO.getTrainingDate())
                .trainingDuration(workloadDTO.getTrainingDuration())
                .isActive(workloadDTO.isActive())
                .build();
    }

    @Test
    void processTraining_ShouldThrowException_WhenActionTypeIsNull() {
        workloadDTO.setActionType(null);
        assertThrows(ObjectNotProvidedException.class, () -> trainerWorkloadService.processTraining(workloadDTO));
    }

    @Test
    void processTraining_ShouldSaveNewWorkload_WhenActionTypeIsAdd() {
        trainerWorkloadService.processTraining(workloadDTO);
        verify(trainerWorkLoadRepository, times(1)).save(any(TrainerWorkload.class));
    }


    @Test
    void processTraining_ShouldDeleteWorkload_WhenActionTypeIsDelete() {
        workloadDTO.setActionType(DELETE);
        when(trainerWorkLoadRepository.findLatestByTrainerUsernameAndTrainingDate(
                workloadDTO.getTrainerUsername(), workloadDTO.getTrainingDate()))
                .thenReturn(Optional.of(workload));

        trainerWorkloadService.processTraining(workloadDTO);

        // Проверяем, что объект удалён
        verify(trainerWorkLoadRepository, times(1)).delete(workload);
    }
}