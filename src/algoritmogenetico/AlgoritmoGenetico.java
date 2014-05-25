package algoritmogenetico;

import java.util.ArrayList;
import java.util.List;
import packing.Presente;

public class AlgoritmoGenetico {

    private final int dimensao;
    //private List<double[]> cromossomos;
    private final List<Cromossomo> cromossomos;

    private final double min;
    private final double max;
    private final int tamanhoPopulacao;
    private double melhorSolucao;
    private Cromossomo cromossomoEscolhido;
    private final int funcaoFitness;
    private int count = 0;

    public AlgoritmoGenetico(int dimensao, double min, double max, int funcaoFitness) {
        this.dimensao = dimensao;
        tamanhoPopulacao = 200;
        cromossomos = new ArrayList<>(tamanhoPopulacao);
        this.min = min;
        this.max = max;
        melhorSolucao = 12345.12345;
        this.funcaoFitness = funcaoFitness;
    }

    public AlgoritmoGenetico(List<Presente> presentes, int dimensao) {
        tamanhoPopulacao = 3;
        this.dimensao = dimensao;
        cromossomos = new ArrayList<>(tamanhoPopulacao);
        this.min = 0;
        this.max = 1000;
        this.funcaoFitness = 0;
    }

    public void inicializaCromossomo() {
        for (int i = 0; i < tamanhoPopulacao; i++) {
            Cromossomo c = new Cromossomo(dimensao, funcaoFitness);
            c.inicializarGenes(min, max);
            cromossomos.add(c);
        }
    }

    public void inicializaCromosssomo(List<Presente> presentes) {
        for (int i = 0; i < tamanhoPopulacao; i++) {
            Cromossomo c = new Cromossomo(presentes);
            c.inicializarGenes(5);
            imprimirPresentes(c);
            List<Presente> p = new ArrayList<>(c.getGenesPresente());
            cromossomos.add(new Cromossomo(p));
        }
    }

    public void executaCrossover() {
        ArrayList<Cromossomo> novaPopulacao = new ArrayList<>(tamanhoPopulacao);
        int probCruzamento = 0 + (int) (Math.random() * (100 - 0));
        for (int i = 0; i < tamanhoPopulacao; i++) {
            int pai1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
            int pai2 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
            List<Presente> filho = new ArrayList<>(tamanhoPopulacao);
            if (probCruzamento > 25) {
                filho = Cruzamento.corte(cromossomos.get(pai1).getGenesPresente(), cromossomos.get(pai2).getGenesPresente(), 3, 6, 10);
            } else {
                filho = new ArrayList<>(cromossomos.get(pai1).getGenesPresente());
            }
            Cromossomo f = new Cromossomo(filho);
            novaPopulacao.add(f);
        }
        for (Cromossomo novoCromossomo : novaPopulacao) {
            cromossomos.add(novoCromossomo);
        }
    }

    public void executaMutacao() {
        for (int i = 0; i < 0.3 * dimensao; i++) {
            int rand1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
            Mutacao.MutacaoPresente(cromossomos.get(rand1).getGenesPresente(), dimensao / 2);
            for (Cromossomo cromossomo : cromossomos) {
                cromossomo.atualizaFitness();
            }
        }
    }

    public void executaSelecao() {
        ArrayList<Cromossomo> popSelecionada = new ArrayList<>();
        popSelecionada.addAll(Selecao.Elitismo(cromossomos, tamanhoPopulacao));
        cromossomos.clear();
        cromossomos.addAll(popSelecionada);
    }

    public double avaliaSolucao() {
        double solucaoAtual;
        double bestFit = -888888;
        cromossomoEscolhido = cromossomos.get(0);
        count++;
        System.out.print(count + " Melhor solucao ate agora: " + cromossomoEscolhido.getFitness() + " ");
        imprimirPresentes(cromossomoEscolhido);
        return cromossomoEscolhido.getFitness();
    }

    public double[] getMelhorCromossomo() {
        return cromossomoEscolhido.getGenes();
    }

    private double getMenorGene() {
        if (cromossomoEscolhido == null) {
            return min;
        }
        double menor = 10000;
        for (double gene : cromossomoEscolhido.getGenes()) {
            if (menor > gene) {
                menor = gene;
            }
        }
        return menor;
    }

    private double getMaxGene() {
        if (cromossomoEscolhido == null) {
            return max;
        }
        double maior = -11111;
        for (double gene : cromossomoEscolhido.getGenes()) {
            if (maior < gene) {
                maior = gene;
            }
        }
        return maior;
    }

    public void imprimirPresentes(Cromossomo c) {

        for (Presente presente : c.getGenesPresente()) {
            System.out.print(presente.getId() + " ");
        }
        System.out.println();
    }
}
