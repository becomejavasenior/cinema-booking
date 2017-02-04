package ua.cinemabooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.cinemabooking.model.BillOrder;
import ua.cinemabooking.model.Movie;
import ua.cinemabooking.model.Place;
import ua.cinemabooking.model.Seans;
import ua.cinemabooking.repository.SeansRepository;
import ua.cinemabooking.serviceModel.Seats;

import javax.persistence.criteria.Order;
import java.util.List;

/**
 * Created by Artem on 04.02.2017.
 */


public class TiketsServiceImpl implements  TiketsService {
    @Autowired
    SeansRepository seansRepository;
    @Override
    public Seats getAllSeats(Movie movie, Seans seans) {
        List<Seans> seansList = seansRepository.findByMovie(movie);

        return null;
    }

    @Override
    public Order createOrder(Seans seans, String email, Place place) {
        return null;
    }

    @Override
    public Order afterPay(BillOrder billOrder) {
        return null;
    }

    @Override
    public List<Movie> movieList() {
        return null;
    }

    @Override
    public List<Seans> seansList(Movie movie) {
        return null;
    }
}
