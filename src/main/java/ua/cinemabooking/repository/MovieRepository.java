package ua.cinemabooking.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.cinemabooking.model.Movie;

/**
 * Created by RATIBOR on 04.02.2017.
 */

@Repository
public interface MovieRepository extends CrudRepository<Movie,Long> , JpaSpecificationExecutor<Movie>{

    @Query("select max(m.id) from Movie m")
    String getMaxCode();
    @Query("select min(m.id) from Movie m")
    String getMinCode();
}

