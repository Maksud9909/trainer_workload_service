package uz.ccrew.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.ccrew.dto.summary.TrainerMonthlySummaryDTO;
import uz.ccrew.dto.training.TrainerWorkloadDTO;
import uz.ccrew.enums.ActionType;
import uz.ccrew.service.impl.TrainerWorkloadServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerWorkloadControllerTest {

    @Mock
    private TrainerWorkloadServiceImpl trainerWorkloadService;

    @InjectMocks
    private TrainerWorkloadController trainerWorkloadController;

    @Test
    void handleTraining_ShouldReturnOk_WhenProcessingSucceeds() {
        TrainerWorkloadDTO workloadDTO = TrainerWorkloadDTO.builder()
                .trainerUsername("testTrainer")
                .trainerFirstName("John")
                .trainerLastName("Doe")
                .trainingDate(LocalDateTime.of(2025, 3, 21, 1, 1))
                .trainingDuration(2.0)
                .actionType(ActionType.ADD)
                .isActive(true)
                .build();

        ResponseEntity<String> response = trainerWorkloadController.processTraining(workloadDTO);

        verify(trainerWorkloadService, times(1)).processTraining(workloadDTO);

        assertEquals("200 OK", response.getStatusCode().toString());
    }

    @Test
    void getMonthlyWorkLoad() {
        when(trainerWorkloadService.getMonthlyWorkload("test")).thenReturn(new ArrayList<>());
        ResponseEntity<List<TrainerMonthlySummaryDTO>> response = trainerWorkloadController.getMonthlyWorkLoad("test");
        verify(trainerWorkloadService, times(1)).getMonthlyWorkload("test");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
