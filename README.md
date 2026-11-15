# Lanchonete Back ao Front

Projeto de estudo de Gabriel Rodrigues de Almeida Messa com Java, Swing, Maven e MySQL. Esta versão integra as telas e o código do professor Olival Paulino, do curso Do Back ao Front. [Projeto original](https://github.com/olivalpaulino/lanchonete/tree/7a824e7c8802ea992a22cacb56706f4064f8bbd7)

[Portfólio e evolução dos projetos](https://github.com/programadorgabrielmessa/Projeto-de-um-sistema-de-cadastro-com-banco-de-dados-mongodb/blob/master/PORTFOLIO.md)

## Fluxo na interface

- **Cadastro → Lanche** grava o nome e o preço no MySQL.
- **Pesquisar → Lanche** busca pelo nome e mostra o ID. Use o ID para editar, excluir ou adicionar o lanche ao carrinho. Para adicionar, informe também a quantidade.
- **Vendas → Carrinho** mostra os itens, as quantidades e o total. O botão **Pagar** fica desabilitado.

## Executar no NetBeans

1. Inicie o MySQL e abra a pasta com o `pom.xml` como projeto Maven no NetBeans. Use o JDK 21 ou posterior.
2. Defina `LANCHONETE_DB_PASSWORD` no ambiente que inicia o NetBeans com a senha do seu MySQL. Se o MySQL estiver sem senha, mantenha essa variável vazia. As demais variáveis são opcionais.

   | Variável | Padrão |
   | --- | --- |
   | `LANCHONETE_DB_USER` | `root` |
   | `LANCHONETE_DB_HOST` | `localhost` |
   | `LANCHONETE_DB_PORT` | `3306` |

3. Pressione **F6** para executar o projeto. A classe principal é `br.com.dobackaofront.lanchonete.Lanchonete`.
4. A inicialização executa o [banco.sql](banco.sql) para criar o banco `lanchonete` e as tabelas `lanche` e `carrinho` quando necessário. Use uma conta MySQL que possa criar esses objetos e inserir, consultar, atualizar e excluir registros. Também é possível abrir e executar o script pelo MySQL Workbench.

Com Maven instalado, a partir da pasta do projeto:

```text
mvn compile org.codehaus.mojo:exec-maven-plugin:3.1.0:java
```

## Histórico do protótipo

- Maio de 2026 — [início do protótipo](https://github.com/programadorgabrielmessa/lanchonete-master/commit/1407c0559b989f5d278af8bc96f1b3b64a48ae6d) e [validação do cadastro](https://github.com/programadorgabrielmessa/lanchonete-master/commit/daac75c6cfe42447b0c04be1ff028e319e8cf82a).
- Julho a setembro de 2026 — estudo da conexão JDBC e da camada de banco. [Commit de setembro](https://github.com/programadorgabrielmessa/lanchonete-master/commit/4dade9844e422430c4e8ad8a3a8d100ae19acf46)
