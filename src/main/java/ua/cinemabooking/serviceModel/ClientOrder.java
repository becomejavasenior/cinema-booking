package ua.cinemabooking.serviceModel;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.util.List;

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
    private List<Long> placeIdSet;

    public ClientOrder(){

    }

    public ClientOrder(String email, Long seansId, List<Long> placeIdSet) {
        this.email = email;
        this.seansId = seansId;
        this.placeIdSet = placeIdSet;
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

    public List<Long> getPlaceIdSet() {
        return placeIdSet;
    }

    public void setPlaceIdSet(List<Long> placeIdSet) {
        this.placeIdSet = placeIdSet;
    }
}
