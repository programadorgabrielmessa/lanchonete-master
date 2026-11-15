/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dobackaofront.lanchonete.model;

import java.util.ArrayList;

/**
 *
 * @author Família Santos
 */
public class Carrinho {
    private ArrayList<Lanche> lanches;

    public Carrinho() {
        lanches = new ArrayList<>();
    }

    public void adicionarCarrinho(Lanche lanche) {
        lanches.add(lanche);
    }

    public void removerLancheCarrinho(int id) {
        for (int i=0; i<lanches.size(); i++) {
            if (lanches.get(i).getId() == id) {
                lanches.remove(lanches.get(i));
                i=lanches.size();
            }
        }
    }

    public double calcularPreco() {
        double total = 0;
        for (Lanche lanche: lanches) {
            total += lanche.getPreco();
        }
        return total;
    }

    public ArrayList<Lanche> getLanches() {
        return lanches;
    }


}
