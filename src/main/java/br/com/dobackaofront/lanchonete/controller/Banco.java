package br.com.dobackaofront.lanchonete.controller;

import br.com.dobackaofront.lanchonete.model.Lanche;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Banco {

    private final String url = "jdbc:mysql://"
            + System.getenv().getOrDefault("LANCHONETE_DB_HOST", "localhost")
            + ":" + System.getenv().getOrDefault("LANCHONETE_DB_PORT", "3306") + "/";
    private final String usuario = System.getenv().getOrDefault("LANCHONETE_DB_USER", "root");
    private final String senha = System.getenv().getOrDefault("LANCHONETE_DB_PASSWORD", "");

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(url + "lanchonete", usuario, senha);
    }

    public void inicializarBanco() throws IOException, SQLException {
        try (InputStream script = Banco.class.getResourceAsStream("/banco.sql")) {
            if (script == null) {
                throw new IOException("banco.sql não foi encontrado no classpath");
            }

            String sql = new String(script.readAllBytes(), StandardCharsets.UTF_8);
            try (Connection conexao = DriverManager.getConnection(url, usuario, senha);
                    Statement stmt = conexao.createStatement()) {
                for (String comando : sql.split(";")) {
                    if (!comando.isBlank()) {
                        stmt.execute(comando);
                    }
                }
            }
        }
    }

    public void salvar(String nome, double preco) throws SQLException {
        String sql = "INSERT INTO lanche (nome, preco) VALUES (?, ?)";
        try (Connection conexao = conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setDouble(2, preco);
            stmt.executeUpdate();
        }
    }

    public ArrayList<Lanche> buscarPorTrechoNome(String trechoNome) throws SQLException {
        String sql = "SELECT id, nome, preco FROM lanche WHERE nome LIKE ? ORDER BY id";
        ArrayList<Lanche> lanches = new ArrayList<>();
        try (Connection conexao = conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + trechoNome + "%");
            try (ResultSet resultado = stmt.executeQuery()) {
                while (resultado.next()) {
                    lanches.add(lerLanche(resultado));
                }
            }
        }
        return lanches;
    }

    public boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM lanche WHERE id = ?";
        try (Connection conexao = conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean editar(String nome, double preco, int id) throws SQLException {
        String sql = "UPDATE lanche SET nome = ?, preco = ? WHERE id = ?";
        try (Connection conexao = conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setDouble(2, preco);
            stmt.setInt(3, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public Lanche buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nome, preco FROM lanche WHERE id = ?";
        try (Connection conexao = conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet resultado = stmt.executeQuery()) {
                return resultado.next() ? lerLanche(resultado) : null;
            }
        }
    }

    public boolean adicionarCarrinho(int id, int quantidade) throws SQLException {
        String sql = """
                INSERT INTO carrinho (nome, preco, quantidade)
                SELECT nome, preco, ? FROM lanche WHERE id = ?
                """;
        try (Connection conexao = conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, quantidade);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public Map<Lanche, Integer> buscarCarrinho() throws SQLException {
        String sql = "SELECT id, nome, preco, quantidade FROM carrinho ORDER BY id";
        Map<Lanche, Integer> carrinho = new LinkedHashMap<>();
        try (Connection conexao = conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet resultado = stmt.executeQuery()) {
            while (resultado.next()) {
                carrinho.put(lerLanche(resultado), resultado.getInt("quantidade"));
            }
        }
        return carrinho;
    }

    private Lanche lerLanche(ResultSet resultado) throws SQLException {
        Lanche lanche = new Lanche(resultado.getString("nome"), resultado.getDouble("preco"));
        lanche.setId(resultado.getInt("id"));
        return lanche;
    }
}
