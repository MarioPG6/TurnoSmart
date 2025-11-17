package jwt.demo.jwt.cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findByClienteId(Integer clienteId);
    List<Cita> findByNegocioId(Integer negocioId);
}