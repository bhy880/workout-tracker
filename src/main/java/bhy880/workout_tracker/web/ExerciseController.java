package bhy880.workout_tracker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import bhy880.workout_tracker.domain.Exercise;
import bhy880.workout_tracker.domain.ExerciseRepository;

@Controller
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public ExerciseController(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping("/addexercise")
    public String addExercise(Model model) {
        model.addAttribute("exercise", new Exercise());
        return "exerciseform";
    }

    @PostMapping("/addexercise")
    public String saveExercise(Exercise exercise) {
        exerciseRepository.save(exercise);
        return "redirect:/workouts";
    }

    @GetMapping("/editexercise/{id}")
    public String editExerciseForm(@PathVariable Long id, Model model) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow();
        model.addAttribute("exercise", exercise);
        return "exerciseform";
    }

    @PostMapping("/editexercise/{id}")
    public String editExercise(@PathVariable Long id, Exercise exercise) {
        exercise.setId(id);
        exerciseRepository.save(exercise);
        return "redirect:/workouts";
    }

    @PostMapping("/deleteexercise/{id}")
    public String deleteExercise(@PathVariable Long id) {
        exerciseRepository.deleteById(id);
        return "redirect:/workouts";
    }
}
