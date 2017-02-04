package ua.cinemabooking.controllers;

import org.springframework.web.bind.annotation.RestController;

/**
 * Created by macbookair on 04.02.17.
 */


@RestController("/")
public class ControllerRest {


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

    @RequestMapping(value = "getAllFilms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
