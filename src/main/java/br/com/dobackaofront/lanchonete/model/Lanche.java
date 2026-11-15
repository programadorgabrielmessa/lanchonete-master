/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dobackaofront.lanchonete.model;

import br.com.dobackaofront.lanchonete.controller.Banco;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Família Santos
 */
public class Lanche {
    private int id;
    private String nome;
    private double preco;

    public Lanche() {
        this.id = 0;
        this.nome = "";
        this.preco = 0;
    }

    public Lanche(String nome, double preco) {
        this.id = 0;
        this.nome = nome;
        this.preco = preco;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the preco
     */
    public double getPreco() {
        return preco;
    }

    /**
     * @param preco the preco to set
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void apresentarLanche() {
        System.out.println("Nome: "+nome+", R$ "+preco+", ID: "+id);
    }



    public void salvar(String nome, double preco) throws SQLException {
        new Banco().salvar(nome, preco);
    }

    public ArrayList<Lanche> pesquisar(String nome) throws SQLException {
        return new Banco().buscarPorTrechoNome(nome);
    }

    public boolean deletar(int id) throws SQLException {
        return new Banco().deletar(id);
    }

    public boolean editar(String nome, double preco, int id) throws SQLException {
        return new Banco().editar(nome, preco, id);
    }

    public Lanche buscarPorId(int id) throws SQLException {
        return new Banco().buscarPorId(id);
    }

    public boolean adicionarCarrinho(int id, int quantidade) throws SQLException {
        return new Banco().adicionarCarrinho(id, quantidade);
    }

    public Map<Lanche, Integer> recuperarCarrinho() throws SQLException {
        return new Banco().buscarCarrinho();
    }



    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
