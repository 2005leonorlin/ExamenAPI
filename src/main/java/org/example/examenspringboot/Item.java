package org.example.examenspringboot;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * Representa un ítem en la tienda.
 */
@Document(collection = "Item")
@Data
public class Item {
    private String id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private double rate;
    private int count;
}
