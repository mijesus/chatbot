package chatbotorioai;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DatabaseInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Database.inicializarBanco();
            System.out.println("Banco de dados inicializado na inicialização do contexto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Nada a fazer no shutdown
    }
}
