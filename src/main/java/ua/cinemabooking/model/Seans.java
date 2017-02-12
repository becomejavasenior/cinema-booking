package ua.cinemabooking.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by RATIBOR on 04.02.2017.
 */
@Entity
public class Seans {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime start;
    private LocalDateTime end;

    @ManyToOne(targetEntity = Movie.class)
    private Movie movie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
