package Database.Services;

import Database.Entities.Usuarios;
import org.springframework.stereotype.Service;
import Database.Repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuarios createUsuario(Usuarios usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuarios> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuarios> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuarios> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuarios> updateUsuario(Integer id, Usuarios usuarioDetails) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioDetails.getNome());
            usuario.setEmail(usuarioDetails.getEmail());
            usuario.setSenha(usuarioDetails.getSenha());
            usuario.setCpf(usuarioDetails.getCpf());
            usuario.setTelefone(usuarioDetails.getTelefone());
            usuario.setEndereco(usuarioDetails.getEndereco());
            usuario.setDataCriacao(usuarioDetails.getDataCriacao());
            usuario.setRole(usuarioDetails.getRole());
            usuario.setAtivo(usuarioDetails.getAtivo());
            return usuarioRepository.save(usuario);
        });
    }

    public boolean deleteUsuario(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Boolean existsByCpf(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }
}