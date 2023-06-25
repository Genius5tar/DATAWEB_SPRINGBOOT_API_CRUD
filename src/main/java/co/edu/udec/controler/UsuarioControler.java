package co.edu.udec.controler;

import co.edu.udec.Services.UsuarioService;
import co.edu.udec.model.Usuario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("api/Usuarios")
public class UsuarioControler {
        
    @Autowired
    private UsuarioService usrService;
    
    @GetMapping("/All")
    public List<Usuario> readAll(){
    return usrService.readAll();    
    }
    
    @GetMapping("/Find/{IdUser}")
    public Usuario readbyid(@PathVariable("IdUser") Integer IdUser) throws Exception{
    return usrService.readbyid(IdUser);    
    }
    
    
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Usuario usr) {
    Map<String, Object> json = new HashMap<>();
        try {
            usrService.create(usr);
            long total = usrService.contarUsuario();
            json.put("resultado", "insertado");
            json.put("total", total);
            return ResponseEntity.ok().body(json);
        } catch (Exception ex) {
            json.put("error", ex.getMessage());
            return ResponseEntity.ok().body(json);
        }
    }
    
    @PutMapping("/Edit/{IdUsr}")
    public ResponseEntity<?> updateUsuario(@PathVariable("IdUsr") Integer IdUsr, @RequestBody Usuario usr) {
        Map<String, Object> json = new HashMap<>();
        try {
            if (usrService.existsById(IdUsr)) {
                usrService.update(usr);
                long total = usrService.contarUsuario();
                json.put("resultado", "actualizado");
                json.put("total", total);
                return ResponseEntity.ok(json);
            } else {
                json.put("error", "No se encontró el usuario con el ID especificado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
            }
        } catch (Exception ex) {
            json.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(json);
        }
    }
    
    @DeleteMapping("/Delete/{IdUsr}")
    public ResponseEntity<?> delete(@PathVariable("IdUsr") Integer IdUsr) {
    Map<String, Object> json = new HashMap<>();
        try {
            usrService.delete(IdUsr);
            long total = usrService.contarUsuario();
            json.put("resultado", "El Usuario con ID: "+IdUsr+" Eliminado exitosamente");
            json.put("total", total);
            return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            json.put("error", ex.getMessage());
            return new ResponseEntity<>(json, HttpStatus.NOT_FOUND);

        }  
    }
    

}
