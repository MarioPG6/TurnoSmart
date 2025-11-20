package jwt.demo.jwt.cliente;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jwt.demo.jwt.cliente.DTO.CambiarEstadoDTO;
import jwt.demo.jwt.cliente.DTO.CrearCitaDTO;
import jwt.demo.jwt.cliente.DTO.EditarCitaDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/citas")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CitaController {

    private final CitaService citaService;

    @PostMapping("/crear")
    public Cita crearCita(@RequestBody CrearCitaDTO dto) {
        return citaService.crearCita(dto);
    }

    @GetMapping("/cliente/{id}")
    public List<Cita> listarPorCliente(@PathVariable Integer id) {
        return citaService.listarPorCliente(id);
    }

    @GetMapping("/negocio/{id}")
    public List<Cita> listarPorNegocio(@PathVariable Integer id) {
        return citaService.listarPorNegocio(id);
    }

    @PutMapping("/{id}/estado")
    public Cita cambiarEstado(
            @PathVariable Integer id,
            @RequestBody CambiarEstadoDTO dto) {
        return citaService.cambiarEstado(id, dto.getEstado());
    }

    @PutMapping("/{id}/editar")
    public Cita editarCita(
            @PathVariable Integer id,
            @RequestBody EditarCitaDTO dto) {
        return citaService.editarCita(id, dto);
    }

}