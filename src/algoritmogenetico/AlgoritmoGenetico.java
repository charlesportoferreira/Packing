package algoritmogenetico;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
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

  

    public AlgoritmoGenetico(List<Presente> presentes, int dimensao) {
        tamanhoPopulacao = 2;
        this.dimensao = dimensao;
        cromossomos = new ArrayList<>(tamanhoPopulacao);
        this.min = 0;
        this.max = 1000;
        this.funcaoFitness = 0;
    }

   

    public void inicializaCromosssomo(List<Presente> presentes) {
        for (int i = 0; i < tamanhoPopulacao; i++) {
            Cromossomo c = new Cromossomo(presentes);
            c.inicializarGenes(70);
            //imprimirPresentes(c);
            List<Presente> p = new ArrayList<>(c.getGenesPresente());
            cromossomos.add(new Cromossomo(p));
        }
        cromossomoEscolhido = cromossomos.get(0);
    }

    public void executaCrossover() {
        ArrayList<Cromossomo> novaPopulacao = new ArrayList<>(tamanhoPopulacao);
        int probCruzamento = 0 + (int) (Math.random() * (100 - 0));
        for (int i = 0; i < tamanhoPopulacao; i++) {
            int pai1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
            int pai2 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
            List<Presente> filho = new ArrayList<>(tamanhoPopulacao);
            if (probCruzamento > 25) {
                filho = Cruzamento.corte(cromossomos.get(pai1).getGenesPresente(), cromossomos.get(pai2).getGenesPresente(), 30, 60, 10);
            } else {
                filho = new ArrayList<>(cromossomos.get(pai1).getGenesPresente());
            }
            Cromossomo f = new Cromossomo(filho);
            novaPopulacao.add(f);
        }
        for (Cromossomo novoCromossomo : novaPopulacao) {
            cromossomos.add(novoCromossomo);
        }
        int a = 0;
    }

    public void executaMutacao() {
        for (int i = 0; i < 0.3 * dimensao; i++) {
            int rand1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
            // Mutacao.MutacaoPresente(cromossomos.get(rand1).getGenesPresente(), dimensao / 2);

        }
        List<Future> futures = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(cromossomos.size());
        //int pronto = 0;
        for (Cromossomo cromossomo : cromossomos) {
            //cromossomo.atualizaFitness();
            if(cromossomo.best){
                continue;
            }
            Future f = pool.submit(cromossomo);
            futures.add(f);
        }
        Instant inicio = Instant.now();
        for (Future future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(AlgoritmoGenetico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Instant fim = Instant.now();
        Duration duracao = Duration.between(inicio, fim);
        long duracaoEmMilissegundos = duracao.toMillis();
        System.out.println(duracaoEmMilissegundos);
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
        for (Cromossomo cromossomo : cromossomos) {
            cromossomo.best = false;
        }

        if (cromossomos.get(0).getFitness() < cromossomoEscolhido.getFitness()) {
            cromossomoEscolhido = cromossomos.get(0);
            cromossomos.get(0).best = true;
        }else{
            cromossomoEscolhido.best = true;
        }
        count++;
        System.out.print(count + " Melhor solucao ate agora: " + cromossomoEscolhido.getFitness() + " ");
        imprimirPresentes(cromossomoEscolhido);
        return cromossomoEscolhido.getFitness();
    }

   
   

    

    public void imprimirPresentes(Cromossomo c) {

        for (Presente presente : c.getGenesPresente()) {
            System.out.print(presente.getId() + " ");
        }
        System.out.println();
    }
}
