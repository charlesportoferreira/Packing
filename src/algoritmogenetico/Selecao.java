package algoritmogenetico;

import fitness.Fitness;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Selecao {

    List<double[]> cromossomos;
    int funcaoFitness;

    public Selecao(List<double[]> cromossomos, int funcaoFitness) {
        this.cromossomos = cromossomos;
        this.funcaoFitness = funcaoFitness;
    }

    public static List<Cromossomo> roleta(List<Cromossomo> cromossomos, int nElementos) {
        int dimensao = cromossomos.size();
        double fitnessTotal = 0;
        double[] fitness = new double[dimensao];

        //pegar o primeiro fitness como menor temporariamente
        double menorFitness = 1 / cromossomos.get(0).getFitness();
        for (int i = 0; i < dimensao; i++) {
            double fitnessAtual = 1 / cromossomos.get(i).getFitness();
            if (fitnessAtual < menorFitness) {
                menorFitness = fitnessAtual;
            }
            fitness[i] = fitnessAtual;
        }

        //faz o escalonamento
        for (int i = 0; i < dimensao; i++) {
            fitness[i] = fitness[i] + menorFitness;
            fitnessTotal += fitness[i];
            fitness[i] = fitnessTotal;
        }
        ArrayList<Cromossomo> cromossomosSelecionados = new ArrayList<>();
        for (int i = 0; i < nElementos; i++) {
            double rand = 0 + (Math.random() * ((fitnessTotal - 0)));
            for (int j = 0; j < dimensao; j++) {
                if (rand <= fitness[j]) {
                    cromossomosSelecionados.add(cromossomos.get(i));
                }
            }
        }
        if (cromossomosSelecionados.size() != nElementos) {
            //throw new RuntimeException("Nao foi achado o fitness na roleta");
        }
        return cromossomosSelecionados;

    }

    public double[] Torneio() {
        int dimensao = cromossomos.size();
        int rand1 = 0 + (int) (Math.random() * (((dimensao - 1) - 0) + 1));
        int rand2 = 0 + (int) (Math.random() * (((dimensao - 1) - 0) + 1));
        double fit1 = 1 / new Fitness(cromossomos.get(rand1), funcaoFitness).getFitness();
        fit1 = Math.round(fit1 * 100.0) / 100.0;
        double fit2 = 1 / new Fitness(cromossomos.get(rand2), funcaoFitness).getFitness();
        fit2 = Math.round(fit2 * 100.0) / 100.0;
        if (fit1 > fit2) {
            // System.out.println(fit1 + " : " + fit2);
            return cromossomos.get(rand1);
        }
        return cromossomos.get(rand2);
    }

    public static List<Cromossomo> Elitismo(List<Cromossomo> cromossomos, int nElementos) {
        Collections.sort(cromossomos, new ComparadorCromossomo());
        List<Cromossomo> copias = new ArrayList<>(cromossomos.subList(0, nElementos));
       // List<Cromossomo> copias = new ArrayList<>(cromossomos.subList(0, cromossomos.size()));
//        cromossomos.clear();
//        double fitnessAtual;
//        double bestFitness;
//        int posicaoMelhorFitness;
//        for (int j = 0; j < nElementos; j++) {
//            //iniciliaza o bestFitness com o primeiro fitness temporariamente
//            bestFitness = 1 / copias.get(0).getFitness();
//            posicaoMelhorFitness = 0;
//            int i = 0;
//            while (i < copias.size()) {
//                fitnessAtual = 1 / copias.get(i).getFitness();
//                if (fitnessAtual > bestFitness) {
//                    bestFitness = fitnessAtual;
//                    posicaoMelhorFitness = i;
//                }
//                i++;
//            }
//            cromossomos.add(copias.get(posicaoMelhorFitness));
//            copias.remove(posicaoMelhorFitness);
//        }
        // return cromossomos;
        return copias;
    }

    private double min(double[] vetor) {
        double min = vetor[0];
        for (double el : vetor) {
            if (el < min) {
                min = el;
            }
        }
        return min;

    }
}
