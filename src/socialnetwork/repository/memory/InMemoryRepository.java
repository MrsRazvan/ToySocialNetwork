package socialnetwork.repository.memory;

import socialnetwork.domain.Entity;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.repoExceptions.DuplicatedIDError;
import socialnetwork.repository.repoExceptions.EntityNotFoundError;

import java.util.*;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {

    private final Validator<E> validator;
    protected Map<ID,E> entities;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities = new HashMap<ID,E>();
    }

    @Override
    public E findOneById(ID id){
        if (id == null)
            throw new IllegalArgumentException("ID must be not null");
        E found  = entities.get(id);
        /*if(found == null)
            throw  new EntityNotFoundError("Entity was not found!");*/
        return found;
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
        //List<E> lsit = new ArrayList<E>(entities.values());
    }

    @Override
    public void save(E entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity must be not null");
        validator.validate(entity);
        if( entities.get(entity.getId()) != null)
            throw new DuplicatedIDError("ID already exists!");
        entities.put(entity.getId(),entity);
    }

    @Override
    public E findOneByOtherAttributes(List<Object> args) {
        return null;
    }

    @Override
    public void delete(ID id) {
        if(id == null)
            throw new IllegalArgumentException("ID must not be null!");
        E rez = entities.remove(id);
        if(rez == null)
            throw new EntityNotFoundError("ID does not exist!");
    }

    @Override
    public void update(E entity) {

        if(entity == null)
            throw new IllegalArgumentException("Entity must be not null!");
        validator.validate(entity);
        E searched = entities.get(entity.getId());
        if(searched == null)
            throw new EntityNotFoundError("Entity does not exist!");
        entities.put(entity.getId(), entity);
    }

    @Override
    public E findOne(ID id){
        if (id==null)
            throw new IllegalArgumentException("ID must be not null");
        return entities.get(id);
    }

}