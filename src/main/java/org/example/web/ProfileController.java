package org.example.web;

import jakarta.validation.Valid;
import org.example.repo.ExerciseRepository;
import org.example.service.PersonalBestService;
import org.example.service.UserService;
import org.example.web.dto.PersonalBestForm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class ProfileController {

    private final UserService users;
    private final PersonalBestService prs;
    private final ExerciseRepository exercises;

    public ProfileController(UserService users, PersonalBestService prs, ExerciseRepository exercises) {
        this.users = users;
        this.prs = prs;
        this.exercises = exercises;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile(Authentication auth, Model model) {
        var userId = users.requireUserId(auth.getName());

        model.addAttribute("username", auth.getName());
        model.addAttribute("exercises", exercises.findAll()); // för select-listan
        model.addAttribute("prs", prs.listForUser(userId));   // lista av PersonalBestView

        if (!model.containsAttribute("form")) {
            var form = new PersonalBestForm();
            form.setAchievedOn(LocalDate.now());
            model.addAttribute("form", form);
        }
        return "profile";
    }

    @PostMapping("/profile/pr")
    public String addOrUpdatePr(Authentication auth,
                                @Valid @ModelAttribute("form") PersonalBestForm form,
                                BindingResult binding,
                                RedirectAttributes ra) {
        if (binding.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.form", binding);
            ra.addFlashAttribute("form", form);
            return "redirect:/profile";
        }
        var userId = users.requireUserId(auth.getName());
        var view = prs.upsert(userId, new org.example.api.dto.PersonalBestCreate(
                form.getExerciseId(), form.getReps(), form.getWeightKg(), form.getAchievedOn()
        ));
        ra.addFlashAttribute("flash", "Sparade PR för " + view.exerciseName() + " (" + view.reps() + " reps).");
        return "redirect:/profile";
    }

    @PostMapping("/profile/pr/{id}/delete")
    public String deletePr(Authentication auth, @PathVariable Long id, RedirectAttributes ra) {
        var userId = users.requireUserId(auth.getName());
        prs.delete(userId, id);
        ra.addFlashAttribute("flash", "Tog bort PR #" + id);
        return "redirect:/profile";
    }
}
