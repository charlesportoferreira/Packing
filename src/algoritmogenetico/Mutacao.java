package algoritmogenetico;

import static algoritmogenetico.Cromossomo.imprimirPresentes;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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


    public static void MutacaoPresente(List<Presente> presentes, int ponto, int deslocamento) {

//        int p1 = 0 + (int) (Math.random() * (ponto - 0));
//        int p2 = 0 + (int) (Math.random() * (ponto - 0));
//        Presente aux1 = presentes.get(p1);
//       // System.out.println("antes da mutação");
//       // imprimirPresentes(new Cromossomo(presentes));
//        presentes.set(p1, presentes.get(p2));
//        presentes.set(p2, aux1);
//       // System.out.println("pos mutação");
//       // imprimirPresentes(new Cromossomo(presentes));

        
         int max = ponto + deslocamento > 1000 ? 1000 : ponto + deslocamento;
         for (int i = 0; i < presentes.size(); i++) {
            int p1 = ponto + (int) (Math.random() * (max - ponto));
            int p2 = ponto + (int) (Math.random() * (max - ponto));
            //p1 = 10;
            //p2 = 0;
            Presente auxP1;
            Presente auxP2;
            try {
                auxP1 = presentes.get(p1).clone();
                auxP2 = presentes.get(p2).clone();
                //System.out.println("Vai trocar " + p1 + " com " + p2);
                //System.out.println("antes da mutação");
                //imprimirPresentes(new Cromossomo(presentes));
                presentes.set(p1, auxP2);
                presentes.set(p2, auxP1);
               // System.out.println("pos mutação");
               // imprimirPresentes(new Cromossomo(presentes));
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Cromossomo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
    }

    public static void imprimirPresentes(Cromossomo c) {

        for (Presente presente : c.getGenesPresente()) {
            System.out.print(presente.getId() + " ");
        }
        System.out.println();
    }

}
