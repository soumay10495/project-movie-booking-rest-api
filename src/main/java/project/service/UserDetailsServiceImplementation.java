package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.repository.UserDetailsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<project.entity.User> optionalUser = userDetailsRepository.loadUserByUsername(userName);

        project.entity.User user = optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("Invalid Username"));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            String prefix = role.getName();
            role.getPermissions().forEach(permission -> {
                grantedAuthorities.add(new SimpleGrantedAuthority(
                        prefix + "_" + permission.getName()));
            });
        });
        System.out.println(grantedAuthorities.toString());
        return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
