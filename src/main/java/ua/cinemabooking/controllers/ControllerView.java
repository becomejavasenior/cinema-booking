package ua.cinemabooking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by macbookair on 04.02.17.
 */

@Controller(value = "/")
//@Controller
public class ControllerView {


    @RequestMapping(value = {"/" ,"/afisha"}, method = RequestMethod.GET)
    public String getAfisha(){

        return "afisha";
    }

    @RequestMapping(value = "/schedule/{filmId}", method = RequestMethod.GET)
    public String getPayment(Model model){


        return "schedule";
    }

    @RequestMapping(value = "/seats/{seansId}", method = RequestMethod.GET)
    public String getSeats(Model model){

        return "seats";
    }

}
