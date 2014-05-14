/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author charleshenriqueportoferreira
 */
public class Treno {

    int[][] tabela;

    int fitness;
    HashMap<Integer, String> areas;
    Set<Integer> alturas;

    public Treno(int tamanho) {
        this.tabela = new int[tamanho + 1][tamanho + 1];
        areas = new HashMap<>();
        alturas = new HashSet<>();
        alturas.add(0);
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public void inserePresente(Presente p) {
        String pos = getMenorAreaLivre(p.getX(), p.getY());
        int x = Integer.parseInt(pos.split(",")[0]);
        int y = Integer.parseInt(pos.split(",")[1]);
        int z = Integer.parseInt(pos.split(",")[2]);
        p.setVerticeInicial(x, y, z);
        

        int limX = x + p.getX() - 1;
        int limY = y + p.getY() - 1;
        for (int i = y; i <= limY; i++) {
            for (int j = x; j <= limX; j++) {
                tabela[i][j] = p.getZ();
            }
        }
        atualizaListaAlturas();
    }

    public String getMenorAreaLivre(int x, int y) {
        areas.clear();
        int altura;
        int menorAltura = 0;
        for (int alt : alturas) {
            altura = alt;
            for (int linha = 1; linha < tabela.length; linha++) {
                for (int coluna = 1; coluna < tabela.length; coluna++) {
                    //altura = tabela[coluna][linha];
                    if (possuiEspacoLivre(coluna, linha, y, x, altura)) {
                        if (!areas.containsKey(altura)) {
                            areas.put(altura, linha + "," + coluna);
                            menorAltura = altura;
                        }
                    }
                }
            }
        }

        // inicializa a menor alturas com a ultima alturas salva
        for (Entry<Integer, String> entry : areas.entrySet()) {
            if (entry.getKey() < menorAltura) {
                menorAltura = entry.getKey();
            }
        }
        String posicaoMenorArea = areas.get(menorAltura);
        // if (areas.containsKey(200)) {
       // System.out.println(posicaoMenorArea);
        // }

        return posicaoMenorArea + "," + menorAltura;
    }

    private boolean possuiEspacoLivre(int linhaI, int colunaI, int linhaF, int colunaF, int altura) {
        if (linhaI + linhaF - 1 < tabela.length && colunaI + colunaF - 1 < tabela.length) {
            return tabela[linhaI + linhaF - 1][colunaI] <= altura
                    && tabela[linhaI + linhaF - 1][colunaI + colunaF - 1] <= altura
                    && tabela[linhaI][colunaI + colunaF - 1] <= altura;
        }
        return false;
    }

    public void imprimirTabela() {
        for (int i = 1; i < tabela.length; i++) {
            for (int j = 1; j < tabela.length; j++) {
                System.out.print(tabela[i][j]);
            }
            System.out.println("");
        }
    }

    public void atualizaListaAlturas() {
        alturas.clear();
        for (int i = 0; i < tabela.length; i++) {
            for (int j = 0; j < tabela.length; j++) {
                alturas.add(tabela[i][j]);
            }
        }
    }
}
