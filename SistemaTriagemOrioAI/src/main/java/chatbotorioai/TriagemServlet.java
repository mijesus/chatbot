package chatbotorioai;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class TriagemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Database.inicializarBanco();
            System.out.println("Banco inicializado com sucesso na TriagemServlet!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String telefone = request.getParameter("telefone");
        String email = request.getParameter("email");
        String nascimento = request.getParameter("nascimento");
        String sintomas = request.getParameter("sintomas");
        String rua = request.getParameter("rua");
        String numero = request.getParameter("numero");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String cep = request.getParameter("cep");

        String prioridade = classificarPrioridade(sintomas);

        try (Connection conn = Database.connect()) {
            conn.setAutoCommit(false);

            // Inserir paciente
            PreparedStatement pacienteStmt = conn.prepareStatement(
                "INSERT INTO paciente (nome, cpf, telefone, email, nascimento) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            pacienteStmt.setString(1, nome);
            pacienteStmt.setString(2, cpf);
            pacienteStmt.setString(3, telefone);
            pacienteStmt.setString(4, email);
            pacienteStmt.setString(5, nascimento);
            pacienteStmt.executeUpdate();

            ResultSet generatedKeys = pacienteStmt.getGeneratedKeys();
            int pacienteId = 0;
            if (generatedKeys.next()) {
                pacienteId = generatedKeys.getInt(1);
            }

            // Inserir endereço
            PreparedStatement enderecoStmt = conn.prepareStatement(
                "INSERT INTO endereco (paciente_id, rua, numero, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            enderecoStmt.setInt(1, pacienteId);
            enderecoStmt.setString(2, rua);
            enderecoStmt.setString(3, numero);
            enderecoStmt.setString(4, bairro);
            enderecoStmt.setString(5, cidade);
            enderecoStmt.setString(6, estado);
            enderecoStmt.setString(7, cep);
            enderecoStmt.executeUpdate();

            // Inserir triagem
            PreparedStatement triagemStmt = conn.prepareStatement(
                "INSERT INTO triagem (sintomas_relato, nivel_prioridade, mensagem_id) VALUES (?, ?, NULL)"
            );
            triagemStmt.setString(1, sintomas);
            triagemStmt.setString(2, prioridade);
            triagemStmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Triagem realizada com sucesso!</h2>");
        out.println("<p>Paciente: " + nome + "</p>");
        out.println("<p>Prioridade: <strong>" + prioridade + "</strong></p>");
        out.println("<a href='index.html'>Voltar</a>");
        out.println("</body></html>");
    }

    private String classificarPrioridade(String texto) {
        texto = texto.toLowerCase();
        if (texto.contains("dor no peito") || texto.contains("falta de ar") || texto.contains("desmaio")) {
            return "Alta";
        } else if (texto.contains("febre") || texto.contains("tosse") || texto.contains("dor de cabeça")) {
            return "Média";
        } else {
            return "Baixa";
        }
    }
}
