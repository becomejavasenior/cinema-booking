package ua.cinemabooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by RATIBOR on 04.02.2017.
 */
@Entity
public class Place implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private Integer x;
    private Integer y;

    //    @ElementCollection(targetClass=HashSet.class)
    @ManyToMany(mappedBy = "placeSet")
    private Set<BillOrder> billOrders = new HashSet<>();


    @JsonBackReference
    public Set<BillOrder> getBillOrders() {
        return billOrders;
    }

    public void setBillOrders(Set<BillOrder> billOrders) {
        this.billOrders = billOrders;
    }

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
