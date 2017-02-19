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

    @GetMapping("/test/index")
    public String goHome() {
        System.out.println("/test/index");
        return "index";
    }

    @PostMapping(value = "/test/movies")
    @ResponseBody
    public DatatablesResponse<Movie> getAllMovies(HttpServletRequest request){
        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);
        return DatatablesResponse.build(moviePagination.getDataSet(criterias.getStart(), criterias.getLength()), criterias);
    }

    @PostMapping(value = "/test/seanses")
    @ResponseBody
    public DatatablesResponse<Seans> getAllSeanses(HttpServletRequest request){
        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);
        return DatatablesResponse.build(seansPagination.getDataSet(criterias.getStart(), criterias.getLength()), criterias);
    }

}
