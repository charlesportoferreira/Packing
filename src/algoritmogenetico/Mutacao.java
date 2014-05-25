package algoritmogenetico;

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

    public void insereRuido(double min, double max) {
        int dimensao = this.cromossomo.length;
        int rand = 0 + (int) (Math.random() * (((dimensao - 1) - 0) + 1));
        double ruido = min + (Math.random() * ((max - (min))));
        cromossomo[rand] = Math.round(ruido * 100.0) / 100.0;
        // System.out.println("!!!!!!!!!               " + Math.round(ruido * 100.0) / 100.0 + "      !!!!!!!!!!!");
    }

    public void mutacaoUnidimensional(double alpha) {
      

        int[] mascara = new int[cromossomo.length];
        int rand;

        for (int i = 0; i < cromossomo.length; i++) {
            rand = 0 + (int) (Math.random() * ((2 - 0) + 1));
            rand--;
            mascara[i] = rand;
        }
        for (int i = 0; i < cromossomo.length; i++) {
            cromossomo[i] = cromossomo[i] + alpha * mascara[i];
        }
        //System.out.println(rand);
    }

    public void mutacaoUnidimensional(double alpha, double[] melhorCromossomo) {
        int[] mascara = new int[cromossomo.length];
        int rand;

        for (int i = 0; i < cromossomo.length; i++) {
            rand = 0 + (int) (Math.random() * ((2 - 0) + 1));
            rand--;
            mascara[i] = rand;
        }
        for (int i = 0; i < cromossomo.length; i++) {
            cromossomo[i] = melhorCromossomo[i] + alpha * mascara[i];
        }
        //System.out.println(rand);
    }

    public static void main(String args[]) {
        for (int i = 0; i < 10; i++) {
            new Mutacao(new double[10]).mutacaoUnidimensional(i);
        }
    }

}
