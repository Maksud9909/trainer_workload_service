package uz.ccrew.trainerworkloadservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.ccrew.trainerworkloadservice.dto.summary.MonthsDTO;
import uz.ccrew.trainerworkloadservice.dto.summary.TrainerMonthlySummaryDTO;
import uz.ccrew.trainerworkloadservice.dto.summary.YearsDTO;
import uz.ccrew.trainerworkloadservice.dto.trainer_workload.TrainerWorkloadDTO;
import uz.ccrew.trainerworkloadservice.entity.TrainerWorkload;
import uz.ccrew.trainerworkloadservice.exp.ObjectNotProvidedException;
import uz.ccrew.trainerworkloadservice.repository.TrainerWorkLoadRepository;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static uz.ccrew.trainerworkloadservice.enums.ActionType.ADD;
import static uz.ccrew.trainerworkloadservice.enums.ActionType.DELETE;


@ExtendWith(MockitoExtension.class)
class TrainerWorkloadServiceImplTest {

    @Mock
    private TrainerWorkLoadRepository trainerWorkLoadRepository;

    @InjectMocks
    private TrainerWorkloadServiceImpl trainerWorkloadService;

    private TrainerWorkloadDTO workloadDTO;

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
        trainerWorkloadService.processTraining(workloadDTO);

        verify(trainerWorkLoadRepository, times(1))
                .deleteByTrainerUsernameAndTrainingDate(workloadDTO.getTrainerUsername(), workloadDTO.getTrainingDate());
    }

    @Test
    void getMonthlyWorkload_ShouldReturnEmptyList_WhenNoData() {
        when(trainerWorkLoadRepository.getMonthlySummary("testTrainer"))
                .thenReturn(Collections.emptyList());
        List<TrainerMonthlySummaryDTO> result = trainerWorkloadService.getMonthlyWorkload("testTrainer");
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(trainerWorkLoadRepository, times(1)).getMonthlySummary("testTrainer");
    }

    @Test
    void getMonthlyWorkload_ShouldReturnData_WhenDataExists() {


        List<Object[]> rawData = new ArrayList<>();
        rawData.add(new Object[]{"testTrainer", "John", "Doe", true, 2025, 3, 10.0});
        rawData.add(new Object[]{"testTrainer", "John", "Doe", true, 2025, 4, 5.5});

        when(trainerWorkLoadRepository.getMonthlySummary("testTrainer"))
                .thenReturn(rawData);
        List<TrainerMonthlySummaryDTO> result = trainerWorkloadService.getMonthlyWorkload("testTrainer");
        assertNotNull(result);
        assertEquals(1, result.size());

        TrainerMonthlySummaryDTO summary = result.get(0);
        assertEquals("testTrainer", summary.getTrainerUsername());
        assertEquals("John", summary.getTrainerFirstName());
        assertEquals("Doe", summary.getTrainerLastName());
        assertTrue(summary.isActive());

        List<YearsDTO> yearsList = summary.getYears();
        assertNotNull(yearsList);
        assertEquals(1, yearsList.size());

        YearsDTO yearsDTO = yearsList.get(0);
        assertEquals(2025, yearsDTO.getYear());
        assertEquals(2, yearsDTO.getMonths().size());

        MonthsDTO month1 = yearsDTO.getMonths().get(0);
        assertEquals(Month.of(3).getDisplayName(TextStyle.FULL, Locale.ENGLISH), month1.getMonth());
        assertEquals(10.0, month1.getTotalDuration(), 0.001);

        MonthsDTO month2 = yearsDTO.getMonths().get(1);
        assertEquals(Month.of(4).getDisplayName(TextStyle.FULL, Locale.ENGLISH), month2.getMonth());
        assertEquals(5.5, month2.getTotalDuration(), 0.001);

        verify(trainerWorkLoadRepository, times(1)).getMonthlySummary("testTrainer");
    }
}
