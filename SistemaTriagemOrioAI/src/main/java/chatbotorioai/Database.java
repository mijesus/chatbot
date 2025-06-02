package chatbotorioai;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    // --> Corrigido o nome do banco aqui
    private static final String URL = "jdbc:mysql://localhost:3306/orioai?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Luci@123"; // <-- sua senha está correta

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void inicializarBanco() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            // Criação das tabelas, se necessário
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS paciente (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "nome VARCHAR(100) NOT NULL," +
                            "cpf VARCHAR(11) UNIQUE NOT NULL," +
                            "telefone VARCHAR(11) NOT NULL," +
                            "email VARCHAR(100) NOT NULL," +
                            "nascimento DATE NOT NULL" +
                            ")");

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS endereco (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "paciente_id INT NOT NULL," +
                            "rua VARCHAR(100) NOT NULL," +
                            "numero VARCHAR(10) NOT NULL," +
                            "bairro VARCHAR(50) NOT NULL," +
                            "cidade VARCHAR(50) NOT NULL," +
                            "estado VARCHAR(2) NOT NULL," +
                            "cep VARCHAR(8) NOT NULL," +
                            "FOREIGN KEY (paciente_id) REFERENCES paciente(id) ON DELETE CASCADE" +
                            ")");

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS plano_saude (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "paciente_id INT NOT NULL," +
                            "nome VARCHAR(100) NOT NULL," +
                            "numero_cartao VARCHAR(30) NOT NULL," +
                            "validade DATE NOT NULL," +
                            "FOREIGN KEY (paciente_id) REFERENCES paciente(id) ON DELETE CASCADE" +
                            ")");

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS agendamento (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "paciente_id INT NOT NULL," +
                            "data_consulta DATE NOT NULL," +
                            "hora_consulta VARCHAR(5) NOT NULL," +
                            "FOREIGN KEY (paciente_id) REFERENCES paciente(id) ON DELETE CASCADE" +
                            ")");

            System.out.println("Banco de dados inicializado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
