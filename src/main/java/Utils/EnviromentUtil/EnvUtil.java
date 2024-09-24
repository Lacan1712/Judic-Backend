package Utils.EnviromentUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnvUtil {
    public static String FILE_PATH = "/home/rodrigo/Documentos Local/Projetos/Spring Boot/Judic-Backend/src/main/java/ENV.env";
    private static final Map<String, String> envVariables = new HashMap<>();
    static {
        try {
            loadEnv();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFILE_PATH() {
        return FILE_PATH;
    }

    public void setFILE_PATH(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
    }

    private static void loadEnv() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Ignora linhas em branco e comentários
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // Divide as variáveis no formato chave=valor
                String[] keyValue = line.split("=", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    envVariables.put(key, value);
                }
            }
        }
    }

    // Metodo estático para obter uma variável de ambiente
    public static String get(String key) {
        return envVariables.get(key);
    }
}