package ua.cinemabooking.controllers;

import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.cinemabooking.model.Movie;
import ua.cinemabooking.model.Seans;
import ua.cinemabooking.repository.SeansRepository;
import ua.cinemabooking.service.pagination.MoviePagination;
import ua.cinemabooking.service.pagination.SeansPagination;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by macbookair on 04.02.17.
 */

@Controller(value = "/")
public class ControllerView extends BaseController{

    @Autowired
    private MoviePagination moviePagination;

    @Autowired
    private SeansPagination seansPagination;

    @Autowired
    SeansRepository seansRepository;

    @RequestMapping(value = {"/" ,"/afisha"}, method = RequestMethod.GET)
    public String getAfisha(){
        return "afisha";
    }

    @RequestMapping(value = "/seans/{filmId}", method = RequestMethod.GET)
    public String getPayment(Model model){
        return "schedule";
    }

    @RequestMapping(value = "/seats/{seansId}", method = RequestMethod.GET)
    public String getSeats(Model model){
        return "seats";
    }

}
