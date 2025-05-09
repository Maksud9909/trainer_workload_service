package uz.ccrew.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.ccrew.dto.summary.TrainerMonthlySummaryDTO;
import uz.ccrew.dto.summary.YearsDTO;
import uz.ccrew.dto.training.TrainerMonthlySummaryProjection;
import uz.ccrew.dto.summary.MonthsDTO;
import uz.ccrew.dto.training.TrainerWorkloadDTO;
import uz.ccrew.entity.TrainerWorkload;
import uz.ccrew.exp.ObjectNotProvidedException;
import uz.ccrew.repository.TrainerWorkLoadRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static uz.ccrew.enums.ActionType.ADD;
import static uz.ccrew.enums.ActionType.DELETE;


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
                .trainingDate(LocalDateTime.of(2025, 3, 21, 1, 1))
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
        TrainerMonthlySummaryProjection dbProjection =
                new TrainerMonthlySummaryProjection("test", "test", "test"
                        , Boolean.TRUE, 2025, 3, 60.0);
        List<TrainerMonthlySummaryProjection> workload = Collections.singletonList(dbProjection);
        when(trainerWorkLoadRepository.getMonthlySummary("test")).thenReturn(workload);
        Map<Integer, List<MonthsDTO>> yearToMonthsMap = new LinkedHashMap<>();
        TrainerMonthlySummaryProjection firstEntry = workload.getFirst();

        for (TrainerMonthlySummaryProjection projection : workload) {
            MonthsDTO monthsDTO = MonthsDTO.builder()
                    .month(Month.of(projection.getMonth()))
                    .totalDuration(projection.getTotalDuration())
                    .build();

            yearToMonthsMap
                    .computeIfAbsent(projection.getYear(), y -> new ArrayList<>())
                    .add(monthsDTO);
        }

        List<YearsDTO> yearsList = (List<YearsDTO>) yearToMonthsMap.entrySet().stream()
                .map(entry -> YearsDTO.builder()
                        .year(entry.getKey())
                        .months(entry.getValue())
                        .build())
                .toList();
        List<TrainerMonthlySummaryDTO> result = trainerWorkloadService.getMonthlyWorkload("test");
        List<TrainerMonthlySummaryDTO> expected = Collections.singletonList(
                TrainerMonthlySummaryDTO.builder()
                        .trainerUsername(firstEntry.getTrainerUsername())
                        .trainerFirstName(firstEntry.getTrainerUsername())
                        .trainerLastName(firstEntry.getTrainerLastName())
                        .isActive(firstEntry.isActive())
                        .years(yearsList)
                        .build()
        );
        assertEquals(expected, result);
    }
}
