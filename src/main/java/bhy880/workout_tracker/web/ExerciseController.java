package bhy880.workout_tracker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import bhy880.workout_tracker.domain.Exercise;
import bhy880.workout_tracker.domain.ExerciseRepository;
import bhy880.workout_tracker.domain.Workout;
import bhy880.workout_tracker.domain.WorkoutRepository;

@Controller
public class ExerciseController {

    
    private WorkoutRepository workoutRepository;
    private ExerciseRepository exerciseRepository;

    public ExerciseController(ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
    }

    @GetMapping("/addexercise/{workoutId}")
    public String addExercise(@PathVariable Long workoutId, Model model) {
        Workout workout = workoutRepository.findById(workoutId).orElseThrow();
        Exercise exercise = new Exercise();
        exercise.setWorkout(workout);
        model.addAttribute("exercise", exercise);
        return "exerciseform";
    }

    @PostMapping("/addexercise/{workoutId}")
    public String saveExercise(@PathVariable Long workoutId,Exercise exercise) {
        Workout workout = workoutRepository.findById(workoutId).orElseThrow();
        exercise.setWorkout(workout);
        exerciseRepository.save(exercise);
        return "redirect:/workout/" + workoutId;
    }

    @GetMapping("/editexercise/{id}")
    public String editExerciseForm(@PathVariable Long id, Model model) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow();
        model.addAttribute("exercise", exercise);
        return "exerciseform";
    }

    @PostMapping("/editexercise/{id}")
    public String editExercise(@PathVariable Long id, Exercise currExercise) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow();
        exercise.setExerciseName(currExercise.getExerciseName());
        exercise.setSets(currExercise.getSets());
        exercise.setRepsPerSet(currExercise.getRepsPerSet());
        exercise.setWeightInKg(currExercise.getWeightInKg());
        exerciseRepository.save(exercise);
        return "redirect:/workout/" + exercise.getWorkout().getId();
    }

    @PostMapping("/deleteexercise/{id}")
    public String deleteExercise(@PathVariable Long id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow();
        Long workoutId = exercise.getWorkout().getId();
        exerciseRepository.delete(exercise);
        return "redirect:/workout/" + workoutId;
    }
}
