package jwt.demo.jwt.Administrador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/negocios")
@CrossOrigin(origins = "*") 
public class NegocioController {

    @Autowired
    private NegocioService negocioService;

    // Crear negocio
    @PostMapping("/crear/{userId}")
    public ResponseEntity<Negocio> crearNegocio(@PathVariable Integer userId, @RequestBody Negocio negocio) {
        Negocio nuevo = negocioService.crearNegocio(userId, negocio);
        return ResponseEntity.ok(nuevo);
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Negocio>> listarTodos() {
        return ResponseEntity.ok(negocioService.listarNegocios());
    }

    // Listar por usuario
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Negocio>> listarPorUsuario(@PathVariable Integer userId) {
        return ResponseEntity.ok(negocioService.listarPorUsuario(userId));
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Negocio> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(negocioService.obtenerPorId(id));
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Negocio> actualizar(@PathVariable Integer id, @RequestBody Negocio negocio) {
        return ResponseEntity.ok(negocioService.actualizarNegocio(id, negocio));
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        negocioService.eliminarNegocio(id);
        return ResponseEntity.noContent().build();
    }
}
