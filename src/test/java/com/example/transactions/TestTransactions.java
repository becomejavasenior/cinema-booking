package com.example.transactions;

import com.example.CinemaApplicationTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.cinemabooking.model.BillOrder;
import ua.cinemabooking.model.Movie;
import ua.cinemabooking.model.Place;
import ua.cinemabooking.model.Seans;
import ua.cinemabooking.repository.MovieRepository;
import ua.cinemabooking.repository.SeansRepository;
import ua.cinemabooking.service.Populator;
import ua.cinemabooking.service.TiketsService;
import ua.cinemabooking.serviceModel.Seats;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by macbookair on 08.02.17.
 */

@Transactional
public class TestTransactions extends CinemaApplicationTests{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TiketsService tiketsService;

    @Autowired
    private Populator populator;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SeansRepository seansRepository;

    @Before
    public void setUp() {
            populator.init();
    }

    @After
    public void tearDown() {
        //After calling each test
    }

    /**
     * Test is successful
     *
     * This test check working createOrder() method
     */
    @Test
    public void createOrderTest(){

        logger.info("> in createOrderTest :");
        Seans seans1 = new Seans();
        seans1.setStart(LocalDateTime.now());
        seans1.setEnd(LocalDateTime.of(2017, 5, 23, 23,23));

        String email = "adsd@gmail.com";

        Place place = new Place();
        place.setX(2);
        place.setY(5);

        BillOrder order = tiketsService.createOrder(seans1, email, place);

        assertNotNull(order);
        assertNotNull(order.getId());
        assertEquals(email, order.getEmail());

        logger.info("< after createOrderTest; ");

    }

    /**
     * Test is successful
     *
     * This test check that method confirm payment by order
     */
    @Test
    public void afterPayTest(){

        logger.info("> in afterPayTest :");

        Seans seans1 = new Seans();
        seans1.setStart(LocalDateTime.now());
        seans1.setEnd(LocalDateTime.of(2017, 5, 23, 23,23));

        String email = "adsd@gmail.com";

        Place place = new Place();
        place.setX(2);
        place.setY(5);

        BillOrder order = tiketsService.createOrder(seans1, email, place);

        assertNotNull(order);

        BillOrder billOrder = tiketsService.afterPay(order);
        assertTrue(billOrder.isPayed());

        logger.info("< after afterPayTest :");

    }

    /**
     * Test is successful
     *
     * This test get All movies
     */
    @Test
    public void movieListTest(){

        logger.info("> in movieListTest :");

        Collection<Movie> movies = tiketsService.movieList();

        assertNotNull(movies);
        assertTrue(movies.size() > 0);

        logger.info("< after movieListTest :");
    }

    /**
     * Test is successful
     *
     * This test find all movies, get first from the list<Movie>,
     * get List of Seans which correspond particular Movie Id
     */
    @Test
    public void seansListTest(){

        logger.info("> in seansListTest :");

        List<Movie> movies = tiketsService.movieList();
        assertNotNull(movies);
        assertTrue(movies.size() > 0);

        logger.info("size of movies -> "+movies.size());

        assertNotNull(movies.get(0));
        assertNotNull(movies.get(0).getId());

        logger.info(String.valueOf(movies.get(0).getId()));

        List<Seans> collection = tiketsService.seansList(movies.get(0).getId());

        assertNotNull(collection);

        assertTrue(collection.size() > 0);

        logger.info("< after seansListTest; ");

    }

    /**
     * Test is successful
     * This method check converting Seans from database and Seats ()
     */
    @Test
    public void getSeatsTest(){

        logger.info("> in getSeatsTest :");

        List<Seans> seanses = (List<Seans>) seansRepository.findAll();
        assertNotNull(seanses);
        assertTrue(seanses.size() > 0);

        Seans seans = seanses.get(0);
        assertNotNull(seans);
        assertNotNull(seans.getId());

        Movie movie = seans.getMovie();
        assertNotNull(movie);
        assertNotNull(movie.getName());
        assertNotNull(movie.getPrice());
        assertNotNull(seans.getStart());
        assertNotNull(seans.getEnd());

        String movieName = movie.getName();
        BigDecimal movieprice = movie.getPrice();
        LocalDateTime seansStart = seans.getStart();

        Seats seats = tiketsService.getSeats(seans.getId());
        assertNotNull(seats);
        assertEquals(movieName,seats.getFilmName());
        assertEquals(movieprice, seats.getPrice());
        assertEquals(seansStart, seats.getFilmDate());

        assertNotNull(seats.getMap());

        logger.info("< after getSeatsTest; ");
    }

    /**
     * Test is successful
     * This method test max and min values of Id in movieRepository
     */
    @Test
    public void getMaxAndMinMoiveIdTest(){
        logger.info("> in getMaxAndMinMoiveIdTest :");

        List<Movie> movies = tiketsService.movieList();

        assertNotNull(movies);
        assertTrue(movies.size() > 0);

        Long maxId = Long.valueOf(movieRepository.getMaxCode());
        Long minId = Long.valueOf(movieRepository.getMinCode());

        assertNotNull(maxId);
        assertNotNull(minId);

        logger.info(" max Id from Movies -> "+maxId.toString()+" ;");
        logger.info(" min Id from Movies -> "+minId.toString()+" ;");

        logger.info("> in getMaxAndMinMoiveIdTest :");
    }

}




























