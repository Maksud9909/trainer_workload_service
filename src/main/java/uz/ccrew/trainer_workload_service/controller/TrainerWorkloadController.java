package uz.ccrew.trainer_workload_service.controller;

import uz.ccrew.trainer_workload_service.dto.trainer_workload.TrainerWorkloadDTO;
import uz.ccrew.trainer_workload_service.service.impl.TrainerWorkloadServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/trainings/workload")
@RequiredArgsConstructor
public class TrainerWorkloadController {
    private final TrainerWorkloadServiceImpl trainerWorkloadService;

    @PostMapping("/process")
    public ResponseEntity<String> handleTraining(@RequestBody TrainerWorkloadDTO trainingDTO) {
        trainerWorkloadService.processTraining(trainingDTO);
        return ResponseEntity.ok().build();
    }
}
