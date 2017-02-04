package ua.cinemabooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.cinemabooking.model.BillOrder;
import ua.cinemabooking.model.Movie;
import ua.cinemabooking.model.Place;
import ua.cinemabooking.model.Seans;
import ua.cinemabooking.repository.BillOrderRepository;
import ua.cinemabooking.repository.SeansRepository;
import ua.cinemabooking.serviceModel.Seats;

import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem on 04.02.2017.
 */
public class TiketsServiceImpl implements  TiketsService {
    @Autowired
    SeansRepository seansRepository;
    @Autowired
    BillOrderRepository billOrderRepository;
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
