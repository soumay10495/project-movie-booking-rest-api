package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.entity.User;
import project.service.GenericService;
import project.service.UserDetailsServiceImplementation;

import javax.annotation.PostConstruct;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private GenericService<User> service;
    @Autowired
    private UserDetailsServiceImplementation userDetailsService;

    @PostConstruct
    public void loadBeforeRequestMapping() {
        service.settClass(User.class);
    }

    //@PreAuthorize("hasAuthority('EMPLOYEE_READ_ALL')")
    @GetMapping("/user")
    public List<User> fetchEntityList() {
        return service.fetchEntityList();
    }

    @GetMapping("/user/{id}")
    public User fetchEntity(@PathVariable int id) {
        User user = service.fetchEntity(id);
        if (user == null)
            throw new EntityNotFoundException("Invalid User ID : " + id);
        return user;
    }

    //@PreAuthorize("hasAuthority('ADMINISTRATOR_CREATE')")
    @PostMapping("/user")
    public String createEntity(@RequestBody User user) {
        try {
            if (userDetailsService.loadUserByUsername(user.getEmail()) != null)
                throw new EntityExistsException("Username already exists");
        } catch (UsernameNotFoundException e) {
        }
        user.setId(0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        service.saveEntity(user);
        return "User added with ID : " + user.getId();
    }

    //@PreAuthorize("hasAuthority('ADMINISTRATOR_UPDATE')")
    @PutMapping("/user")
    public String updateEntity(@RequestBody User user) {
        User user1 = service.fetchEntity(user.getId());
        if (user1 == null)
            throw new EntityNotFoundException("Invalid User ID : " + user.getId());
        try {
            if (!user1.getEmail().equals(user.getEmail()) &&
                    userDetailsService.loadUserByUsername(user.getEmail()) != null)
                throw new EntityExistsException("Username already exists");
        } catch (UsernameNotFoundException e) {
        }
        if (!user1.getPassword().equals(user.getPassword()))
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        service.saveEntity(user);
        return "User with ID : " + user.getId() + " updated";
    }

    //@PreAuthorize("hasAuthority('ADMINISTRATOR_DELETE')")
    @DeleteMapping("/user/{id}")
    public String removeEntity(@PathVariable int id) {
        User user = service.fetchEntity(id);
        if (user == null)
            throw new EntityNotFoundException("Invalid Entity ID : " + id);
        service.removeEntity(user);
        return "User with ID : " + id + " removed";
    }
}
