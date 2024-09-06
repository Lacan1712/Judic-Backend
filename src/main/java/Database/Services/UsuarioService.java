package Database.Services;

import Database.Entities.Usuarios;
import org.springframework.stereotype.Service;
import Database.Repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    public List<Usuarios> getAllUsuarios() {
        return userRepository.findAll();
    }
}
