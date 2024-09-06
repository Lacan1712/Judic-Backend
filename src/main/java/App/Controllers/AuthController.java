package App.Controllers;

import App.Services.DTO.CredenciasLoginDTO.CredenciaisDTO;
import java.util.HashMap;
import java.util.Map;

import Database.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author rodrigo
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
            
    public AuthController() {
    }

    @PostMapping("/login")
    public Map<String,Object> getString(@RequestBody CredenciaisDTO credenciaisDTO) {
        Map<String, Object> token = new HashMap<>();
        token.put("token", "12345");
        return token;
    }
}