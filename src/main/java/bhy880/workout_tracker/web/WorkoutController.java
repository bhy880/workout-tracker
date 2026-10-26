package bhy880.workout_tracker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import bhy880.workout_tracker.domain.Workout;
import bhy880.workout_tracker.domain.WorkoutRepository;

@Controller 
public class WorkoutController {

    @Autowired 
    private WorkoutRepository wRepository;    

    public WorkoutController(WorkoutRepository wRepository) {
        this.wRepository = wRepository;
    }

    @GetMapping("/addworkout")
    public String addWorkoutForm(Model model) {
        model.addAttribute("workout", new Workout());
        return "addworkout";
    }

    @PostMapping("/addworkout")
    public String addWorkout(Workout workout) {
        wRepository.save(workout);
        return "redirect:/addworkout";
    }

    @GetMapping("/editworkout/{id}")
    public String editWorkoutForm(@PathVariable Long id, Model model) {
        Workout workout = wRepository.findById(id).orElseThrow();
        model.addAttribute("workout", workout);
        return "editworkout";
    }

    @PostMapping("/editworkout/{id}")
    public String editWorkout(@PathVariable Long id,
             Workout workout) {
        workout.setId(id);
        wRepository.save(workout);
        return "redirect:/editworkout/" + id;
    }

    @PostMapping("/deleteworkout/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        wRepository.deleteById(id);
        return "redirect:/addworkout";
    }
}
