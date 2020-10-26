package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.entity.Ticket;
import project.service.GenericService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
public class TicketController {
    @Autowired
    GenericService<Ticket> service;

    @PostConstruct
    public void loadBeforeRequestMapping() {
        service.settClass(Ticket.class);
    }

    @GetMapping("/ticket")
    public List<Ticket> fetchEntityList() {
        return service.fetchEntityList();
    }

    @GetMapping("/ticket/{id}")
    public Ticket fetchEntity(@PathVariable int id) {
        Ticket ticket = service.fetchEntity(id);
        if (ticket == null)
            throw new EntityNotFoundException("Invalid Ticket ID : " + id);
        return ticket;
    }

    @PostMapping("/ticket")
    public String createEntity(@RequestBody Ticket ticket) {
        ticket.setId(0);
        System.out.println(ticket.getBookingDate());
        service.saveEntity(ticket);
        return "Ticket added with ID : " + ticket.getId();
    }

    @PutMapping("/ticket")
    public String updateEntity(@RequestBody Ticket ticket) {
        if (service.fetchEntity(ticket.getId()) == null)
            throw new EntityNotFoundException("Invalid Ticket ID : " + ticket.getId());
        System.out.println(ticket.getBookingDate());
        service.saveEntity(ticket);
        return "Ticket with ID : " + ticket.getId() + " updated";
    }

    @DeleteMapping("/ticket/{id}")
    public String removeEntity(@PathVariable int id) {
        Ticket ticket = service.fetchEntity(id);
        if (ticket == null)
            throw new EntityNotFoundException("Invalid Ticket ID : " + id);
        service.removeEntity(ticket);
        return "Ticket with ID : " + id + " removed";
    }
}
