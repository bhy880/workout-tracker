package bhy880.workout_tracker.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import bhy880.workout_tracker.domain.Exercise;
import bhy880.workout_tracker.domain.ExerciseRepository;
import bhy880.workout_tracker.domain.Workout;
import bhy880.workout_tracker.domain.WorkoutRepository;
import jakarta.validation.Valid;

@Controller
public class ExerciseController {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    public ExerciseController(ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
    }

    // Workout must belong to the logged-in user, otherwise 404
    private Workout findOwnWorkout(Long workoutId, Authentication auth) {
        return workoutRepository.findByIdAndOwnerUsername(workoutId, auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Exercise's workout must belong to the logged-in user, otherwise 404
    private Exercise findOwnExercise(Long id, Authentication auth) {
        return exerciseRepository.findByIdAndWorkoutOwnerUsername(id, auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/addexercise/{workoutId}")
    public String addExercise(@PathVariable Long workoutId, Model model, Authentication auth) {
        Exercise exercise = new Exercise();
        exercise.setWorkout(findOwnWorkout(workoutId, auth));
        model.addAttribute("exercise", exercise);
        return "exerciseform";
    }

    @PostMapping("/addexercise/{workoutId}")
    public String saveExercise(@PathVariable Long workoutId, @Valid Exercise exercise,
                               BindingResult result, Authentication auth) {
        exercise.setWorkout(findOwnWorkout(workoutId, auth));   // set before returning the form
        if (result.hasErrors()) {
            return "exerciseform";
        }
        exerciseRepository.save(exercise);
        return "redirect:/workout/" + workoutId;
    }

    @GetMapping("/editexercise/{id}")
    public String editExerciseForm(@PathVariable Long id, Model model, Authentication auth) {
        model.addAttribute("exercise", findOwnExercise(id, auth));
        return "exerciseform";
    }

    @PostMapping("/editexercise/{id}")
    public String editExercise(@PathVariable Long id, @Valid Exercise currExercise,
                               BindingResult result, Authentication auth) {
        Exercise exercise = findOwnExercise(id, auth);
        if (result.hasErrors()) {
            currExercise.setId(id);
            currExercise.setWorkout(exercise.getWorkout());     // form needs workout.id
            return "exerciseform";
        }
        exercise.setExerciseName(currExercise.getExerciseName());
        exercise.setSets(currExercise.getSets());
        exercise.setRepsPerSet(currExercise.getRepsPerSet());
        exercise.setWeightInKg(currExercise.getWeightInKg());
        exerciseRepository.save(exercise);
        return "redirect:/workout/" + exercise.getWorkout().getId();
    }

    @PostMapping("/deleteexercise/{id}")
    public String deleteExercise(@PathVariable Long id, Authentication auth) {
        Exercise exercise = findOwnExercise(id, auth);
        Long workoutId = exercise.getWorkout().getId();
        exerciseRepository.delete(exercise);
        return "redirect:/workout/" + workoutId;
    }
}