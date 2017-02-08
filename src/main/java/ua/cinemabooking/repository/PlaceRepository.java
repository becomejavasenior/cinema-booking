package ua.cinemabooking.repository;

import org.springframework.stereotype.Repository;
import ua.cinemabooking.model.Place;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by RATIBOR on 04.02.2017.
 */
@Repository
public interface PlaceRepository extends CrudRepository<Place,Long> {

}
