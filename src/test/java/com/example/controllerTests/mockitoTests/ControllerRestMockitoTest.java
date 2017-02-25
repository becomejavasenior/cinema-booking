package com.example.controllerTests.mockitoTests;

import com.example.controllerTests.AbstractControllerTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ua.cinemabooking.controllers.ControllerRest;
import ua.cinemabooking.liqPayApi.LiqPayService;
import ua.cinemabooking.model.BillOrder;
import ua.cinemabooking.model.Movie;
import ua.cinemabooking.model.Place;
import ua.cinemabooking.model.Seans;
import ua.cinemabooking.repository.MovieRepository;
import ua.cinemabooking.repository.PlaceRepository;
import ua.cinemabooking.repository.SeansRepository;
import ua.cinemabooking.service.Populator;
import ua.cinemabooking.service.TiketsService;
import ua.cinemabooking.serviceModel.ClientOrder;
import ua.cinemabooking.serviceModel.Seats;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.mockito.Mockito.*;

/**
 * Created by macbookair on 08.02.17.
 */

@Transactional
public class ControllerRestMockitoTest extends AbstractControllerTest {

    @Mock
    private TiketsService tiketsService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private SeansRepository seansRepository;

    @Mock
    private Populator populator;

    @Mock
    private LiqPayService liqPayService;

    @InjectMocks
    private ControllerRest controllerRest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setUp(controllerRest);
        populator.init();
    }

    @Test
    public void createNewOrder() throws Exception {

        ClientOrder order = getClientOrder();
        Seans seans = getSeansById(order.getSeansId());
//        Place place = getPlaceById(order.getPlaceId());
        List<Place> placeSet = getPlaceSet(order.getPlaceIdList());

        when(placeRepository.findAll(anyListOf(Long.class))).thenReturn(placeSet);
        when(seansRepository.findOne(any(Long.class))).thenReturn(seans);

        when(tiketsService.createOrder(seans, order.getEmail(), new HashSet<>(placeSet))).
                thenReturn(getBillOrderByPlacesAmount(placeSet.size(), seans));

        String uri = "/createOrder";

        String orderJson = mapToJson(order);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).content(orderJson)).andReturn();

        String content = result.getResponse().getContentAsString();

        int status = result.getResponse().getStatus();

        verify(tiketsService, times(1)).createOrder(seans, order.getEmail(), new HashSet<>(placeSet));

        Assert.assertEquals("failure -> expected status 201 ", 201, status);
        //Assert.assertTrue("failure -> expected content ", content.trim().length() > 0);

        ClientOrder order1 = mapFromJson(content, ClientOrder.class);

        Assert.assertNotNull("failure -> ClientOrder is null", order1);
        //Assert.assertEquals("failure -> text is not equals", order.getEmail(), order1.getEmail());

    }

    @Test
    public void getAllFilmsTest() throws Exception {

        when(tiketsService.movieList()).thenReturn(getAllMovie());

        String uri = "/getAllFilms";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).
                accept(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();

        int status = result.getResponse().getStatus();

        verify(tiketsService, times(1)).movieList();

        Assert.assertEquals("failure -> expected status 200 ", 200, status);
        Assert.assertTrue("failure -> expected content ", content.trim().length() > 0);

    }

    @Test
    public void getScheduleByFilmIdTest() throws Exception {

        Long id = getIdInAvaliableRange(100, 120);

        when(tiketsService.seansList(id)).thenReturn(getAllSeans());

        String uri = "/getSeans/{filmId}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).
                accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();

        int status = result.getResponse().getStatus();

        verify(tiketsService, times(1)).seansList(id);

        Assert.assertEquals("failure -> expected status 200 ", 200, status);
        Assert.assertTrue("failure -> expected content ", content.trim().length() > 0);

    }

    @Test
    public void getSeatsBySeansIdTest() throws Exception {

        Long id = getIdInAvaliableRange(0, 7);

        when(tiketsService.getSeats(id)).thenReturn(getSeats(id));

        String uri = "/getSeats/{seansId}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).
                accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();

        int status = result.getResponse().getStatus();

        verify(tiketsService, times(1)).getSeats(id);

        Assert.assertEquals("failure -> expected status 200 ", 200, status);
        Assert.assertTrue("failure -> expected content ", content.trim().length() > 0);
    }

    private Seats getSeats(Long seansId) {

        Seans seans = getSeansById(seansId);

        int xAmount = 10;
        int yAmount = 10;

        List<BillOrder> orderList = getListBillOrder(xAmount, yAmount, seans);
        List<Place> placeList = getPlaces();
        Map<Long, Boolean> freeseats = new HashMap<>();


        placeList.forEach((p) -> {

            final boolean[] r = {true};
            assert orderList != null;
            orderList.forEach((order) -> {
                //hаскоментировать
                if (order.isPayed()) {

                    order.getPlaceSet().forEach((place1 -> {

                        if (place1.getX().equals(p.getX()) && place1.getY().equals(p.getY()))
                            r[0] = false;
                    }));
                }


            });
            freeseats.put(p.getId(), r[0]);
        });
//
//        for (int x = 0; x <xAmount; x++) {
//
//            for (int y = 0; y <yAmount ; y++) {
//
//                Boolean resalt = true;
//                for (BillOrder order: orderList
//                        ) {
//                    if (order.getPlace().getX()==x && order.getPlace().getY()==y && order.isPayed())
//                        resalt = false;
//                }
//                freeseats.add(resalt);
//            }
//        }

        Seats seats = new Seats();
        seats.setPrice(seans.getMovie().getPrice());
        seats.setFilmDate(seans.getStart());
        seats.setFilmName(seans.getMovie().getName());
        seats.setMap(freeseats);

        return seats;
    }

    private List<BillOrder> getListBillOrder(int xAmount, int yAmount, Seans seans) {

        List<BillOrder> list = new LinkedList<>();

        for (int i = 0; i < xAmount; i++) {

            for (int j = 0; j < yAmount; j++) {

                list.add(getBillOrder(i, j, seans));
            }
        }

        return list;
    }

    private List<Place> getPlaces() {

        List<Place> list = new ArrayList<>();
        Place place = null;
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                place = new Place();
                place.setId(random.nextLong());
                place.setX(i);
                place.setY(j);
            }
        }
        return list;
    }


    private BillOrder getBillOrder(int x, int y, Seans seans) {

        BillOrder billOrder = getBillOrderByPlacesAmount(1, seans);
//        hаскоментировать
        billOrder.setPlaceSet(new HashSet<>(Arrays.asList(getPlace(x, y))));

        return billOrder;
    }


    private BillOrder getBillOrderByPlacesAmount(int amount, Seans seans) {

        BillOrder billOrder = new BillOrder();
        Random random = new Random();
        int i = 9;
        billOrder.setId((long) random.nextInt(10));
        billOrder.setDataTime(LocalDateTime.of(2017, Month.FEBRUARY, 4, i, 0));
        billOrder.setEmail("email1234@gmail.com");

        billOrder.setPayed(false);
        Set<Place> set = new HashSet<>();
        for (int j = 0; j < amount; j++) {
            set.add(getPlace(j, 1));
        }
        billOrder.setPlaceSet(set);
        billOrder.setSeans(seans);

        return billOrder;
    }


    private Place getPlaceById(Long id) {

        Place place = getPlace(8, 8);
        place.setId(id);
        return place;
    }

    private List<Place> getPlaceSet(List<Long> idSet) {
        List<Place> placeSet = new LinkedList<>();

        idSet.forEach((id) -> {
            placeSet.add(getPlaceById(id));
        });

        return placeSet;
    }

    private Place getPlace(int x, int y) {

        Place place = new Place();
        Random random = new Random();
        place.setId(random.nextLong());
        place.setX(x);
        place.setY(y);
        return place;
    }

    private Seans getSeansById(Long id) {

        Seans seans = new Seans();
//        Random random = new Random(10);
        seans.setId(id);
        int i = 9;
        seans.setStart(LocalDateTime.of(2017, Month.FEBRUARY, 4, i, 0));
        seans.setMovie(getMovie());
        seans.setEnd(LocalDateTime.of(2017, Month.FEBRUARY, 4, i + 2, 55));

        return seans;
    }

    private Long getIdInAvaliableRange(int minId, int maxId) {

        Random random = new Random();

        return (long) minId + (long) (random.nextInt((int) (maxId - minId + 1)));
    }

    private List<Movie> getAllMovie() {
        List<Movie> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(getMovie());
        }
        return list;
    }

    private List<Seans> getAllSeans() {

        Movie movie = getMovie();

        List<Seans> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(getSeans(movie));
        }

        return list;

    }

    private Seans getSeans(Movie movie) {

        Seans seans = new Seans();
        Random random = new Random(100);
        int number = random.nextInt();
        seans.setId((long) number);
        int i = random.nextInt(9);
        seans.setStart(LocalDateTime.of(2017, Month.FEBRUARY, 4, i, 0));
        seans.setMovie(getMovie());
        seans.setEnd(LocalDateTime.of(2017, Month.FEBRUARY, 4, i, 55));
        return seans;
    }

    private Movie getMovie() {
        Movie movie = new Movie();
        Random random = new Random(100);
        int number = random.nextInt();
        movie.setId((long) number);
        movie.setName("Movie" + number);
        movie.setPrice(BigDecimal.valueOf(number));
        return movie;
    }

    private ClientOrder getClientOrder() {
        ClientOrder order = new ClientOrder();
        order.setEmail("email1234@gmail.com");

        Random random = new Random();
        //hаскоментировать
        List<Long> set = new LinkedList<>(Arrays.asList((long) random.nextInt(10000), (long) random.nextInt(2000), (long) random.nextInt(30000)));

        order.setPlaceIdList(set);
        order.setSeansId((long) random.nextInt(10));
        return order;
    }

    private ClientOrder getBadClientOrder() {
        ClientOrder order = getClientOrder();
        order.setEmail("email1234gmail.com");
        return order;
    }
}

































