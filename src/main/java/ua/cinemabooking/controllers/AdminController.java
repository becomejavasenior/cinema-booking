package ua.cinemabooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.cinemabooking.repository.MovieRepository;

/**
 * Created by Sunny on 18.02.2017.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    MovieRepository movieRepository;

    @RequestMapping
    public String allMovies(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "admin/movies";
    }
}
