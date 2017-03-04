package ua.cinemabooking.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by RATIBOR on 04.02.2017.
 */
@Entity
public class BillOrder implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private LocalDateTime dataTime;

    private BigDecimal moneyCapacity;

//    @ManyToOne(targetEntity = Place.class, fetch = FetchType.EAGER)
//    private Place place;

    //то что было изменено, надо мониторить почему посыпались проблемы в тестах, только из-за одной строчки


    //    @ElementCollection(targetClass=HashSet.class)
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "billorder_place", joinColumns = @JoinColumn(name = "billorder_id", referencedColumnName = "id",
            columnDefinition = "LONGVARBINARY"),
            inverseJoinColumns = @JoinColumn(name = "place_id", referencedColumnName = "id", columnDefinition = "LONGVARBINARY"))
    private Set<Place> placeSet = new HashSet<>();

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
//    @OneToMany(mappedBy = "billOrder",fetch = FetchType.EAGER, cascade = CascadeType.ALL)


    @JsonManagedReference
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
