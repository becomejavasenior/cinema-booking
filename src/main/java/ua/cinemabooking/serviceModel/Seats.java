package ua.cinemabooking.serviceModel;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created by Artem on 04.02.2017.
 */
@Component
public class Seats {

    private Map<Long,Boolean> map;

    private BigDecimal price;
    private String filmName;
    private LocalDateTime filmDate;

    public Map<Long, Boolean> getMap() {
        return map;
    }

    public void setMap(Map<Long, Boolean> map) {
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
