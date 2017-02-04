package ua.cinemabooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cinemabooking.model.Movie;
import ua.cinemabooking.model.Place;
import ua.cinemabooking.model.Seans;
import ua.cinemabooking.repository.MovieRepository;
import ua.cinemabooking.repository.PlaceRepository;
import ua.cinemabooking.repository.SeansRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Random;

/**
 * Created by Sunny on 04.02.2017.
 */
@Service
public class Populator {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    SeansRepository seansRepository;

    @Autowired
    PlaceRepository placeRepository;

    private void populateMovies() {
        for (int i = 0; i < 10; i++) {
            Movie movie = new Movie();
            movie.setName("movie " + i);
            movie.setPrice(new BigDecimal(50 + i * 10));
            movieRepository.save(movie);
        }

    }

    private void populateSeanses() {
        for (int i = 9; i < 23; i++) {
            Seans seans = new Seans();
            LocalDateTime start = LocalDateTime.of(2017, Month.FEBRUARY, 4, i, 0);
            seans.setStart(start);
            LocalDateTime end = LocalDateTime.of(2017, Month.FEBRUARY, 4, i, 55);
            seans.setEnd(end);
            Random r = new Random();
            List<Movie> all = (List<Movie>) movieRepository.findAll();
            int size = all.size();
            Movie movie = movieRepository.findOne((long) r.nextInt(size));
            seans.setMovie(movie);
            seansRepository.save(seans);
        }
    }

    private void populatePlaces() {
        List<Seans> all = (List<Seans>) seansRepository.findAll();
        for (Seans seans : all) {
            for (int i = 1; i < 12; i++) {
                for (int j = 1; j < 7; j++) {
                    Place place = new Place();
                    place.setX(i);
                    place.setY(j);
                    placeRepository.save(place);
                }
            }
        }
    }

    public void init() {
        populateMovies();
        populateSeanses();
        populatePlaces();
    }
}
