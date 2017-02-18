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
import ua.cinemabooking.repository.PlaceRepository;
import ua.cinemabooking.repository.SeansRepository;
import ua.cinemabooking.service.TiketsService;
import ua.cinemabooking.serviceModel.ClientOrder;
import ua.cinemabooking.serviceModel.Seats;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by macbookair on 04.02.17.
 */

@RestController
public class ControllerRest extends BaseController{


    @Autowired
    private TiketsService tiketsService;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private SeansRepository seansRepository;

    @Autowired
    private LiqPayService liqPayService;

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createNewOrder(@Valid @RequestBody ClientOrder clientOrder){

        Place place = placeRepository.findOne(clientOrder.getPlaceId());

//        if (place == null) return new ResponseEntity<Map<String, String>>(HttpStatus.NOT_FOUND);

        Seans seans = seansRepository.findOne(clientOrder.getSeansId());

//        if (seans == null) return new ResponseEntity<Map<String, String>>(HttpStatus.NOT_FOUND);

        BillOrder billOrder = tiketsService.createOrder(seans, clientOrder.getEmail(), place);

        Map<String, String> result = liqPayService.liqPayGenerateParamForHtmlForm(billOrder.getId(), seans.getMovie().getPrice().intValue());

        if (billOrder != null){
             return new ResponseEntity<Map<String, String>>(result, HttpStatus.CREATED);
        }else return new ResponseEntity<Map<String, String>>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/getSeans/{filmId}", method = RequestMethod.GET,
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

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

}
