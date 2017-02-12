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

    private static final int SEANSES_STARTS = 9;
    private static final int SEANSES_ENDS = 23;
    private static final int MOVIES_NUMBER = 10;
    private static final int ROWS = 7;
    private static final int SEATS = 15;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SeansRepository seansRepository;

    @Autowired
    private PlaceRepository placeRepository;

    private void populateMovies() {
        for (int i = 0; i < MOVIES_NUMBER; i++) {
            Movie movie = new Movie();
            movie.setName("movie " + i);
            movie.setPrice(new BigDecimal(50 + i * 10));
            movieRepository.save(movie);
        }

    }

    private void populateSeanses() {
        for (int i = SEANSES_STARTS; i < SEANSES_ENDS; i++) {
            Seans seans = new Seans();
            LocalDateTime start = LocalDateTime.of(2017, Month.FEBRUARY, 4, i, 0);
            seans.setStart(start);
            LocalDateTime end = LocalDateTime.of(2017, Month.FEBRUARY, 4, i, 55);
            seans.setEnd(end);
            Random r = new Random();
//            List<Movie> all = (List<Movie>) movieRepository.findAll();
//            int size = all.size();

            //These lines were changed because tests have shown that simple random by range(10) is not working
            Long maxId = Long.valueOf(movieRepository.getMaxCode());
            Long minId = Long.valueOf(movieRepository.getMinCode());
            Movie movie = movieRepository.findOne(minId + (long) r.nextInt((int) (maxId - minId + 1)));

//            Movie movie = movieRepository.findOne((long) r.nextInt(size));

            seans.setMovie(movie);
            seansRepository.save(seans);
        }
    }

    private void populatePlaces() {
        List<Seans> all = (List<Seans>) seansRepository.findAll();
        for (Seans seans : all) {
            for (int i = 1; i < SEATS; i++) {
                for (int j = 1; j < ROWS; j++) {
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

    public static int getSeansesStarts() {
        return SEANSES_STARTS;
    }

    public static int getSeansesEnds() {
        return SEANSES_ENDS;
    }

    public static int getMoviesNumber() {
        return MOVIES_NUMBER;
    }

    public static int getROWS() {
        return ROWS;
    }

    public static int getSEATS() {
        return SEATS;
    }
}
