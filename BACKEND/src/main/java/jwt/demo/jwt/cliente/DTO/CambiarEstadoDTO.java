package jwt.demo.jwt.cliente.DTO;

import jwt.demo.jwt.cliente.EstadoCita;
import lombok.Data;

@Data
public class CambiarEstadoDTO {
    private EstadoCita estado;
}