package Database.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Database.Entities.Usuarios;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
    Optional<Usuarios> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByCpf(String cpf);
}
