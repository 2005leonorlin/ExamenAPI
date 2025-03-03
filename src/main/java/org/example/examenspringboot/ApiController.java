package org.example.examenspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controlador REST para la gestión de ítems.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    /**
     * GET /items : Obtener todos los ítems.
     *
     * @return la lista de ítems
     */
    @Autowired
    private ItemRepository itemRepository;
    @GetMapping("/items")
    public List<Item> getAllVuelos() {
        return itemRepository.findAll();
    }


    /**
     * POST / : Crear un nuevo ítem.
     *
     * @param item el ítem a crear
     * @return el ResponseEntity con el estado 201 (Creado) y con el cuerpo el nuevo ítem,
     * o con el estado 409 (Conflicto) si el ítem ya existe
     */


    @PostMapping("/")
    public ResponseEntity<Item> create(@RequestBody Item item) {
        if (itemRepository.existsById(item.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            itemRepository.save(item);
            return new ResponseEntity<>(item, HttpStatus.CREATED);
        }
    }

    /**
     * DELETE /{id} : Eliminar el ítem con el id especificado.
     *
     * @param id el id del ítem a eliminar
     * @param token el token de autorización
     * @return el ResponseEntity con el estado 204 (Sin Contenido) si el ítem es eliminado,
     * o con el estado 401 (No Autorizado) si el token es inválido,
     * o con el estado 404 (No Encontrado) si el ítem no existe
     */


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id, @RequestParam String token) {
        if (!"1234".equals(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * GET /{id} : Obtener el ítem con el id especificado.
     *
     * @param id el id del ítem a recuperar
     * @return el ResponseEntity con el estado 200 (OK) y con el cuerpo el ítem,
     * o con el estado 404 (No Encontrado) si el ítem no existe
     */

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
        return itemRepository.findById(id)
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET /category/{category} : Obtener ítems por categoría.
     *
     * @param category la categoría de los ítems a recuperar
     * @return la lista de ítems en la categoría especificada
     */

    @GetMapping("/category/{category}")
    public List<Item> getItemsByCategory(@PathVariable String category) {
        return itemRepository.findByCategory(category);
    }
    /**
     * GET /stadistics : Obtener estadísticas de la tienda.
     *
     * @param minRate la puntuación mínima para filtrar ítems con alta puntuación (opcional)
     * @return el ResponseEntity con el estado 200 (OK) y con el cuerpo las estadísticas
     */

    @GetMapping("/stadistics")
    public ResponseEntity<?> getStoreStats(@RequestParam(required = false) Double minRate) {
        long totalItems = itemRepository.count();
        List<Item> highRatedItems = (minRate != null) ? itemRepository.findByRateGreaterThan(minRate) : List.of();

        return ResponseEntity.ok().body("Total de ítems: " + totalItems + ", Ítems con puntuación mayor a " + minRate + ": " + highRatedItems.size());
    }
}

