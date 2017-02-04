package ua.cinemabooking.service;

import org.junit.Test;
import org.mockito.Mockito;
import ua.cinemabooking.model.BillOrder;
import ua.cinemabooking.model.Movie;
import ua.cinemabooking.model.Place;
import ua.cinemabooking.model.Seans;
import ua.cinemabooking.repository.BillOrderRepository;
import ua.cinemabooking.serviceModel.Seats;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

/*
 *
 */
public class PurchaseTicketServiceTest {

  /**
  * getSeatsForSession
  * Input: session ID
  * Output: all seats with
  * Exception: ID not found
 */

  private static final Seats FREE_SEATS = new Seats();
  private static final String FILM_NAME = "film";

  static {
    FREE_SEATS.setFilmDate(LocalDateTime.now());
    FREE_SEATS.setFilmName(FILM_NAME);
    FREE_SEATS.setMap(new ArrayList<>(10));
  }

  private static final List<BillOrder> BILL_ORDERS = new ArrayList<>(10);

  static {
    long i = 0;
    for (BillOrder billOrder : BILL_ORDERS) {
      Place place = new Place();
      place.setId(i);
      place.setX((int) i);
      place.setY((int) i);
      billOrder.setId(i++);
      billOrder.setPlace(place);
      billOrder.setPayed(true);
    }
  }

  private static final Movie MOVIE = new Movie();

  static {
    MOVIE.setId(1L);
    MOVIE.setName(FILM_NAME);
    MOVIE.setPrice(BigDecimal.valueOf(10));
  }

  private static final Seans SEANS = new Seans();

  static {
    SEANS.setId(1L);
    SEANS.setMovie(MOVIE);
  }

  @Test
  public void getAllSeats() {
    BillOrderRepository billOrderRepository = Mockito.mock(BillOrderRepository.class);
    when(billOrderRepository.findBySeans(SEANS)).thenReturn(BILL_ORDERS);

    TiketsServiceImpl tiketsService = new TiketsServiceImpl();
    Seats seats = tiketsService.getAllSeats(SEANS);

    assertTrue(seats.getMap().size() == 10);
  }

  /**
   * getFilms()
   * @return: List of films for the day or empty list;
   */

  /**
   * getSession(Id film);
   *
   * @return: List of Sessions
   * @throws: IllegalArgumentException
   *
   */

  /**
   * createBillOrder(Seans Id, Place Id)
   *
   * returns BillOrder
   * throws AlreadyBookedException
   */
}
