package jwt.demo.jwt.administrador;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, Integer> {
    
    // Buscar negocios por id de usuario
    List<Negocio> findByUserId(Integer userId);
}
