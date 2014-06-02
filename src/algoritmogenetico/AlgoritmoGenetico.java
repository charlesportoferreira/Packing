package algoritmogenetico;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
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
    private final List<Cromossomo> cromossomos;

    private final int tamanhoPopulacao;
    private Cromossomo cromossomoEscolhido;
    private int count = 0;

    public AlgoritmoGenetico(List<Presente> presentes, int dimensao) {
        tamanhoPopulacao = 5;
        this.dimensao = dimensao;
        cromossomos = new ArrayList<>(tamanhoPopulacao);
    }

    public void inicializaCromosssomo(List<Presente> presentes) {
        int deslocamento = 10;
        for (int i = 0; i < tamanhoPopulacao; i++) {
            Cromossomo c = new Cromossomo(presentes);

            if (i != 0) {
                int ponto = 0 + (int) (Math.random() * (1000 - 0));
                c.inicializarGenes(ponto, deslocamento);

            } else {
                c.best = true;
            }

            //imprimirPresentes(c);
            //List<Presente> p = new ArrayList<>(c.getGenesPresente());
            cromossomos.add(c);
        }
        cromossomoEscolhido = cromossomos.get(0);
    }

    public void executaCrossover() {
        //ArrayList<Cromossomo> novaPopulacao = new ArrayList<>(tamanhoPopulacao);
        if (count > 0) {
            Iterator<Cromossomo> it = cromossomos.iterator();
            while (it.hasNext()) {
                if (!it.next().best) {
                    it.remove();
                }
            }
            int a = 0;
        }
        int probCruzamento = 0 + (int) (Math.random() * (100 - 0));
        for (int i = 0; i < 2*tamanhoPopulacao; i++) {
            int pai1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
            int pai2 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
            // System.out.println(pai1 + " " + pai2);
            List<Presente> filho = new ArrayList<>(tamanhoPopulacao);
            if (probCruzamento > 99) {
                filho = Cruzamento.corte(cromossomos.get(pai1).getGenesPresente(), cromossomos.get(pai2).getGenesPresente(), 30, 60, 10);
            } else {
                for (Presente presente : cromossomos.get(pai1).getGenesPresente()) {
                    try {
                        filho.add(presente.clone());
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(AlgoritmoGenetico.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            Cromossomo f = new Cromossomo(filho);
             cromossomos.add(f);
        }
//        for (Cromossomo novoCromossomo : novaPopulacao) {
//            cromossomos.add(novoCromossomo);
//        }
        int a = 0;
    }

    public void executaMutacao() {
        for (int i = 1; i < cromossomos.size(); i++) {
            int rand1 = 0 + (int) (Math.random() * (cromossomos.size() - 0));
            if (cromossomos.get(rand1).best) {
                continue;
            }
            int pontoMutacao = 0 + (int) (Math.random() * (1000 - 0));
             Mutacao.MutacaoPresente(cromossomos.get(i).getGenesPresente(), pontoMutacao, 10);

        }
        List<Future> futures = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(cromossomos.size());
        //int pronto = 0;
        for (Cromossomo cromossomo : cromossomos) {
            //cromossomo.atualizaFitness();
            if (cromossomo.best && count > 0) {
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
        } else {
            cromossomoEscolhido.best = true;
        }
        count++;
        System.out.print(count + " Melhor solucao ate agora: " + cromossomoEscolhido.getFitness() + " ");
        imprimirPresentes(cromossomoEscolhido);
        return cromossomoEscolhido.getFitness();
    }

    public List<Presente> getMelhorCromossomo() {
        return cromossomoEscolhido.getGenesPresente();
    }

    public void imprimirPresentes(Cromossomo c) {

        for (Presente presente : c.getGenesPresente()) {
            System.out.print(presente.getId() + " ");
        }
        System.out.println();
    }
}
