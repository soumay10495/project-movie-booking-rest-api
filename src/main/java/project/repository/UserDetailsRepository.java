package project.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.entity.User;

import java.util.Optional;

@Repository
public class UserDetailsRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public Optional<User> loadUserByUsername(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User where email=:email");
        query.setParameter("email", email);
        return query.uniqueResultOptional();
    }
}
