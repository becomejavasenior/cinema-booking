package ua.cinemabooking.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by RATIBOR on 04.02.2017.
 */
@Entity
public class BillOrder {

    @Id @GeneratedValue
    private Long id;

    private String email;

    private LocalDateTime dataTime;

    @ManyToOne(targetEntity = Place.class, fetch = FetchType.EAGER)
    private Place place;

    @ManyToOne(targetEntity = Seans.class, fetch = FetchType.EAGER)
    private Seans seans;

    private boolean payed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDateTime dataTime) {
        this.dataTime = dataTime;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Seans getSeans() {
        return seans;
    }

    public void setSeans(Seans seans) {
        this.seans = seans;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }
}
