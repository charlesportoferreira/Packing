/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packing;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author charleshenriqueportoferreira
 */
public class Packing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Presente p1 = new Presente(1, 2, 4, 2);
        Presente p2 = new Presente(2, 2, 2, 3);
        Presente p3 = new Presente(3, 4, 4, 4);
        Presente p4 = new Presente(4, 4, 2, 5);
        Presente p5 = new Presente(5, 2, 2, 6);
        Presente p6 = new Presente(6, 2, 2, 7);
        Presente p7 = new Presente(7, 3, 3, 8);

        //p1.setVerticeInicial(1, 1, 1);
//       
//        List<int[]> vertices = p1.getVertices();
//        System.out.println("Vertices");
//        vertices.stream().forEach((v) -> {
//            System.out.println(Arrays.toString(v));
//        });
        Treno t = new Treno(4);
        t.imprimirTabela();
        //t.getMenorAreaLivre(2,2);
        t.inserePresente(p1);
        System.out.println(p1.getX() + ":" + p1.getY() + ":" + p1.getZ());
        t.imprimirTabela();
        imprimeVertices(p1.getVertices());
        t.inserePresente(p2);
        System.out.println(p2.getX() + ":" + p2.getY() + ":" + p2.getZ());
        t.imprimirTabela();
        imprimeVertices(p2.getVertices());
        t.inserePresente(p3);
        System.out.println(p3.getX() + ":" + p3.getY() + ":" + p3.getZ());
        t.imprimirTabela();
        imprimeVertices(p3.getVertices());
        t.inserePresente(p4);
        System.out.println(p4.getX() + ":" + p4.getY() + ":" + p4.getZ());
        t.imprimirTabela();
        imprimeVertices(p4.getVertices());
        t.inserePresente(p5);
        System.out.println(p5.getX() + ":" + p5.getY() + ":" + p5.getZ());
        t.imprimirTabela();
        imprimeVertices(p5.getVertices());
        t.inserePresente(p6);
        System.out.println(p6.getX() + ":" + p6.getY() + ":" + p6.getZ());
        t.imprimirTabela();
        imprimeVertices(p6.getVertices());
        t.inserePresente(p7);
        System.out.println(p7.getX() + ":" + p7.getY() + ":" + p7.getZ());
        t.imprimirTabela();
        imprimeVertices(p7.getVertices());
    }

    public static void imprimeVertices(List<int[]> vertices) {
        for (int[] vertice : vertices) {
            System.out.println(Arrays.toString(vertice));
        }
    }

}
