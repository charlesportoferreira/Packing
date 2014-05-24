/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author charleshenriqueportoferreira
 */
public class Treno {

    int[][] tabela;

    int fitness;
    HashMap<Integer, String> areas;
    Set<Integer> alturas;

    ArrayList<Integer> c;

    public Treno(int tamanho) {
        this.tabela = new int[tamanho + 1][tamanho + 1];
        areas = new HashMap<>();
        alturas = new HashSet<>();

        c = new ArrayList<>();
        alturas = new TreeSet<>();
        alturas.add(1);
        inicializaTreino();
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public void inserePresente(Presente p) {

        String pos = getMenorAreaLivre(p.getX(), p.getY(),p);
        int lar = Integer.parseInt(pos.split(",")[0]);
        int comp = Integer.parseInt(pos.split(",")[1]);
        int alt = Integer.parseInt(pos.split(",")[2]);
        p.setVerticeInicial(comp, lar, alt);
        if (p.getId() == 2) {
            // p.setVerticeInicial(4, 10, 0);
        }
        int limX = comp + p.getX() - 1;
        int limY = lar + p.getY() - 1;
        for (int i = lar; i <= limY; i++) {
            for (int j = comp; j <= limX; j++) {
                try {
                    tabela[i][j] = p.getZMax();
                } catch (Exception e) {
                    System.out.println(i + " : " + j);
                    System.out.println(p.getY() + " " + p.getX() + " " + p.getZ());
                    System.out.println(limY + " " + limX);
                    //System.out.println(e.);
                    System.exit(0);
                }

            }
        }
        atualizaListaAlturas();
    }

    public String getMenorAreaLivre(int x, int y, Presente p) {
        //areas.clear();
        
        int altura;
        int menorAltura = 0;
        for (int alt : alturas) {
            altura = alt;
            for (int linha = 1; linha < tabela.length; linha++) {
                for (int coluna = 1; coluna < tabela.length; coluna++) {
                    //altura = tabela[coluna][linha];
                    if (possuiEspacoLivre(linha, coluna,  y,  x, altura,p)) {
                        return linha + "," + coluna + "," + altura;
//                        if (!areas.containsKey(altura)) {
//                            areas.put(altura, linha + "," + coluna);
//                            menorAltura = altura;
//                        }
                    }
                }
            }
        }
        throw new RuntimeException("Nao acho lugar");
        // inicializa a menor alturas com a ultima alturas salva
//        for (Entry<Integer, String> entry : areas.entrySet()) {
//            if (entry.getKey() < menorAltura) {
//                menorAltura = entry.getKey();
//            }
//        }
//        String posicaoMenorArea = areas.get(menorAltura);
//        // if (areas.containsKey(200)) {
//        // System.out.println(posicaoMenorArea);
//        // }
//
//        return posicaoMenorArea + "," + menorAltura;
    }

    private boolean possuiEspacoLivre(int linhaI, int colunaI, int linhaF, int colunaF, int altura, Presente p) {
        if (linhaI + linhaF - 1 < tabela.length && colunaI + colunaF - 1 < tabela.length) {
            boolean possui1;
            boolean possui2;
            boolean possui3;
            for (int i = linhaI; i < linhaI + linhaF - 1; i++) {
                for (int j = colunaI; j < colunaI + colunaF - 1; j++) {
                    if(j == 376 && i == 732 && p.getId() == 214 && altura == 186){
                        int a = 0;
                    }
                    if (tabela[i][j] > altura) {
                        return false;
                    }
                }
            }
            possui1 = tabela[linhaI][colunaI + colunaF - 1] <= altura;
            possui2 = tabela[linhaI + linhaF - 1][colunaI + colunaF - 1] <= altura;
            possui3 = tabela[linhaI + linhaF - 1][colunaI] <= altura;

            return possui1 && possui2 && possui3;
        }
        return false;
    }

    public void imprimirTabela() {
        for (int i = 1; i < tabela.length; i++) {
            for (int j = 1; j < tabela.length; j++) {
                System.out.print(tabela[i][j] + "\t");
            }
            System.out.println("");
        }
    }

    public void atualizaListaAlturas() {
        alturas.clear();
        //c.clear();
        for (int i = 1; i < tabela.length; i++) {
            for (int j = 1; j < tabela.length; j++) {

                alturas.add(tabela[i][j]);

            }
        }
        //Collections.sort(c);
        //alturas.addAll(c);
    }

    private void inicializaTreino() {
        for (int i = 0; i < tabela.length; i++) {
            for (int j = 0; j < tabela.length; j++) {
                tabela[i][j] = 1;
            }
        }
    }
}
