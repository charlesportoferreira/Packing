/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmogenetico;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import packing.Presente;

public class Cruzamento {

    private final double pai1[];
    private final double pai2[];
    private final double cromossomos[][];

    public Cruzamento(double[] pai1, double[] pai2) {
        cromossomos = new double[2][pai1.length];
        this.pai1 = pai1;
        this.pai2 = pai2;
    }

    public double[][] CX(int posicaoCorte, int tamanho) {
        if (posicaoCorte + tamanho > pai1.length) {
            throw new RuntimeException("limites de corte errados: " + posicaoCorte + ":" + tamanho + " " + pai1.length);
        }
        double filho1[] = new double[pai1.length];
        double filho2[] = new double[pai2.length];
        System.arraycopy(pai1, posicaoCorte, filho1, 0, tamanho);
        System.arraycopy(pai2, 0, filho1, tamanho, posicaoCorte);
        System.arraycopy(pai2, posicaoCorte + tamanho, filho1, posicaoCorte + tamanho, pai2.length - (posicaoCorte + tamanho));

        System.arraycopy(pai2, posicaoCorte, filho2, 0, tamanho);
        System.arraycopy(pai1, 0, filho2, tamanho, posicaoCorte);
        System.arraycopy(pai1, posicaoCorte + tamanho, filho2, posicaoCorte + tamanho, pai1.length - (posicaoCorte + tamanho));
        cromossomos[0] = filho1;
        cromossomos[1] = filho2;
        return cromossomos;
    }

    public double[][] PMX(int posicaoCorte1, int posicaoCorte2) {
        if (posicaoCorte1 > pai1.length || posicaoCorte2 > pai2.length || posicaoCorte1 > posicaoCorte2) {
            throw new RuntimeException("limites de corte errados: " + posicaoCorte1 + ":" + posicaoCorte2);
        }
        double filho1[] = new double[pai1.length];
        double filho2[] = new double[pai2.length];
        System.arraycopy(pai1, posicaoCorte1, filho1, posicaoCorte1, posicaoCorte1 + posicaoCorte2);
        System.arraycopy(pai2, 0, filho1, 0, posicaoCorte1);
        System.arraycopy(pai2, posicaoCorte2, filho1, posicaoCorte2, pai1.length - posicaoCorte2);

        System.arraycopy(pai2, posicaoCorte1, filho2, posicaoCorte1, posicaoCorte1 + posicaoCorte2);
        System.arraycopy(pai1, 0, filho2, 0, posicaoCorte1);
        System.arraycopy(pai1, posicaoCorte2, filho2, posicaoCorte2, pai2.length - posicaoCorte2);
        cromossomos[0] = filho1;
        cromossomos[1] = filho2;
        return cromossomos;
    }

    public static List<Presente> corte(List<Presente> p1, List<Presente> p2, int posCorteInicial, int posCorteFinal, int tamanho) {
        System.out.println("----");
        System.out.println(posCorteInicial + " " + posCorteFinal);
        imprimirPresentes(new Cromossomo(p1));
        imprimirPresentes(new Cromossomo(p2));
        System.out.println("----");
        List<Integer> genesPreDefinidos = new ArrayList<>();

        //int metade = (int) Math.floor(tamanho / 2);
        List<Presente> f1 = new ArrayList<>();
        for (Presente presente : p1) {
            try {
                f1.add(presente.clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Cruzamento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // int k = 0;
        for (int i = posCorteInicial; i < posCorteFinal; i++) {
            genesPreDefinidos.add(p1.get(i).getId());

        }
        int i = posCorteFinal - posCorteInicial;
        int j = 0;
        for (Presente presente : p2) {
            if (genesPreDefinidos.contains(presente.getId())) {
                continue;
            }
            if (j == posCorteInicial) {
                j = posCorteFinal;
            }
            try {
                f1.set(j, presente.clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Cruzamento.class.getName()).log(Level.SEVERE, null, ex);
            }
            j++;
        }
//        while (j < tamanho) {
//            if (!genesPreDefinidos.contains(p2.get(j).getId())) {
//                try {
//                    f1.set(i, p2.get(j).clone());
//                } catch (CloneNotSupportedException ex) {
//                    Logger.getLogger(Cruzamento.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                i++;
//            }
//            j++;
//        }
        imprimirPresentes(new Cromossomo(f1));
        return f1;
    }

    public static void imprimirPresentes(Cromossomo c) {

        for (Presente presente : c.getGenesPresente()) {
            System.out.print(presente.getId() + " ");
        }
        System.out.println();
    }

}
