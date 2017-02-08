package ua.cinemabooking.repository;

import org.springframework.stereotype.Repository;
import ua.cinemabooking.model.Movie;
import ua.cinemabooking.model.Seans;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by RATIBOR on 04.02.2017.
 */
@Repository
public interface SeansRepository extends CrudRepository<Seans,Long> {
    List<Seans> findByMovie(Movie movie);
}
