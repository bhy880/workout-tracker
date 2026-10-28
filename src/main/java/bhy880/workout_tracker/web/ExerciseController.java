package bhy880.workout_tracker.web;

import org.springframework.stereotype.Controller;

import bhy880.workout_tracker.domain.ExerciseRepository;
import bhy880.workout_tracker.domain.WorkoutRepository;

@Controller 
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;

    public ExerciseController(ExerciseRepository exerciseRepository,
                              WorkoutRepository workoutRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
    }

}
