package bhy880.workout_tracker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import bhy880.workout_tracker.domain.ExerciseRepository;
import bhy880.workout_tracker.domain.Workout;
import bhy880.workout_tracker.domain.WorkoutRepository;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
public class WorkoutController {

    @Autowired 
    private WorkoutRepository wRepository;
    
    @Autowired
    private ExerciseRepository eRepository;

    public WorkoutController(WorkoutRepository wRepository, ExerciseRepository eRepository) {
        this.wRepository = wRepository;
        this.eRepository= eRepository;
    }

    @GetMapping("/workouts")
    public String showWorkouts(Model model) {
        model.addAttribute("workouts", wRepository.findAll());
        return "workouts";
    }
    

    @GetMapping("/addworkout")
    public String addWorkout(Model model) {
        model.addAttribute("workout", new Workout());
        return "workoutform";
    }

    @PostMapping("/addworkout")
    public String saveWorkout(Workout workout) {
        wRepository.save(workout);
        return "redirect:/workouts";
    }

    @GetMapping("/editworkout/{id}")
    public String editWorkoutForm(@PathVariable Long id, Model model) {
        Workout workout = wRepository.findById(id).orElseThrow();
        model.addAttribute("workout", workout);
        return "workoutform";
    }

    @PostMapping("/editworkout/{id}")
    public String editWorkout(@PathVariable Long id,
             Workout workout) {
        workout.setId(id);
        wRepository.save(workout);
        return "redirect:/workouts";
    }

    @PostMapping("/deleteworkout/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        wRepository.deleteById(id);
        return "redirect:/workouts";
    }

    @GetMapping("/workout/{id}")
    public String workoutDetail(@PathVariable Long id, Model model) {
    model.addAttribute("workout", wRepository.findById(id).orElseThrow());
    return "workoutdetails";
}
}
