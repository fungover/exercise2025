package org.example.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.example.api.dto.ExerciseView;
import org.example.api.dto.PersonalBestCreate;
import org.example.service.PersonalBestService;
import org.example.service.UserService;
import org.example.repo.ExerciseRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@Validated
public class WebController {
    private final ExerciseRepository exercises;
    private final PersonalBestService prs;
    private final UserService users;

    public WebController(ExerciseRepository exercises, PersonalBestService prs, UserService users) {
        this.exercises = exercises;
        this.prs = prs;
        this.users = users;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile(Authentication auth, Model model) {
        var userId = users.requireUserId(auth.getName());
        model.addAttribute("username", auth.getName());
        model.addAttribute("prs", prs.listForUser(userId));
        // mappa repo->view enkelt
        List<ExerciseView> exViews = exercises.findAll().stream()
                .map(e -> new ExerciseView(e.getId(), e.getName(), e.getMuscleGroup()))
                .toList();
        model.addAttribute("exercises", exViews);
        return "profile";
    }


    @PostMapping("/profile/pr")
    public String createPr(Authentication auth, @ModelAttribute @Validated PrForm form) {
        var userId = users.requireUserId(auth.getName());
        var dto = new PersonalBestCreate(form.exerciseId(), form.reps(), form.weightKg(), form.achievedOn());
        prs.upsert(userId, dto);
        return "redirect:/profile";
    }


    @PostMapping("/profile/pr/{id}")
    public String deletePr(Authentication auth, @PathVariable Long id) {
        var userId = users.requireUserId(auth.getName());
        prs.delete(userId, id);
        return "redirect:/profile";
    }

  
    public record PrForm(
            @NotNull Long exerciseId,
            @Min(1) int reps,
            @NotNull BigDecimal weightKg,
            @NotNull LocalDate achievedOn
    ) {
    }
}
