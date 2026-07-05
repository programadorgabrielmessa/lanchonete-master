/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.com.dobackaofront.lanchonete;

import br.com.dobackaofront.lanchonete.controller.newpackage.Banco;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class Lanchonete {

    public static void main(String[] args) {
        Banco b = new Banco();
        Connection conexao = b.conectar();
        
        if (conexao != null){
            try {
                conexao.close();
            } catch(SQLException e){
                System.out.println("Erro ao fecha a conexao com o banco de dados! ");
            }
        }
    }
}
