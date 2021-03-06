package win.doyto.query.core;

import win.doyto.query.entity.Persistable;

import java.io.Serializable;
import java.util.List;

/**
 * DataAccess
 *
 * @author f0rb
 */
public interface DataAccess<E extends Persistable<I>, I extends Serializable, Q> {

    List<E> query(Q query);

    long count(Q query);

    E get(I id);

    default E get(E e) {
        return get(e.getId());
    }

    int delete(I id);

    default int delete(E e) {
        return delete(e.getId());
    }

    int delete(Q query);

    void create(E e);

    void update(E e);

    void patch(E e);

    /**
     * force to get a new entity object from database
     *
     * @param id entity id
     * @return a new entity object
     */
    E fetch(I id);
}
