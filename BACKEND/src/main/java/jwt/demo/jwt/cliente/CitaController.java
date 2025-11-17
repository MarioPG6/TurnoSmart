package jwt.demo.jwt.cliente;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import jwt.demo.jwt.cliente.DTO.CambiarEstadoDTO;
import jwt.demo.jwt.cliente.DTO.CrearCitaDTO;
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
            @RequestBody CambiarEstadoDTO dto
    ) {
        return citaService.cambiarEstado(id, dto.getEstado());
    }
}