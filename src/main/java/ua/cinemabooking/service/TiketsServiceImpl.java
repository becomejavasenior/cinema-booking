package ua.cinemabooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cinemabooking.model.BillOrder;
import ua.cinemabooking.model.Movie;
import ua.cinemabooking.model.Place;
import ua.cinemabooking.model.Seans;
import ua.cinemabooking.repository.BillOrderRepository;
import ua.cinemabooking.repository.MovieRepository;
import ua.cinemabooking.repository.SeansRepository;
import ua.cinemabooking.serviceModel.Seats;

import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem on 04.02.2017.
 */
@Service
public class TiketsServiceImpl implements  TiketsService {
    @Autowired
    SeansRepository seansRepository;
    @Autowired
    BillOrderRepository billOrderRepository;
    @Autowired
    MovieRepository movieRepository;
    @Override
    public Seats getAllSeats(Seans seans) {
        List<BillOrder> orderList = billOrderRepository.findBySeans(seans);
        List<Boolean> seats = new ArrayList<>();
        for (int x = 0; x <10; x++) {
            for (int y = 0; y <10 ; y++) {
                Boolean resalt = true;
                for (BillOrder order: orderList
                     ) {
                    if (order.getPlace().getX()==x && order.getPlace().getY()==y && order.isPayed())
                        resalt = false;
                }
                seats.add(resalt);

            }
        }
        Seats seats1= new Seats();
        seats1.setMap(seats);
        seats1.setPrice(seans.getMovie().getPrice());
        seats1.setFilmDate(seans.getStart());
        seats1.setFilmName(seans.getMovie().getName());
        return seats1;
    }

    @Override
    public BillOrder createOrder(Seans seans, String email, Place place) {
        BillOrder billOrder = new BillOrder();
        billOrder.setSeans(seans);
        billOrder.setEmail(email);
        billOrder.setPlace(place);
        billOrder.setPayed(false);
        return billOrder;
    }

    @Override
    public BillOrder afterPay(BillOrder billOrder) {
        BillOrder billOrder1;
        billOrder1 = billOrder;
        billOrder1.setPayed(true);
        return billOrder1;
    }

    @Override
    public List<Movie> movieList() {
        List<Movie> movieList = (List<Movie>) movieRepository.findAll();
        return movieList;
    }

    @Override
    public List<Seans> seansList(Movie movie) {
        List<Seans> seansList = seansRepository.findByMovie(movie);
        return null;
    }
}
