package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.entity.Show;
import project.service.GenericService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
public class ShowController {
    @Autowired
    GenericService<Show> service;

    @PostConstruct
    public void loadBeforeRequestMapping() {
        service.settClass(Show.class);
    }

    @GetMapping("/show")
    public List<Show> fetchEntityList() {
        return service.fetchEntityList();
    }

    @GetMapping("/show/{id}")
    public Show fetchEntity(@PathVariable int id) {
        Show show = service.fetchEntity(id);
        if (show == null)
            throw new EntityNotFoundException("Invalid Show ID : " + id);
        return show;
    }

    @PostMapping("/show")
    public String createEntity(@RequestBody Show show) {
        show.setId(0);
        service.saveEntity(show);
        return "Show added with ID : " + show.getId();
    }

    @PutMapping("/show")
    public String updateEntity(@RequestBody Show show) {
        if (service.fetchEntity(show.getId()) == null)
            throw new EntityNotFoundException("Invalid Show ID : " + show.getId());
        service.saveEntity(show);
        return "Show with ID : " + show.getId() + " updated";
    }

    @DeleteMapping("/show/{id}")
    public String removeEntity(@PathVariable int id) {
        Show show = service.fetchEntity(id);
        if (show == null)
            throw new EntityNotFoundException("Invalid Show ID : " + id);
        service.removeEntity(show);
        return "Show with ID : " + id + " removed";
    }
}
