package App.Controllers;


import App.DTO.UsuarioDTO;
import App.Filters.Auth.AuthJWTFilter;
import Database.Entities.Usuarios;
import Database.Services.UsuarioService;
import Utils.PasswordHashingUtil.PasswordHashingUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersController {

    private final UsuarioService usuarioService;
    private final AuthJWTFilter authJWTFilter;

    // Injeção de dependências via construtor
    public UsersController(UsuarioService usuarioService, AuthJWTFilter authJWTFilter) {
        this.usuarioService = usuarioService;
        this.authJWTFilter = authJWTFilter;
    }
    @PostMapping("/users/create")
    public Usuarios getAllUsers(@RequestBody UsuarioDTO usuarioDTO){
        String hashedPassword = PasswordHashingUtil.hashPassword(usuarioDTO.senha());
        Usuarios NovoUsuario = new Usuarios(usuarioDTO.id(),
                usuarioDTO.nome(),
                usuarioDTO.email(),
                hashedPassword,
                usuarioDTO.cpf(),
                usuarioDTO.telefone(),
                usuarioDTO.endereco(),
                usuarioDTO.dataCriacao(),
                usuarioDTO.role(),
                usuarioDTO.ativo());

        return usuarioService.createUsuario(NovoUsuario);
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<Usuarios>> getAllUsers(HttpServletRequest request) {
        // Verifica se o usuário tem a role específica
        if (AuthJWTFilter.hasRole(request, "ROLE_ADMIN")) {
            List<Usuarios> usuarios = usuarioService.findAll();
            return ResponseEntity.ok(usuarios); // Retorna a lista de usuários com status 200 OK
        }

        // Caso o usuário não tenha a role necessária, retorna 403 Forbidden
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
    @GetMapping("/secured-endpoint")
    public ResponseEntity<String> securedEndpoint(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader == null) {
            return ResponseEntity.status(401).body("Missing Authorization header");
        }

        // Aqui você pode validar o token de autenticação (se necessário)
        return ResponseEntity.ok("Authorization header is present: " + authorizationHeader);
    }


}
