package app.persistence;

import java.util.List;

public interface GenericDAO<T>
{
    T create(T t);
    T read(T t);
    T update(T t);
    T delete(T t);
    List<T> findAll();
    T findById(T t);
}


