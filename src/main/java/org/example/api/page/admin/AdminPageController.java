package org.example.api.page.admin;

import org.example.service.director.DirectorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.example.entities.Director;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPageController {
    private final DirectorService directorService;

    public AdminPageController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public String adminDashboard(Model model) {
        List<Director> directors = directorService.getAllDirectors();
        model.addAttribute("directors", directors);

        return "admin/admin";
    }

}
