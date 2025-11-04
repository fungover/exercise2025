package org.example.api.page.director;

import org.example.entities.Director;
import org.example.entities.Movie;
import org.example.service.director.DirectorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/director")
public class DirectorPageController {
    private final DirectorService directorService;

    public DirectorPageController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/{id}")
    public String directorDetails(@PathVariable Long id, Model model) {
        Director director = directorService.getDirector(id);
        List<Movie> movies = director.getMovies();
        model.addAttribute("director", director);
        model.addAttribute("movies", movies);

        return "director/director";
    }

}
