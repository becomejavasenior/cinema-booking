package ua.cinemabooking.service;

import ua.cinemabooking.model.BillOrder;
import ua.cinemabooking.model.Movie;
import ua.cinemabooking.model.Place;
import ua.cinemabooking.model.Seans;
import ua.cinemabooking.serviceModel.Seats;

import javax.persistence.criteria.Order;
import java.util.List;

/**
 * Created by Artem on 04.02.2017.
 */
public interface TiketsService {
    public Seats getAllSeats(Movie movie, Seans seans);

    public Order createOrder(Seans seans, String email, Place place);

    public Order afterPay(BillOrder billOrder);

    public List<Movie> movieList();

    public  List<Seans> seansList(Movie movie);

}
