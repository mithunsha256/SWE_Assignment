package storage;

import java.io.IOException;
import java.util.List;

/**
 * Generic Storage interface representing a repository contract.
 * 
 * OOP Concepts Demonstrated:
 * 1. Interface & Abstraction: Separates data persistence operations from business services.
 * 2. Generics: Type parameter T allows reusable implementations for any entity.
 * 3. Polymorphism: Used by services through the Storage<T> interface reference.
 */
public interface Storage<T> {
    /**
     * Persists a list of entities into physical storage.
     * @param items list of items to save
     * @throws IOException if an I/O error occurs
     */
    void save(List<T> items) throws IOException;

    /**
     * Loads entities from physical storage.
     * @return list of loaded items
     * @throws IOException if an I/O error occurs
     */
    List<T> load() throws IOException;
}

