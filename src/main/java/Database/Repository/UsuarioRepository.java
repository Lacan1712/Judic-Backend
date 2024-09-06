package Database.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Database.Entities.Usuarios;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
    Usuarios findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
