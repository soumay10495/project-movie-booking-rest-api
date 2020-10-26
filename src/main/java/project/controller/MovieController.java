package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.entity.Movie;
import project.service.GenericService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
public class MovieController {
    @Autowired
    GenericService<Movie> service;

    @PostConstruct
    public void loadBeforeRequestMapping() {
        service.settClass(Movie.class);
    }

    @GetMapping("/movie")
    public List<Movie> fetchEntityList() {
        return service.fetchEntityList();
    }

    @GetMapping("/movie/{id}")
    public Movie fetchEntity(@PathVariable int id) {
        Movie movie = service.fetchEntity(id);
        if (movie == null)
            throw new EntityNotFoundException("Invalid Movie ID : " + id);
        return movie;
    }

    @PostMapping("/movie")
    public String createEntity(@RequestBody Movie movie) {
        movie.setId(0);
        service.saveEntity(movie);
        return "Movie added with ID : " + movie.getId();
    }

    @PutMapping("/movie")
    public String updateEntity(@RequestBody Movie movie) {
        if (service.fetchEntity(movie.getId()) == null)
            throw new EntityNotFoundException("Invalid Movie ID : " + movie.getId());
        service.saveEntity(movie);
        return "Movie with ID : " + movie.getId() + " updated";
    }

    @DeleteMapping("/movie/{id}")
    public String removeEntity(@PathVariable int id) {
        Movie movie = service.fetchEntity(id);
        if (movie == null)
            throw new EntityNotFoundException("Invalid Movie ID : " + id);
        service.removeEntity(movie);
        return "Movie with ID : " + id + " removed";
    }
}
