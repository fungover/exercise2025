package org.example.api.page.admin;

import org.example.entities.Movie;
import org.example.service.director.DirectorService;
import org.example.service.movie.MovieService;
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
    private final MovieService movieService;

    public AdminPageController(DirectorService directorService, MovieService movieService) {
        this.directorService = directorService;
        this.movieService = movieService;
    }

    @GetMapping
    public String adminDashboard(Model model) {
        List<Director> directors = directorService.getAllDirectors();
        List<Movie> movies = movieService.getAllMovies();

        model.addAttribute("directors", directors);
        model.addAttribute("movies", movies);

        return "admin/admin";
    }

}
