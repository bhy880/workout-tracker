package bhy880.workout_tracker.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity 
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    private String exerciseName;

    @NotNull
    private int sets;

    @NotNull
    private int repsPerSet;

    @NotNull
    @PositiveOrZero
    private double weightInKg;

    @ManyToOne 
    @JoinColumn(name = "workoutid")
    private Workout workout;
        
    
    public Exercise() {
    }
    
    public Exercise(String exerciseName, int sets, int repsPerSet, double weightInKg) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.repsPerSet = repsPerSet;
        this.weightInKg = weightInKg;
    }
    
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getExerciseName() {
        return exerciseName;
    }
    
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }
    
    public int getSets() {
        return sets;
    }
    
    public void setSets(int sets) {
        this.sets = sets;
    }
    
    public int getRepsPerSet() {
        return repsPerSet;
    }
    
    public void setRepsPerSet(int repsPerSet) {
        this.repsPerSet = repsPerSet;
    }
    
    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
    
    
    public double getWeightInKg() {
        return weightInKg;
    }



    public void setWeightInKg(double weightInKg) {
        this.weightInKg = weightInKg;
    }

}
