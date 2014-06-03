/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packing;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author charleshenriqueportoferreira
 */
public class Treno {

    int[][] tabela;

    int fitness;
    //HashMap<Integer,> areas;
    public Set<Integer> alturas;
    // public Set<Integer> todasAlturas;
    // int[] lAlturas;
    // ArrayList<Integer> c;

    public Treno(int tamanho) {

        this.tabela = new int[tamanho + 1][tamanho + 1];
       // areas = new HashMap<>();
        //  todasAlturas = new HashSet<>();

        // c = new ArrayList<>(200);
        alturas = new TreeSet<>();
//        alturas = new HashSet<>();
        alturas.add(1);
        //c.add(1);
        inicializaTreno();
        //      lAlturas = new int[61168];
//        lAlturas[0] = 1;

    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public void inserePresente(Presente p) {

        String pos = getMenorAreaLivre(p.getX(), p.getY(), p);
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
        boolean possuiEspaco;

        int altura;
        int ultimaAltura = 0;
        for (int alt : alturas) {
//        for (int alt : c) {
            // if (alt == 0) {
            //  continue;
            //}
            ultimaAltura = alt;
            altura = alt;
            for (int linha = 1; linha < tabela.length; linha = linha + 3 * linha) {
                for (int coluna = 1; coluna < tabela.length; coluna = coluna +  2*coluna) {
                    int proximoEstado = possuiEspacoLivre(linha, coluna, y, x, altura, p);
                    possuiEspaco = (proximoEstado == 0);
                    if (possuiEspaco) {
                        return linha + "," + coluna + "," + altura;
                    } else {
                        if (proximoEstado > 0) {
                            // coluna = proximoEstado - 1;
                        } else {
                            //System.out.println("Opa");
                            coluna = 1000;
                            linha = 1000;
                        }
                    }
                }
            }
        }
        throw new RuntimeException("Nao acho lugar");
    }

    private int possuiEspacoLivre(int linhaI, int colunaI, int linhaF, int colunaF, int altura, Presente p) {
        if (linhaI + linhaF - 1 < tabela.length && colunaI + colunaF - 1 < tabela.length) {
            boolean possui1;
            boolean possui2;
            boolean possui3;
            for (int i = linhaI; i <= linhaI + linhaF - 1; i++) {
                for (int j = colunaI; j <= colunaI + colunaF - 1; j++) {
                    // if (i == 9 && j == 929 && altura == 1 && p.getId() == 30) {
                    // int a = tabela[i][j];
                    // int b = 0;
                    // }
                    if (tabela[i][j] > altura) {
//                        return procuraProximo(i, j, altura);
                        return 1;
                    }
                }
            }
            possui1 = tabela[linhaI][colunaI + colunaF - 1] <= altura;
            possui2 = tabela[linhaI + linhaF - 1][colunaI + colunaF - 1] <= altura;
            possui3 = tabela[linhaI + linhaF - 1][colunaI] <= altura;

            return 0;
        }
        //Math.negateExact(altura);
        int ca = (colunaI + colunaF - 1) >= tabela.length ? colunaI + colunaF - 1 : Math.negateExact(linhaI + linhaF - 1);
//        return colunaI + colunaF - 1;
        return ca;
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
//        c.clear();
        // lAlturas = new int[61168];
//        Arrays.fill(lAlturas, 1);
        for (int i = 1; i < tabela.length; i++) {
            for (int j = 1; j < tabela.length; j++) {

                alturas.add(tabela[i][j]);
                // todasAlturas.add(tabela[i][j]);
                //c.add(tabela[i][j]);
                // lAlturas[tabela[i][j]] = tabela[i][j];

            }
        }

        //        c.add(1);
        //        for (int is : lAlturas) {
        //            if (is != 1) {
        //                c.add(is);
        //            }
        //        }
        //        for (int is : alturas) {
        //            if (is != 1) {
        //                System.out.print(is + " ");
        //            }
        //           
        //        }
        //         System.out.println();
        //Collections.sort(c);
        //alturas.addAll(c);
    }

    public void put(int i, int j, int e) {
//        lAlturas[(i) * tabela.length + j] = e;
    }

    private void inicializaTreno() {
        for (int i = 0; i < tabela.length; i++) {
            for (int j = 0; j < tabela.length; j++) {
                tabela[i][j] = 1;
            }
        }
    }

    private int procuraProximo(int i, int j, int altura) {
        for (int a = i; a < i + 1; a++) {
            for (int b = j; b < 1000; b++) {
                if (tabela[a][b] <= altura) {
                    return b;
                }
            }

        }
        return 1000;
    }

}
