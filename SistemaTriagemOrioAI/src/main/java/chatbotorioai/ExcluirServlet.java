package chatbotorioai;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class ExcluirServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Adicione o serialVersionUID também

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String idParam = request.getParameter("id"); // ID que o usuário passou para excluir

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM paciente WHERE id = ?")) {

            stmt.setInt(1, Integer.parseInt(idParam));
            int rowsAffected = stmt.executeUpdate();

            out.println("<html><body>");
            if (rowsAffected > 0) {
                out.println("<h2>Paciente excluído com sucesso!</h2>");
            } else {
                out.println("<h2>Paciente não encontrado.</h2>");
            }
            out.println("<a href='consultas'>Voltar para Lista</a>");
            out.println("</body></html>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h2>Erro ao excluir paciente.</h2>");
            out.println("</body></html>");
        }
    }
}
