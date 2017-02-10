package ua.cinemabooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.cinemabooking.model.Movie;
import ua.cinemabooking.model.Seans;
import ua.cinemabooking.service.TiketsService;
import ua.cinemabooking.serviceModel.Seats;

import java.util.List;

/**
 * Created by macbookair on 04.02.17.
 */


/**
 * 2 methods have not written yet, because frontend developer must
 * decide how client side will be work
 * After this decision, Controller will be refactored
 */
@RestController
public class ControllerRest extends BaseController{


    @Autowired
    private TiketsService tiketsService;

    @RequestMapping(value = "/getSchedule/{filmId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Seans>> getSchedule(@PathVariable(name = "filmId") String filmId){

        if (filmId != null) {

            Long filmIdLong = Long.valueOf(filmId);

            List<Seans> seans = tiketsService.seansList(filmIdLong);

            if (seans != null) {
                return new ResponseEntity<List<Seans>>(seans, HttpStatus.OK);
            } else return new ResponseEntity<List<Seans>>(HttpStatus.NOT_FOUND);

        }else return new ResponseEntity<List<Seans>>(HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/getAllFilms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> getAllFilms(){

        List<Movie>  listMovies = tiketsService.movieList();
        if (listMovies != null){
            return new ResponseEntity<List<Movie>>(listMovies, HttpStatus.OK);
        }else return new ResponseEntity<List<Movie>>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/getSeats/{seansId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Seats> getSeats(@PathVariable(name = "seansId") String seansId){

        if (seansId != null) {
        Long seansIdLong = Long.valueOf(seansId);
            Seats seats = tiketsService.getSeats(seansIdLong);
            if (seats != null){
                return new ResponseEntity<Seats>(seats, HttpStatus.OK);
            }else return new ResponseEntity<Seats>(HttpStatus.NOT_FOUND);
        }else return new ResponseEntity<Seats>(HttpStatus.BAD_REQUEST);
    }

}
