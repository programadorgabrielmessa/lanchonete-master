# Lanchonete Back ao Front

Protótipo desktop de estudo em Java para praticar orientação a objetos, interface Swing e validação de um formulário de lanches.

[Portfólio e evolução dos projetos](https://github.com/programadorgabrielmessa/Projeto-de-um-sistema-de-cadastro-com-banco-de-dados-mongodb/blob/master/PORTFOLIO.md)

## Fluxo implementado na interface

1. O menu “Cadastro > Lanche” abre o formulário.
2. O botão “Salvar” verifica nome e preço vazios.
3. O preço é convertido para número com ponto decimal.
4. Um objeto `Lanche` é criado e apresentado no console.
5. O botão “Cancelar” limpa os campos e fecha o formulário interno.

## Tecnologias e organização

Java · Swing · Maven · Apache NetBeans · JDBC

| Componente | Papel atual |
| --- | --- |
| `model/Lanche.java` | Nome, preço, construtor, getters, setters e apresentação do objeto |
| `view/GUIMenu.java` | Menus, formulário, eventos e validação |
| `controller/Banco.java` | Estudo de conexão JDBC, leitura de script e inserção parametrizada |
| `pom.xml` | Dependências e configuração Maven |

Os arquivos Java ficam em `src/main/java/br/com/dobackaofront/lanchonete/`.

## Situação da integração com banco

A classe `Banco` contém código de conexão e `INSERT` com `PreparedStatement`. O botão “Salvar” da interface ainda não chama essa classe, e o arquivo `banco.sql` ainda não define um esquema. Assim, o fluxo da interface atual não persiste o lanche no MySQL e ainda não representa um CRUD completo.

Os menus de edição, pesquisa e vendas fazem parte da interface em construção.

## Abrir e demonstrar

Abra o projeto Maven no NetBeans e execute a classe:

```text
br.com.dobackaofront.lanchonete.view.GUIMenu
```

O `pom.xml` atual configura JDK 25 e uma dependência local `AbsoluteLayout` criada pelo NetBeans. Esses requisitos devem ser resolvidos no ambiente antes da compilação. A configuração e a execução Java não foram validadas nesta revisão.

Para demonstrar a lógica implementada, teste os campos vazios, um preço não numérico e um preço como `12.50`; observe a criação do objeto no console.

## Evolução verificável

- Maio de 2026: [início do protótipo](https://github.com/programadorgabrielmessa/lanchonete-master/commit/1407c0559b989f5d278af8bc96f1b3b64a48ae6d).
- Maio de 2026: [cadastro e validação](https://github.com/programadorgabrielmessa/lanchonete-master/commit/daac75c6cfe42447b0c04be1ff028e319e8cf82a).
- Julho a setembro de 2026: estudo e versionamento da camada de banco. [Commit de setembro](https://github.com/programadorgabrielmessa/lanchonete-master/commit/4dade9844e422430c4e8ad8a3a8d100ae19acf46).

Próximas etapas do projeto: integrar persistência ao formulário, criar o esquema, acrescentar consulta/edição/exclusão e testes de integração.

**Autor:** Gabriel Rodrigues de Almeida Messa. Projeto pessoal de aprendizado.
