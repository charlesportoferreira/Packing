/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packing;

import algoritmogenetico.AlgoritmoGenetico;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author charleshenriqueportoferreira
 */
public class Packing {

    public static List<Presente> presentes = new ArrayList<>(1000);
    static int maxAltura = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        lerArquivo();
        Treno t = new Treno(1000);
        System.out.println("Lido todos os presentes");

        AlgoritmoGenetico ag = new AlgoritmoGenetico(presentes, 10);
        ag.inicializaCromosssomo(presentes);
        for (int i = 0; i < 10; i++) {
            ag.executaCrossover();
            ag.executaMutacao();
            ag.executaSelecao();
            ag.avaliaSolucao();
        }
        System.exit(0);
        Instant inicio = Instant.now();
        for (int i = 0; i < presentes.size(); i++) {

            t.inserePresente(presentes.get(i));
        }
        Instant fim = Instant.now();
        Duration duracao = Duration.between(inicio, fim);
        long duracaoEmMilissegundos = duracao.toMillis();

        int novaAltura = 0;
        System.out.println(duracaoEmMilissegundos);

        inicio = Instant.now();
        Stack<Integer> pilha = new Stack<>();
        for (Iterator<Integer> it = t.alturas.iterator(); it.hasNext();) {
            novaAltura = it.next();
            //pilha.addElement(novaAltura);
            System.out.print(novaAltura + " ");
            if (novaAltura > maxAltura) {
                maxAltura = novaAltura;
            }
        }

        Set<Integer> todasAlturas = new TreeSet<>();
        for (Presente presente : presentes) {
            todasAlturas.add(presente.getZMax());
        }
        for (Integer integer : todasAlturas) {
            pilha.addElement(integer);
        }

        ArrayList<Integer> todos = new ArrayList<>();
        int qtde = pilha.size();
        for (int i = 0; i < qtde; i++) {
            ArrayList<Integer> altPre = new ArrayList<>();
            int alt = pilha.pop();
            for (Presente presente : presentes) {
                if (presente.getZMax() == alt) {
                    altPre.add(presente.getId());
                }
            }
            Collections.sort(altPre);
            todos.addAll(altPre);
        }
        //Collections.reverse(todos);
        calculaFitness(todos);
        fim = Instant.now();
        duracao = Duration.between(inicio, fim);
        duracaoEmMilissegundos = duracao.toMillis();
        System.out.println(duracaoEmMilissegundos);
        escreverArquivo(t);

        System.exit(0);
//        Presente p1 = new Presente(1, 2, 4, 2);
//        Presente p2 = new Presente(2, 2, 2, 3);
//        Presente p3 = new Presente(3, 4, 4, 4);
//        Presente p4 = new Presente(4, 4, 2, 5);
//        Presente p5 = new Presente(5, 2, 2, 6);
//        Presente p6 = new Presente(6, 2, 2, 7);
//        Presente p7 = new Presente(7, 3, 3, 8);
//
//
//        Treno t2 = new Treno(4);
//        t2.imprimirTabela();
//     
//        t2.inserePresente(p1);
//        System.out.println(p1.getX() + ":" + p1.getY() + ":" + p1.getZ());
//        t2.imprimirTabela();
//        imprimeVertices(p1.getVertices());
//        t2.inserePresente(p2);
//        System.out.println(p2.getX() + ":" + p2.getY() + ":" + p2.getZ());
//        t2.imprimirTabela();
//        imprimeVertices(p2.getVertices());
//        t2.inserePresente(p3);
//        System.out.println(p3.getX() + ":" + p3.getY() + ":" + p3.getZ());
//        t2.imprimirTabela();
//        imprimeVertices(p3.getVertices());
//        t2.inserePresente(p4);
//        System.out.println(p4.getX() + ":" + p4.getY() + ":" + p4.getZ());
//        t2.imprimirTabela();
//        imprimeVertices(p4.getVertices());
//        t2.inserePresente(p5);
//        System.out.println(p5.getX() + ":" + p5.getY() + ":" + p5.getZ());
//        t2.imprimirTabela();
//        imprimeVertices(p5.getVertices());
//        t2.inserePresente(p6);
//        System.out.println(p6.getX() + ":" + p6.getY() + ":" + p6.getZ());
//        t2.imprimirTabela();
//        imprimeVertices(p6.getVertices());
//        t2.inserePresente(p7);
//        System.out.println(p7.getX() + ":" + p7.getY() + ":" + p7.getZ());
//        t2.imprimirTabela();
//        imprimeVertices(p7.getVertices());
    }

    public static void imprimeVertices(List<int[]> vertices) {
        for (int[] vertice : vertices) {
            System.out.println(Arrays.toString(vertice));
        }
    }

    public static void lerArquivo() {
        try {
            try (BufferedReader in = new BufferedReader(new FileReader("presents.csv"))) {
                String str;
                int i = 0;
                int somaAltura = 0;
                while (in.ready()) {
                    if (i > 1000) {
                        break;
                    }
                    str = in.readLine();
                    if (!str.contains("PresentId")) {
                        int id = Integer.parseInt(str.split(",")[0]);
                        int comprimento = Integer.parseInt(str.split(",")[1]);
                        int largura = Integer.parseInt(str.split(",")[2]);
                        int altura = Integer.parseInt(str.split(",")[3]);
                        somaAltura += altura;
                        presentes.add(new Presente(id, comprimento, largura, altura));
                        i++;
                    }

                    //process(str);
                    // System.out.println(str);
                }
                // System.out.println(somaAltura);
                // System.exit(0);
                Collections.reverse(presentes);
            }
        } catch (IOException e) {
        }
    }

    public static void escreverArquivo(Treno t) {

        StringBuilder texto = new StringBuilder();
        texto.append("PresentId,x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4,x5,y5,z5,x6,y6,z6,x7,y7,z7,x8,y8,z8\n");
        for (int i = 0; i < presentes.size(); i++) {
            texto.append(presentes.get(i));
        }
        //texto.replace(texto.length()-2, texto.length()-1, "");
        //System.out.println(texto);
        FileWriter fw = null;
        try {
            fw = new FileWriter("resposta.csv");
            try (BufferedWriter bw = new BufferedWriter(fw)) {

                bw.write(texto.toString());
                //bw.newLine();
            }
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Packing.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Packing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private static void calculaFitness(List<Integer> todos) {
        // Collections.reverse(presentes);
        int somatorio = 0;
        for (int i = 0; i < todos.size(); i++) {
            somatorio += Math.abs((i + 1) - todos.get(i));
        }
        int resultado = (2 * maxAltura) + somatorio;
        System.out.println("\nMinha metrica: " + resultado);

    }

}
