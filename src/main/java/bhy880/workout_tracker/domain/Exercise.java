package bhy880.workout_tracker.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    String exerciseName;
    int sets;
    int repsPerSet;
    double weightInKg;
    
    
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



    public double getWeightInKg() {
        return weightInKg;
    }



    public void setWeightInKg(double weightInKg) {
        this.weightInKg = weightInKg;
    }
    


    

}
