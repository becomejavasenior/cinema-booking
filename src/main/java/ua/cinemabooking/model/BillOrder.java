package ua.cinemabooking.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by RATIBOR on 04.02.2017.
 */
@Entity
public class BillOrder {

    @Id @GeneratedValue
    private Long id;

    private String email;

    private LocalDateTime dataTime;

    private BigDecimal moneyCapacity;

//    @ManyToOne(targetEntity = Place.class, fetch = FetchType.EAGER)
//    private Place place;

    //то что было изменено, надо мониторить почему посыпались проблемы в тестах, только из-за одной строчки

    private Set<Place> placeSet;
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

//    public Place getPlace() {
//        return place;
//    }
//
//    public void setPlace(Place place) {
//        this.place = place;
//    }
    @OneToMany(mappedBy = "billOrder",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<Place> getPlaceSet() {
        return placeSet;
    }

    public void setPlaceSet(Set<Place> placeSet) {
        this.placeSet = placeSet;
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

    public BigDecimal getMoneyCapacity() {
        return moneyCapacity;
    }

    public void setMoneyCapacity(BigDecimal moneyCapacity) {
        this.moneyCapacity = moneyCapacity;
    }
}
