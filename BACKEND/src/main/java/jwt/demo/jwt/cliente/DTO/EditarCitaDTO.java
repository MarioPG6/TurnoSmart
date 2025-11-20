package jwt.demo.jwt.cliente.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class EditarCitaDTO {
    private String razon;
    private LocalDate fecha;
    private LocalTime hora;
}
