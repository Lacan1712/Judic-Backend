package App.Filters.Auth;

import Utils.AlgorithmsUtil.RSAKeyUtil;
import Utils.JWTUtil.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthJWTFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthJWTFilter(RSAKeyUtil rsaKeyUtil) {
        this.jwtUtil = new JwtUtil(rsaKeyUtil);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Recupera o cabeçalho Authorization
        String authorizationHeader = request.getHeader("Authorization");
        String roleList;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            roleList = this.jwtUtil.getRolesFromToken(token);
            request.setAttribute("role",roleList);
        }
        // Continua com a cadeia de filtros
        filterChain.doFilter(request, response);
    }

    // Método utilitário para verificar se o usuário tem uma role específica
    public static boolean hasRole(HttpServletRequest request, String roleName) {
        String roles = (String) request.getAttribute("role");
        return roles != null && roles.contains(roleName);
    }
}
