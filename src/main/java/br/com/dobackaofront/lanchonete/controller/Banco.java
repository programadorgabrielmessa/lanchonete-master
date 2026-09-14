// Define em qual pacote esta classe está localizada.
package br.com.dobackaofront.lanchonete.controller;

// Importa a classe Lanche que está no pacote model.
// Precisamos dela porque o método salvar() recebe um objeto Lanche.
import br.com.dobackaofront.lanchonete.model.Lanche;

// Permite trabalhar com leitura de arquivos linha por linha.
import java.io.BufferedReader;

// Permite abrir um arquivo existente no computador.
import java.io.FileInputStream;

// Representa uma entrada de dados.
// Neste projeto será usado para representar o arquivo banco.sql.
import java.io.InputStream;

// Converte os bytes do arquivo em caracteres de texto.
import java.io.InputStreamReader;

// Representa uma conexão com o banco de dados.
import java.sql.Connection;

// Classe responsável por abrir conexões JDBC com o MySQL.
import java.sql.DriverManager;

// Usado para executar comandos SQL com parâmetros,
// como INSERT INTO com os símbolos ?.
import java.sql.PreparedStatement;

// Representa erros relacionados ao banco de dados.
import java.sql.SQLException;

// Usado para executar comandos SQL comuns,
// como CREATE DATABASE, USE e CREATE TABLE.
import java.sql.Statement;


/**
 * Classe responsável pela comunicação
 * entre o sistema Java e o banco de dados MySQL.
 */
public class Banco {

    // Guarda o endereço do servidor MySQL.
    // localhost significa que o MySQL está no próprio computador.
    // 3306 é a porta padrão utilizada pelo MySQL.
    private String url;

    // Guarda o nome do usuário utilizado para acessar o MySQL.
    private String usuario;

    // Guarda a senha utilizada para acessar o MySQL.
    private String senha;

    // Variável que pode armazenar uma conexão com o banco.
    private Connection conexao;


    /**
     * Construtor da classe Banco.
     *
     * O construtor é executado automaticamente
     * quando fazemos:
     *
     * Banco banco = new Banco();
     */
    public Banco() {

        // Define o endereço do servidor MySQL.
        // Neste momento ainda não estamos entrando
        // especificamente no banco "lanchonete".
        url = "jdbc:mysql://localhost:3306";

        // Define o usuário do MySQL.
        usuario = "root";

        // Define a senha do MySQL.
        // Se a senha do seu MySQL não for root,
        // você deverá alterar este valor.
        senha = "root";

        // Chama o método responsável por ler o arquivo
        // banco.sql e criar o banco/tabelas.
        inicializarBanco(url, usuario, senha);
    }


    /**
     * Método responsável por conectar
     * a um banco específico.
     *
     * Exemplo:
     *
     * conectar("lanchonete");
     *
     * @param banco nome do banco que queremos acessar.
     * @return uma conexão aberta ou null caso aconteça erro.
     */
    public Connection conectar(String banco) {

        // Inicia um bloco try.
        // O Java tentará executar o código dentro dele.
        try {

            // Acrescenta o nome do banco na URL.
            //
            // Antes:
            // jdbc:mysql://localhost:3306
            //
            // Depois:
            // jdbc:mysql://localhost:3306/lanchonete
            url += "/" + banco;

            // Tenta abrir uma conexão com o MySQL.
            //
            // São enviados:
            // 1 - endereço do banco
            // 2 - usuário
            // 3 - senha
            Connection conexao =
                    DriverManager.getConnection(
                            url,
                            usuario,
                            senha
                    );

            // Esta mensagem aparece se a conexão
            // for realizada corretamente.
            System.out.println(
                    "Conexão com o banco de dados estabelecida com sucesso!"
            );

            // Retorna a conexão criada.
            //
            // Assim outra parte do programa poderá utilizar:
            //
            // Connection conexao = banco.conectar("lanchonete");
            return conexao;

        // Se acontecer algum erro relacionado ao banco,
        // o Java entra neste catch.
        } catch (SQLException e) {

            // Mostra uma mensagem informando
            // que não foi possível conectar.
            System.out.println(
                    "Não foi possível conectar no banco de dados"
            );

            // e.getMessage() mostra a mensagem real
            // que veio do MySQL/JDBC.
            System.out.println(
                    "Erro: " + e.getMessage()
            );

            // Como não foi possível conectar,
            // o método retorna null.
            return null;
        }
    }


    /**
     * Método responsável por salvar
     * um objeto Lanche no banco de dados.
     *
     * @param lanche objeto contendo nome e preço.
     * @param conexao conexão aberta com o banco.
     */
    public void salvar(
            Lanche lanche,
            Connection conexao
    ) {

        // Cria o comando SQL responsável
        // por inserir um registro na tabela lanche.
        //
        // Os ? serão substituídos posteriormente.
        String sql =
                "INSERT INTO lanche (nome, preco) VALUES (?, ?)";

        // Tenta executar o comando SQL.
        try {

            // Cria um PreparedStatement.
            //
            // O PreparedStatement permite colocar
            // valores nos símbolos ? do SQL.
            PreparedStatement stmt =
                    conexao.prepareStatement(sql);

            // Substitui o primeiro ? pelo nome do lanche.
            //
            // Exemplo:
            //
            // Café Expresso
            stmt.setString(
                    1,
                    lanche.getNome()
            );

            // Substitui o segundo ? pelo preço do lanche.
            //
            // Exemplo:
            //
            // 5.90
            stmt.setDouble(
                    2,
                    lanche.getPreco()
            );

            // Executa o INSERT no banco.
            //
            // executeUpdate() retorna a quantidade
            // de linhas afetadas.
            int linhasAfetadas =
                    stmt.executeUpdate();

            // Verifica se pelo menos uma linha
            // foi inserida.
            if (linhasAfetadas > 0) {

                // Se uma linha foi inserida,
                // mostra a mensagem de sucesso.
                System.out.println(
                        "Lanche foi salvo com sucesso!"
                );
            }

            // Fecha o PreparedStatement.
            //
            // Isso libera recursos utilizados
            // durante a execução do SQL.
            stmt.close();

        // Caso aconteça um erro SQL,
        // este bloco será executado.
        } catch (SQLException e) {

            // Informa que ocorreu um erro
            // ao salvar o lanche.
            System.out.println(
                    "Erro ao salvar lanche."
            );

            // Mostra a mensagem original do erro.
            System.out.println(
                    "Erro: " + e.getMessage()
            );
        }
    }


    /**
     * Método responsável por inicializar o banco.
     *
     * Ele:
     *
     * abre uma conexão com o MySQL,
     * abre o arquivo banco.sql,
     * lê o arquivo linha por linha,
     * monta os comandos SQL
     * e executa cada comando.
     */
    public void inicializarBanco(
            String url,
            String usuario,
            String senha
    ) {

        // Tenta conectar ao servidor MySQL.
        try {

            // Abre uma conexão apenas com o servidor.
            //
            // Exemplo:
            // jdbc:mysql://localhost:3306
            //
            // Aqui ainda não precisamos entrar
            // diretamente no banco lanchonete porque
            // o próprio banco.sql irá criá-lo.
            Connection conexao =
                    DriverManager.getConnection(
                            url,
                            usuario,
                            senha
                    );

            // Cria um Statement.
            //
            // Ele será usado para executar
            // os comandos encontrados no banco.sql.
            Statement stmt =
                    conexao.createStatement();

            // Começa outro try.
            //
            // Agora vamos tentar abrir e ler
            // o arquivo banco.sql.
            try {

                // Abre o arquivo banco.sql.
                //
                // O Java procura esse arquivo
                // na pasta principal do projeto.
                InputStream is =
                        new FileInputStream(
                                "banco.sql"
                        );

                // O FileInputStream trabalha com bytes.
                //
                // InputStreamReader transforma
                // esses bytes em caracteres.
                InputStreamReader isr =
                        new InputStreamReader(is);

                // BufferedReader permite ler
                // o arquivo linha por linha.
                BufferedReader br =
                        new BufferedReader(isr);

                // Variável que armazenará
                // cada linha lida do arquivo.
                String linha;

                // StringBuilder será utilizado
                // para juntar várias linhas SQL.
                StringBuilder sql =
                        new StringBuilder();

                // Lê a primeira linha do banco.sql
                // e guarda dentro da variável linha.
                linha = br.readLine();

                // Enquanto ainda existir alguma linha
                // dentro do arquivo...
                while (linha != null) {

                    // Adiciona a linha atual
                    // dentro do StringBuilder.
                    sql.append(linha)

                       // Depois da linha,
                       // adiciona uma quebra de linha.
                       .append("\n");

                    // Verifica se a linha termina com ;
                    //
                    // No SQL o ; representa
                    // o fim de um comando.
                    if (linha.trim().endsWith(";")) {

                        // Executa no MySQL todo o SQL
                        // que foi acumulado no StringBuilder.
                        stmt.execute(
                                sql.toString()
                        );

                        // Limpa o StringBuilder.
                        //
                        // Isso é necessário porque agora
                        // começaremos a montar o próximo
                        // comando SQL.
                        sql.setLength(0);
                    }

                    // Lê a próxima linha do arquivo.
                    //
                    // Sem esta linha o while ficaria
                    // preso para sempre na mesma linha.
                    linha = br.readLine();
                }

                // Fecha o BufferedReader.
                br.close();

                // Fecha o InputStreamReader.
                isr.close();

                // Fecha o arquivo.
                is.close();

                // Fecha o Statement.
                stmt.close();

                // Fecha a conexão utilizada
                // para criar o banco.
                conexao.close();

                // Mostra uma mensagem se todo
                // o processo terminar corretamente.
                System.out.println(
                        "Banco de dados inicializado com sucesso!"
                );

            // Se acontecer algum problema
            // ao abrir ou ler o banco.sql...
            } catch (Exception e) {

                // Mostra mensagem de erro.
                System.out.println(
                        "Não foi possível ler o arquivo banco.sql"
                );

                // Mostra detalhes do erro.
                System.out.println(
                        "Erro: " + e.getMessage()
                );
            }

        // Se não conseguir conectar no MySQL,
        // o programa entra aqui.
        } catch (SQLException e) {

            // Informa o problema.
            System.out.println(
                    "Erro ao conectar ao MySQL."
            );

            // Mostra a mensagem original
            // enviada pelo JDBC/MySQL.
            System.out.println(
                    "Erro: " + e.getMessage()
            );
        }
    }

} // Aqui termina a classe Banco.