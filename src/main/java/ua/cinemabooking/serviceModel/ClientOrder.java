package ua.cinemabooking.serviceModel;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.util.Set;

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
    private Set<Long> placeIdSet;

    public ClientOrder(){

    }

    public ClientOrder(String email, Long seansId, Set<Long> placeIdSet) {
        this.email = email;
        this.seansId = seansId;
        this.placeIdSet = placeIdSet;
    }

    public Set<Long> getPlaceIdSet() {
        return placeIdSet;
    }

    public void setPlaceId(Set<Long> placeIdSet) {
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


}
