package ua.cinemabooking.service;

import ua.cinemabooking.model.BillOrder;
import ua.cinemabooking.model.Movie;
import ua.cinemabooking.model.Place;
import ua.cinemabooking.model.Seans;
import ua.cinemabooking.serviceModel.Seats;

import java.util.List;
import java.util.Set;

/**
 * Created by Artem on 04.02.2017.
 */
public interface TiketsService {


    public BillOrder createOrder(Seans seans, String email, Set<Place> place);

    public BillOrder afterPay(BillOrder billOrder);

    public List<Movie> movieList();

    public Seats getSeats(Long seansId);

    public  List<Seans> seansList(Long movieId);


}