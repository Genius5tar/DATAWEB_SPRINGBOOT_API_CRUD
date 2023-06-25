package co.edu.udec.Services;

import co.edu.udec.model.Articulo;
import co.edu.udec.repository.ArticuloRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author PEDRO DE HORTA
 */

@Service
public class ArticuloService {
    
    @Autowired
    private ArticuloRepository ArticuloRepository;
    
    public void create(Articulo art) throws Exception {
        if(ArticuloRepository.existsById(art.getId())){
            throw new Exception("El Articulo COD: "+ art.getId()+" Ya Existe en el sistema");     
      }
        ArticuloRepository.save(art);
    }
    
    public List<Articulo> readAll() {
       return ArticuloRepository.findAll();
    }
    
    public Articulo readbyid(Integer id)throws Exception{
        Articulo art =  ArticuloRepository.findById(id).orElse(null);
        if(art == null){
            throw new Exception("El Articulo ID: "+id+" No Existe en el sistema");     
        }
        return art;
    }
    
    public void update(Articulo art){
        ArticuloRepository.save(art);
    }
    
    public void delete(Integer id) throws Exception{
        Articulo art = readbyid(id);
        ArticuloRepository.deleteById(id);
    }

    public long contarArticulos (){
        return ArticuloRepository.count();
    }
    
    public boolean existsById(Integer id) throws Exception{
        return ArticuloRepository.existsById(id);
    }
            
}
