package ua.cinemabooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.cinemabooking.liqPayApi.LiqPayService;
import ua.cinemabooking.model.BillOrder;
import ua.cinemabooking.model.Movie;
import ua.cinemabooking.model.Place;
import ua.cinemabooking.model.Seans;
import ua.cinemabooking.repository.MovieRepository;
import ua.cinemabooking.repository.PlaceRepository;
import ua.cinemabooking.repository.SeansRepository;
import ua.cinemabooking.service.TiketsService;
import ua.cinemabooking.serviceModel.ClientOrder;
import ua.cinemabooking.serviceModel.Seats;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by macbookair on 04.02.17.
 */

@RestController
public class ControllerRest extends BaseController {

    private final TiketsService tiketsService;
    private final PlaceRepository placeRepository;
    private final SeansRepository seansRepository;
    private final LiqPayService liqPayService;
    private final MovieRepository movieRepository;

    @Autowired
    public ControllerRest(TiketsService tiketsService, PlaceRepository placeRepository,
                          SeansRepository seansRepository, LiqPayService liqPayService, MovieRepository movieRepository) {
        this.tiketsService = tiketsService;
        this.placeRepository = placeRepository;
        this.seansRepository = seansRepository;
        this.liqPayService = liqPayService;
        this.movieRepository = movieRepository;
    }


    @RequestMapping(value = "/createOrder", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createNewOrder(@Valid @RequestBody ClientOrder clientOrder) {

        Place place = placeRepository.findOne(clientOrder.getPlaceId());

        if (place == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Seans seans = seansRepository.findOne(clientOrder.getSeansId());

        Movie movie = movieRepository.findOne(seans.getMovie().getId());

        BillOrder billOrder = tiketsService.createOrder(seans, clientOrder.getEmail(), place);

        if (billOrder != null) {
            Map<String, String> resultParams = liqPayService.liqPayGenerateParamForHtmlForm(billOrder.getId(), movie.getPrice().intValueExact());
            return new ResponseEntity<>(resultParams, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getSeans/{filmId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Seans>> getSchedule(@PathVariable(name = "filmId") String filmId) {

        if (filmId != null) {

            Long filmIdLong = Long.valueOf(filmId);

            List<Seans> seans = tiketsService.seansList(filmIdLong);

            if (seans != null) {
                return new ResponseEntity<List<Seans>>(seans, HttpStatus.OK);
            } else return new ResponseEntity<List<Seans>>(HttpStatus.NOT_FOUND);

        } else return new ResponseEntity<List<Seans>>(HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/getAllFilms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> getAllFilms() {

        List<Movie> listMovies = tiketsService.movieList();
        if (listMovies != null) {
            return new ResponseEntity<List<Movie>>(listMovies, HttpStatus.OK);
        } else return new ResponseEntity<List<Movie>>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/getSeats/{seansId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Seats> getSeats(@PathVariable(name = "seansId") String seansId) {

        if (seansId != null) {
            Long seansIdLong = Long.valueOf(seansId);
            Seats seats = tiketsService.getSeats(seansIdLong);
            if (seats != null) {
                return new ResponseEntity<Seats>(seats, HttpStatus.OK);
            } else return new ResponseEntity<Seats>(HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<Seats>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/getFilmIdBySeansId/{seansId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> getFilmIdBySeansId(@PathVariable(name = "seansId") String seansId) {
        if (seansId != null) {
            Long id = Long.valueOf(seansId);
            Seans one = seansRepository.findOne(id);
            Movie movie = one.getMovie();
            if (movie == null) {
                System.out.println("Нет movie");
                return new ResponseEntity<Movie>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Movie>(movie, HttpStatus.OK);
        } else return new ResponseEntity<Movie>(HttpStatus.BAD_REQUEST);

    }

}
