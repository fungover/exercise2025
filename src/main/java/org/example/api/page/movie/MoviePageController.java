package org.example.api.page.movie;

import org.example.entities.Director;
import org.example.entities.Movie;
import org.example.service.director.DirectorService;
import org.example.service.movie.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movie")
public class MoviePageController {
    MovieService movieService;
    DirectorService directorService;

    public MoviePageController(MovieService movieService, DirectorService directorService) {
        this.movieService = movieService;
        this.directorService = directorService;
    }

    @GetMapping("/{id}")
    public String moviePage(@PathVariable Long id, Model model){
        Movie movie = movieService.getMovie(id);
        Director director = directorService.getDirector(movie.getDirector().getId());

        model.addAttribute("director", director);
        model.addAttribute("movie", movie);
        return "movie/movie";
    }

}
