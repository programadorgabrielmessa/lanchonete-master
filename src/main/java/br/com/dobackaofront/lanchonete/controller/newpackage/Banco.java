/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dobackaofront.lanchonete.controller.newpackage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
/**
 *
 * @author User
 */
public class Banco {
    private String url = "jdbc:mysql://localhost:3306/lanchonete";
    private String usuario = "root";
    private String senha = "root";
    
    public Banco(){
        url = "jdbc:mysql://localhost::3306/lanchonete";
        usuario = "root";
        senha = "root";
    }
    
    public Connection conectar(){
        try {
            Connection conexao = DriverManager.getConnection(url,usuario,senha);
            System.out.println("Conexao com o banco de dados estabelecida com sucesso!");
            return conexao;
        } catch(SQLException e){
            System.out.println("Não foi possivel conectar no banco de dados");
            return null;
        }
        public void salvar (Lance lanche);
    }
    
}
