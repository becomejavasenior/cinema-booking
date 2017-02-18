package ua.cinemabooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cinemabooking.model.BillOrder;
import ua.cinemabooking.model.Movie;
import ua.cinemabooking.model.Place;
import ua.cinemabooking.model.Seans;
import ua.cinemabooking.repository.BillOrderRepository;
import ua.cinemabooking.repository.MovieRepository;
import ua.cinemabooking.repository.PlaceRepository;
import ua.cinemabooking.repository.SeansRepository;
import ua.cinemabooking.serviceModel.Seats;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Artem on 04.02.2017.
 */
@Service
public class TiketsServiceImpl implements  TiketsService {
    @Autowired
    private SeansRepository seansRepository;
    @Autowired
    private BillOrderRepository billOrderRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public BillOrder createOrder(Seans seans, String email, Place place) {
        BillOrder billOrder = new BillOrder();
        billOrder.setSeans(seans);
        billOrder.setEmail(email);
        billOrder.setPlace(place);
        billOrder.setPayed(false);
        return billOrderRepository.save(billOrder);
    }

    @Override
    public BillOrder afterPay(BillOrder billOrder) {
        BillOrder billOrder1;
        billOrder1 = billOrder;
        billOrder1.setPayed(true);
        billOrder1 = billOrderRepository.save(billOrder1);
        return billOrder1;
    }

    @Override
    public List<Movie> movieList() {
        List<Movie> movieList = (List<Movie>) movieRepository.findAll();
        return movieList;
    }

    @Override
    public Seats getSeats(Long seansId) {
        Seans seans = seansRepository.findOne(seansId);
        List<BillOrder> orderList = billOrderRepository.findBySeans(seans);
        List<Place> placeList = (List<Place>) placeRepository.findAll();

        Map<Long, Boolean> freeseats = new LinkedHashMap<>();

        placeList.forEach((place) ->{

            final boolean[] resalt = {true};
            orderList.forEach((order) ->{

                if (order.getPlace().getX().equals(place.getX()) && order.getPlace().getY().equals(place.getY()) && order.isPayed())
                    resalt[0] = false;
            });

            freeseats.put(place.getId(), resalt[0]);

        });

        Seats seats1= new Seats();
        seats1.setMap(freeseats);
        seats1.setPrice(seans.getMovie().getPrice());
        seats1.setFilmDate(seans.getStart());
        seats1.setFilmName(seans.getMovie().getName());
        return seats1;
    }

    @Override
    public List<Seans> seansList(Long movieId) {
        Movie movie = movieRepository.findOne(movieId);
        List<Seans> seansList = seansRepository.findByMovie(movie);
        return seansList;
    }

}
