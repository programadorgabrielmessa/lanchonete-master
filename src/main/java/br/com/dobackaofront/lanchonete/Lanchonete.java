/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license
 */

package br.com.dobackaofront.lanchonete;

import br.com.dobackaofront.lanchonete.controller.Banco;
import br.com.dobackaofront.lanchonete.model.Lanche;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class Lanchonete {

    public static void main(String[] args) {

        // Cria um objeto responsável pelo banco de dados
        Banco banco = new Banco();

        // Conecta ao banco chamado "lanchonete"
        Connection conexao = banco.conectar("lanchonete");

        // Só continua se a conexão tiver funcionado
        if (conexao != null) {

            // Cria um novo objeto Lanche
            Lanche lanche = new Lanche("Café Gelado", 9.90);

            try {

                // Salva o lanche no banco de dados
                banco.salvar(lanche, conexao);

                // Fecha a conexão depois de terminar
                conexao.close();

            } catch (SQLException e) {

                System.out.println(
                        "Erro ao fechar a conexão com o banco de dados!"
                );

                System.out.println(
                        "Erro: " + e.getMessage()
                );
            }
        }
    }
}