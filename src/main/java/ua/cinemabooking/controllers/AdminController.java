package ua.cinemabooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.cinemabooking.model.Movie;
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
        model.addAttribute("mov", new Movie());
        return "admin/movies";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        movieRepository.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("mov", movieRepository.findOne(id));
        return "admin/movies";
    }

    @RequestMapping("/save")
    public String save(Movie movie, Model model) {
//        System.out.println("movie = " + movie);
        movieRepository.save(movie);
        return "redirect:/";
    }
}
