package jwt.demo.jwt.cliente;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jwt.demo.jwt.User.User;
import jwt.demo.jwt.User.UserRepository;
import jwt.demo.jwt.administrador.Negocio;
import jwt.demo.jwt.administrador.NegocioRepository;
import jwt.demo.jwt.cliente.DTO.CrearCitaDTO;
import jwt.demo.jwt.cliente.DTO.EditarCitaDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CitaService {

    private final CitaRepository citaRepo;
    private final UserRepository userRepo;
    private final NegocioRepository negocioRepo;

    // Crear cita
    @SuppressWarnings("null")
    public Cita crearCita(CrearCitaDTO dto) {

        @SuppressWarnings("null")
        User cliente = userRepo.findById(dto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (!cliente.getRole().name().equals("CLIENTE")) {
            throw new RuntimeException("Solo usuarios con rol CLIENTE pueden agendar citas");
        }

        Negocio negocio = negocioRepo.findById(dto.getIdNegocio())
                .orElseThrow(() -> new RuntimeException("Negocio no encontrado"));

        Cita cita = Cita.builder()
                .cliente(cliente)
                .negocio(negocio)
                .razon(dto.getRazon())
                .fecha(dto.getFecha())
                .hora(dto.getHora())
                .estado(EstadoCita.PENDIENTE)
                .build();

        return citaRepo.save(cita);
    }

    public List<Cita> listarPorCliente(Integer idCliente) {
        return citaRepo.findByClienteId(idCliente);
    }

    public List<Cita> listarPorNegocio(Integer idNegocio) {
        return citaRepo.findByNegocioId(idNegocio);
    }

    @SuppressWarnings("null")
    public Cita cambiarEstado(Integer idCita, EstadoCita nuevoEstado) {
        Cita cita = citaRepo.findById(idCita)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        cita.setEstado(nuevoEstado);
        return citaRepo.save(cita);
    }

    @SuppressWarnings("null")
    public Cita editarCita(Integer idCita, EditarCitaDTO dto) {
        Cita cita = citaRepo.findById(idCita)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (dto.getRazon() != null)
            cita.setRazon(dto.getRazon());
        if (dto.getFecha() != null)
            cita.setFecha(dto.getFecha());
        if (dto.getHora() != null)
            cita.setHora(dto.getHora());

        return citaRepo.save(cita);
    }

}