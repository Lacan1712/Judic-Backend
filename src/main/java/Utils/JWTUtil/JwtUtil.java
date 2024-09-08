package Utils.JWTUtil;


import Utils.AlgorithmsUtil.RSALoadKeyUtil;
import Utils.EnviromentUtil.EnvUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    RSAPrivateKey privateKey;
    RSAPublicKey publicKey;

    public JwtUtil() {
        publicKey = (RSAPublicKey) RSALoadKeyUtil.loadPublicKey();
        privateKey = (RSAPrivateKey) RSALoadKeyUtil.loadPrivateKey();
    }

    public String generateJWTWithClaims(Map<String, Object> claims) {
        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);

        // Cria o token JWT
        return JWT.create()
                .withIssuer("judic-bakend")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hora de expiração
                .withPayload(claims) // Adiciona as claims
                .sign(algorithm);
    }

    public void getSubjectsFromToken(String token) {

    }

}
