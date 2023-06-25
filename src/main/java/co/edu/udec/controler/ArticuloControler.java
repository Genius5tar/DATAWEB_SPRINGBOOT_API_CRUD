/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udec.controler;

import co.edu.udec.Services.ArticuloService;
import co.edu.udec.model.Articulo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PEDRO DE HORTA
 */

@RestController
@RequestMapping("api/Articulos")
public class ArticuloControler {
        
    @Autowired
    private ArticuloService artService;
    
    @GetMapping("/All")
    public List<Articulo> readAll(){
    return artService.readAll();
    }
    
    @GetMapping("/Find/{IdArt}")
    public Articulo readbyid(@PathVariable("IdArt") Integer IdArt) throws Exception{
    return artService.readbyid(IdArt);    
    }
    
    
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Articulo art) {
    Map<String, Object> json = new HashMap<>();
        try {
            artService.create(art);
            long total = artService.contarArticulos();
            json.put("resultado", "insertado");
            json.put("total", total);
            return ResponseEntity.ok().body(json);
        } catch (Exception ex) {
            json.put("error", ex.getMessage());
            return ResponseEntity.ok().body(json);
        }
    }
    
    @PutMapping("/Edit/{IdArt}")
    public ResponseEntity<?> update(@PathVariable("IdArt") Integer IdArt, @RequestBody Articulo art) {
        Map<String, Object> json = new HashMap<>();
        try {
            if (artService.existsById(IdArt)) {
                artService.update(art);
                long total = artService.contarArticulos();
                json.put("resultado", "actualizado");
                json.put("total", total);
                return ResponseEntity.ok(json);
            } else {
                json.put("error", "No se encontró el Articulo con el ID especificado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
            }
        } catch (Exception ex) {
            json.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(json);
        }
    }
    
    @DeleteMapping("/{IdArt}")
    public ResponseEntity<?> delete(@PathVariable("IdArt") Integer IdArt) {
    Map<String, Object> json = new HashMap<>();
        try {
            artService.delete(IdArt);
            long total = artService.contarArticulos();
            json.put("resultado", "El Articulo con ID: "+IdArt+" EliminaDo exitosamente");
            json.put("total", total);
            return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            json.put("error", ex.getMessage());
            return new ResponseEntity<>(json, HttpStatus.NOT_FOUND);

        }  
    }
    
      
    
    

}

