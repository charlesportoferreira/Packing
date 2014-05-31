/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmogenetico;

import fitness.Fitness;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import packing.Presente;
import packing.Treno;

/**
 *
 * @author charleshenriqueportoferreira
 */
public class Cromossomo implements Runnable {

    private List<Presente> presentes;
    private double fitness;
    int id;
    public Treno t;
    boolean best;

    //public int getId() {
    //  return id;
    //}
    public void setId(int id) {
        this.id = id;
    }

    int funcaoFitness;

    public Cromossomo(List<Presente> presentes) {
        this.presentes = new ArrayList<>();
        for (Presente presente : presentes) {
            try {
                this.presentes.add(presente.clone());
            } catch (CloneNotSupportedException ex) {
                System.out.println("!!!!!!!!!!!!!!!!!!");
                Logger.getLogger(Cromossomo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        t = new Treno(1000);
        best = false;
    }

    public void atualizaFitness() {
        this.fitness = getFitnessPresente();
    }

    public double getFitnessPresente() {
        atualizaTreno();

        Stack<Integer> pilha = new Stack<>();
        Set<Integer> todasAlturas = new TreeSet<>();
        for (Presente presente : presentes) {
            todasAlturas.add(presente.getZMax());
        }
        for (Integer integer : todasAlturas) {
            pilha.addElement(integer);
        }
        int alturaMaxima = pilha.lastElement();

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
        this.fitness = calculaFitnessPresente(todos, alturaMaxima);
        return fitness;
    }

    public void inicializarGenes(int ponto) {
        for (int i = 0; i < presentes.size(); i++) {
            int p1 = 0 + (int) (Math.random() * (ponto - 0));
            int p2 = 0 + (int) (Math.random() * (ponto - 0));
            Presente aux1 = presentes.get(p1);
            presentes.set(p1, presentes.get(p2));
            presentes.set(p2, aux1);
        }

    }

    public double getFitness() {
        //if (fitness == 12345.12345) {
        // calculaFitness(funcaoFitness);
        // }
        return fitness;
    }

    public List<Presente> getGenesPresente() {
        return this.presentes;
    }

    private int calculaFitnessPresente(ArrayList<Integer> todos, int alturaMaxima) {
        int somatorio = 0;
        for (int i = 0; i < todos.size(); i++) {
            somatorio += Math.abs((i + 1) - todos.get(i));
        }
        int resultado = (2 * alturaMaxima) + somatorio;
        System.out.println("\nMinha metrica: " + resultado + " somatorio: " + somatorio + " 2*altura: " + 2 * alturaMaxima);
        return resultado;
    }

    private void atualizaTreno() {
        for (int i = 0; i < presentes.size(); i++) {

            t.inserePresente(presentes.get(i));
        }
        //t.imprimirTabela();
    }

    @Override
    public void run() {
        this.fitness = this.getFitnessPresente();
    }

}
