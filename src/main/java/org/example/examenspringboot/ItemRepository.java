package org.example.examenspringboot;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
/**
 * Representa un ítem en la tienda.
 */
public interface ItemRepository extends MongoRepository<Item, String> {

    /**
     * Buscar ítems por categoría.
     *
     * @param category la categoría de los ítems a buscar
     * @return la lista de ítems en la categoría especificada
     */
     public List<Item> findByCategory(String category);

    /**
     * Buscar ítems con una puntuación mayor a un valor dado.
     *
     * @param rate la puntuación mínima de los ítems a buscar
     * @return la lista de ítems con una puntuación mayor a la especificada
     */
    public List<Item> findByRateGreaterThan(double rate);
}