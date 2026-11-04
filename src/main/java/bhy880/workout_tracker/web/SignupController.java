package bhy880.workout_tracker.web;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import bhy880.workout_tracker.domain.AppUser;
import bhy880.workout_tracker.domain.AppUserRepository;
import bhy880.workout_tracker.domain.SignupForm;
import jakarta.validation.Valid;

@Controller
public class SignupController {

    private final AppUserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public SignupController(AppUserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupForm signupForm, BindingResult result) {

    // Username must be free
    if (userRepository.findByUsername(signupForm.getUsername()).isPresent()) {
        result.rejectValue("username", "taken", "This  username is already taken :( ");
    }

    // Any errors → show the form again
    if (result.hasErrors()) {
        return "signup";
    }

    // Save new user: password hashed, role always USER
    AppUser user = new AppUser(
            signupForm.getUsername(),
            encoder.encode(signupForm.getPassword()),
            "USER");
    userRepository.save(user);

    return "redirect:/login?registered";
}
}