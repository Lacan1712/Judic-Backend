package App.Controllers;

import App.DTO.UsuarioDTO;
import App.Services.Authentication.AuthUserService;
import App.Services.DTO.CredenciasLoginDTO.CredenciaisDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Database.Entities.Usuarios;
import Database.Services.UsuarioService;
import Utils.AlgorithmsUtil.RSAKeyUtil;
import Utils.EnviromentUtil.EnvUtil;
import Utils.JWTUtil.JwtUtil;
import Utils.PasswordHashingUtil.PasswordHashingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author rodrigo
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthUserService authUserService;

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(RSAKeyUtil rsaKeyUtil) {
        this.jwtUtil = new JwtUtil(rsaKeyUtil); // Injetando a implementação de RSAKeyUtil no construtor de JwtUtil
    }

    @PostMapping("/login")
    public String getString(@RequestBody CredenciaisDTO credenciaisDTO) {
        return authUserService.logarUsuario(credenciaisDTO.username(), credenciaisDTO.password())
                .map(usuario -> "usuário logado: " + usuario.getNome())
                .orElse("Usuário ou senha inválido");
    }

    @GetMapping("/users")
    public List<Usuarios> getAllUsers(){
        return usuarioService.findAll();
    }

    @PostMapping("/create")
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

    @PostMapping("/teste")
    public String token(){
        Map<String,Object> retorno = new HashMap<>();
        retorno.put("nome","Rodrigo Lacan Barbosa Costa");

        // Usando a instância injetada de jwtUtil
        return jwtUtil.generateJWTWithClaims(retorno);
    }
}
