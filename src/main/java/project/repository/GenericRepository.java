package project.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope("prototype")
public class GenericRepository<T> {
    Class<T> tClass;
    @Autowired
    private SessionFactory sessionFactory;

    public void settClass(Class<T> tClass) {
        this.tClass = tClass;
    }

    public List<T> fetchEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from " + tClass.getSimpleName() + " order by id");
        return query.getResultList();
    }

    public T fetchEntity(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(tClass, id);
    }

    public void saveEntity(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
    }

    public void removeEntity(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(entity);
    }
}
