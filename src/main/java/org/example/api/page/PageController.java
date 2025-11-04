package org.example.api.page;

import org.example.entities.Director;
import org.example.entities.Movie;
import org.example.service.director.DirectorService;
import org.example.service.movie.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class PageController {
    DirectorService directorService;
    MovieService movieService;

    public PageController(DirectorService directorService, MovieService movieService) {
        this.directorService = directorService;
        this.movieService = movieService;
    }

    @GetMapping
    public String index(Model model) {
        List<Director> directors = directorService.getAllDirectors();
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("directors", directors);
        model.addAttribute("movies", movies);

        return "index";
    }
}
