package algoritmogenetico;

import java.util.List;
import packing.Presente;

public class Mutacao {

    private final double cromossomo[];

    public Mutacao(double[] cromossomo) {
        this.cromossomo = cromossomo;
    }

    public void trocaPosicao() {
        int dimensao = this.cromossomo.length;
        int rand1 = 0 + (int) (Math.random() * (((dimensao - 1) - 0) + 1));
        int rand2 = 0 + (int) (Math.random() * (((dimensao - 1) - 0) + 1));
        double aux = cromossomo[rand1];
        cromossomo[rand1] = cromossomo[rand2];
        cromossomo[rand2] = aux;
        System.out.println(rand1 + " : " + rand2);
    }


    public static void MutacaoPresente(List<Presente> presentes, int ponto) {

        int p1 = 0 + (int) (Math.random() * (ponto - 0));
        int p2 = 0 + (int) (Math.random() * (ponto - 0));
        Presente aux1 = presentes.get(p1);
       // System.out.println("antes da mutação");
       // imprimirPresentes(new Cromossomo(presentes));
        presentes.set(p1, presentes.get(p2));
        presentes.set(p2, aux1);
       // System.out.println("pos mutação");
       // imprimirPresentes(new Cromossomo(presentes));

    }

    public static void imprimirPresentes(Cromossomo c) {

        for (Presente presente : c.getGenesPresente()) {
            System.out.print(presente.getId() + " ");
        }
        System.out.println();
    }

}
