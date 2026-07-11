package br.com.dobackaofront.lanchonete.controller.newpackage;

import br.com.dobackaofront.lanchonete.model.Lanche;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Classe responsável pela comunicação com o banco de dados.
 */
public class Banco {

    // Endereço do banco de dados MySQL
    private String url = "jdbc:mysql://localhost:3306/lanchonete";

    // Usuário do banco de dados
    private String usuario = "root";

    // Senha do banco de dados
    private String senha = "root";

    /**
     * Método responsável por abrir a conexão com o banco de dados.
     * @return conexão com o banco ou null caso ocorra erro.
     */
    public Connection conectar() {
        try {
            // Tenta criar uma conexão com o banco usando URL, usuário e senha
            Connection conexao = DriverManager.getConnection(url, usuario, senha);

            // Mensagem exibida se a conexão funcionar
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");

            // Retorna a conexão criada
            return conexao;

        } catch (SQLException e) {
            // Caso ocorra erro, mostra mensagem no console
            System.out.println("Não foi possível conectar no banco de dados");
            System.out.println("Erro: " + e.getMessage());

            // Retorna null para indicar que a conexão falhou
            return null;
        }
    }

    /**
     * Método responsável por salvar um lanche no banco de dados.
     * @param lanche objeto com nome e preço do lanche.
     * @param conexao conexão aberta com o banco de dados.
     */
    public void salvar(Lanche lanche, Connection conexao) {

        // Comando SQL para inserir um novo lanche na tabela
        String sql = "INSERT INTO lanche (nome, preco) VALUES (?, ?)";

        try {
            // Prepara o comando SQL para receber os valores
            PreparedStatement stmt = conexao.prepareStatement(sql);

            // Substitui o primeiro ? pelo nome do lanche
            stmt.setString(1, lanche.getNome());

            // Substitui o segundo ? pelo preço do lanche
            stmt.setDouble(2, lanche.getPreco());

            // Executa o INSERT no banco de dados
            int linhasAfetadas = stmt.executeUpdate();

            // Se inseriu pelo menos uma linha, mostra mensagem de sucesso
            if (linhasAfetadas > 0) {
                System.out.println("Lanche foi salvo com sucesso!");
            }

            // Fecha o PreparedStatement
            stmt.close();

        } catch (SQLException e) {
            // Caso ocorra erro ao salvar, mostra o erro no console
            System.out.println("Erro ao salvar lanche.");
            System.out.println("Erro: " + e.getMessage());
        }
    }
}