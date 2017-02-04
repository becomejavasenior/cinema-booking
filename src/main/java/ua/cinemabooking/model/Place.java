package ua.cinemabooking.model;

import javax.persistence.*;

/**
 * Created by RATIBOR on 04.02.2017.
 */
@Entity
public class Place {
    @Id @GeneratedValue
    Long id;
    Integer x;
    Integer y;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
