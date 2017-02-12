package ua.cinemabooking.serviceModel;

import org.hibernate.validator.constraints.Email;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Created by macbookair on 12.02.17.
 */


public class ClientOrder {

    @NotNull
    @Email
    private String email;

    @NotNull
    private Long seansId;

    @NotNull
    private Long placeId;

    public ClientOrder(){

    }

    public ClientOrder(String email, Long seansId, Long placeId) {
        this.email = email;
        this.seansId = seansId;
        this.placeId = placeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSeansId() {
        return seansId;
    }

    public void setSeansId(Long seansId) {
        this.seansId = seansId;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }
}
