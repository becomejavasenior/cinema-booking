package ua.cinemabooking.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by macbookair on 04.02.17.
 */

@Controller(value = "/")
public class ControllerView {

    @RequestMapping(value = "/afisha", method = RequestMethod.GET)
    public ResponseEntity<String> getAfisha(Model model){

        return new ResponseEntity<String>("/afisha.html", HttpStatus.OK);
    }

    @RequestMapping(value = "/schedule/{filmId}", method = RequestMethod.GET)
    public ResponseEntity<String> getPayment(Model model){


        return new ResponseEntity<String>("/schedule.html", HttpStatus.OK);
    }

    @RequestMapping(value = "/seats/{seansId}", method = RequestMethod.GET)
    public ResponseEntity<String> getSeats(Model model){

        return new ResponseEntity<String>("/seats.html", HttpStatus.OK);
    }

}
