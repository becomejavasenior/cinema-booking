package ua.cinemabooking.serviceModel;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by macbookair on 12.02.17.
 */


public class ClientOrder implements Serializable{

    @NotNull
    @Email
    private String email;

    @NotNull
    private Long seansId;

    @NotNull
    private List<Long> placeIdList;

    public ClientOrder(){

    }

    public ClientOrder(String email, Long seansId, List<Long> placeIdList) {
        this.email = email;
        this.seansId = seansId;
        this.placeIdList = placeIdList;
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

    public List<Long> getPlaceIdList() {
        return placeIdList;
    }

    public void setPlaceIdList(List<Long> placeIdList) {
        this.placeIdList = placeIdList;
    }
}
