package br.com.dobackaofront.lanchonete;

import br.com.dobackaofront.lanchonete.controller.Banco;
import br.com.dobackaofront.lanchonete.view.GUIMenu;
import java.awt.EventQueue;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * @author Olival Paulino
 */
public class Lanchonete {

    public static void main(String[] args) {
        try {
            new Banco().inicializarBanco();
        } catch (IOException | SQLException e) {
            System.getLogger(Lanchonete.class.getName())
                    .log(System.Logger.Level.ERROR, "Erro ao inicializar o banco da lanchonete.", e);
            EventQueue.invokeLater(() -> JOptionPane.showMessageDialog(null,
                    "Não foi possível preparar o banco. Verifique se o MySQL está em execução "
                            + "e confira as configurações de conexão no README.",
                    "Erro ao abrir a lanchonete", JOptionPane.ERROR_MESSAGE));
            return;
        }

        EventQueue.invokeLater(() -> new GUIMenu().setVisible(true));
    }
}
