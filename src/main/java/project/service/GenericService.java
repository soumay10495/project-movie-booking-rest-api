package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.repository.GenericRepository;

import java.util.List;

@Service
@Transactional
@Scope("prototype")
public class GenericService<T> {
    @Autowired
    private GenericRepository<T> repository;

    public void settClass(Class<T> tClass) {
        repository.settClass(tClass);
    }

    public List<T> fetchEntityList() {
        return repository.fetchEntityList();
    }

    public T fetchEntity(int id) {
        return repository.fetchEntity(id);
    }

    public void saveEntity(T entity) {
        repository.saveEntity(entity);
    }

    public void removeEntity(T entity) {
        repository.removeEntity(entity);
    }
}
