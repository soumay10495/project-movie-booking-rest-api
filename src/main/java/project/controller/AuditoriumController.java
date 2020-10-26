package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.entity.Auditorium;
import project.service.GenericService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
public class AuditoriumController {
    @Autowired
    GenericService<Auditorium> service;

    @PostConstruct
    public void loadBeforeRequestMapping() {
        service.settClass(Auditorium.class);
    }

    @GetMapping("/auditorium")
    public List<Auditorium> fetchEntityList() {
        return service.fetchEntityList();
    }

    @GetMapping("/auditorium/{id}")
    public Auditorium fetchEntity(@PathVariable int id) {
        Auditorium auditorium = service.fetchEntity(id);
        if (auditorium == null)
            throw new EntityNotFoundException("Invalid Auditorium ID : " + id);
        return auditorium;
    }

    @PostMapping("/auditorium")
    public String createEntity(@RequestBody Auditorium auditorium) {
        auditorium.setId(0);
        service.saveEntity(auditorium);
        return "Auditorium added with ID : " + auditorium.getId();
    }

    @PutMapping("/auditorium")
    public String updateEntity(@RequestBody Auditorium auditorium) {
        if (service.fetchEntity(auditorium.getId()) == null)
            throw new EntityNotFoundException("Invalid Auditorium ID : " + auditorium.getId());
        service.saveEntity(auditorium);
        return "Auditorium with ID : " + auditorium.getId() + " updated";
    }

    @DeleteMapping("/auditorium/{id}")
    public String removeEntity(@PathVariable int id) {
        Auditorium auditorium = service.fetchEntity(id);
        if (auditorium == null)
            throw new EntityNotFoundException("Invalid Auditorium ID : " + id);
        service.removeEntity(auditorium);
        return "Auditorium with ID : " + id + " removed";
    }
}
