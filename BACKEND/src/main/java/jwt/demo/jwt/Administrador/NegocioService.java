package jwt.demo.jwt.Administrador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jwt.demo.jwt.User.User;
import jwt.demo.jwt.User.UserRepository;

@Service
public class NegocioService {

    @Autowired
    private NegocioRepository negocioRepository;

    @Autowired
    private UserRepository userRepository;

    // Crear un nuevo negocio
    public Negocio crearNegocio(Integer userId, Negocio negocio) {
        @SuppressWarnings("null")
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        negocio.setUser(user);
        return negocioRepository.save(negocio);
    }

    // Obtener todos los negocios
    public List<Negocio> listarNegocios() {
        return negocioRepository.findAll();
    }

    // Obtener negocios por usuario
    public List<Negocio> listarPorUsuario(Integer userId) {
        return negocioRepository.findByUserId(userId);
    }

    // Buscar un negocio por ID
    @SuppressWarnings("null")
    public Negocio obtenerPorId(Integer id) {
        return negocioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Negocio no encontrado"));
    }

    // Actualizar un negocio
    public Negocio actualizarNegocio(Integer id, Negocio datosActualizados) {
        Negocio negocio = obtenerPorId(id);

        negocio.setNombreNegocio(datosActualizados.getNombreNegocio());
        negocio.setDescripcion(datosActualizados.getDescripcion());
        negocio.setDireccion(datosActualizados.getDireccion());
        negocio.setHorario(datosActualizados.getHorario());
        negocio.setTelefono(datosActualizados.getTelefono());

        return negocioRepository.save(negocio);
    }

    // Eliminar un negocio
    @SuppressWarnings("null")
    public void eliminarNegocio(Integer id) {
        negocioRepository.deleteById(id);
    }
}
