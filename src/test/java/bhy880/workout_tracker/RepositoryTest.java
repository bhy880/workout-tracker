package bhy880.workout_tracker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import bhy880.workout_tracker.domain.AppUserRepository;
import bhy880.workout_tracker.domain.WorkoutRepository;

@SpringBootTest
class RepositoryTests {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Test
    void userExists() {
        assertThat(userRepository.findByUsername("user")).isPresent();
    }

    @Test
    void unknownUserDoesNotExist() {
        assertThat(userRepository.findByUsername("nobody")).isEmpty();
    }

    @Test
    void userHasWorkouts() {
        assertThat(workoutRepository.findByOwnerUsername("user")).isNotEmpty();
    }
}