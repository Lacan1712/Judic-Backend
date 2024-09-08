package Utils.AlgorithmsUtil;

import Utils.AlgorithmsUtil.Interfaces.CryptographyRSAInterface;
import Utils.EnviromentUtil.EnvUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RSAKeyUtil implements CryptographyRSAInterface<RSAPublicKey, RSAPrivateKey> {
    private final static String privateKeyPath = EnvUtil.get("PRIVATE_KEY_PATH");
    private final static String publicKeyPath = EnvUtil.get("PUBLIC_KEY_PATH");

    @Override
    public RSAPrivateKey loadPrivateKey() {
        try {
            String key = new String(Files.readAllBytes(Paths.get(privateKeyPath)));
            key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            // Decodificar o conteúdo Base64
            byte[] keyBytes = Base64.getDecoder().decode(key);

            // Converter os bytes para uma chave privada RSA
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);

        } catch (NoSuchFileException e) {
            System.err.println("Arquivo de chave não encontrado: " + privateKeyPath);
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algoritmo RSA não disponível: " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            System.err.println("Formato inválido para a chave privada: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao decodificar Base64: " + e.getMessage());
        } catch (Exception e) {
            // Tratamento genérico para qualquer outra exceção
            System.err.println("Erro desconhecido ao carregar a chave privada: " + e.getMessage());
            e.printStackTrace();  // Exibe o stack trace completo para depuração
        }
        return null;
    }

    @Override
    public RSAPublicKey loadPublicKey() {
        try {
            String key = new String(Files.readAllBytes(Paths.get(publicKeyPath)));

            key = key.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            // Decodificar o conteúdo Base64
            byte[] keyBytes = Base64.getDecoder().decode(key);

            // Converter os bytes para uma chave pública RSA
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(spec);

        } catch (NoSuchFileException e) {
            System.err.println("Arquivo de chave pública não encontrado: " + publicKeyPath);
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algoritmo RSA não disponível: " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            System.err.println("Formato inválido para a chave pública: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao decodificar Base64: " + e.getMessage());
        } catch (Exception e) {
            // Tratamento genérico para qualquer outra exceção
            System.err.println("Erro desconhecido ao carregar a chave pública: " + e.getMessage());
            e.printStackTrace();  // Exibe o stack trace completo para depuração
        }

        // Caso ocorra uma exceção, retorna null
        return null;
    }
}