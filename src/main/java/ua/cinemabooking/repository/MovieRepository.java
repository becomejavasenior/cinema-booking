package ua.cinemabooking.repository;

import ua.cinemabooking.model.Movie;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by RATIBOR on 04.02.2017.
 */
public interface MovieRepository extends CrudRepository<Movie,Long> {
}
