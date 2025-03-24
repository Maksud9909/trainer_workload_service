package uz.ccrew.trainerworkloadservice.controller;

import uz.ccrew.trainerworkloadservice.dto.summary.TrainerMonthlySummaryDTO;
import uz.ccrew.trainerworkloadservice.dto.trainer_workload.TrainerWorkloadDTO;
import uz.ccrew.trainerworkloadservice.service.impl.TrainerWorkloadServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/trainers")
@RequiredArgsConstructor
public class TrainerWorkloadController {
    private final TrainerWorkloadServiceImpl trainerWorkloadService;

    @PostMapping("/")
    public ResponseEntity<String> processTraining(@RequestBody TrainerWorkloadDTO trainingDTO) {
        trainerWorkloadService.processTraining(trainingDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/workload")
    public ResponseEntity<List<TrainerMonthlySummaryDTO>> getMonthlyWorkLoad(@PathVariable("username") String username) {
        List<TrainerMonthlySummaryDTO> result = trainerWorkloadService.getMonthlyWorkload(username);
        return ResponseEntity.ok(result);
    }
}
