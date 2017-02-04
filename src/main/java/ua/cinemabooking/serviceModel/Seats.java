package ua.cinemabooking.serviceModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Artem on 04.02.2017.
 */
public class Seats {
    List<Boolean> map;
    BigDecimal price;
    String filmName;
    LocalDateTime filmDate;

    public List<Boolean> getMap() {
        return map;
    }

    public void setMap(List<Boolean> map) {
        this.map = map;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public LocalDateTime getFilmDate() {
        return filmDate;
    }

    public void setFilmDate(LocalDateTime filmDate) {
        this.filmDate = filmDate;
    }
}
