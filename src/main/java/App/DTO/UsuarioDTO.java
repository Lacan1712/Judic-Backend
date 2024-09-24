package App.DTO;

import Database.Entities.Role;

import java.time.LocalDateTime;

public record UsuarioDTO(Integer id, String nome, String email, String senha, String cpf, String telefone, String endereco, LocalDateTime dataCriacao, Role role, Boolean ativo) {
}
