/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitness;

import java.util.Arrays;

/**
 *
 * @author charles
 */
public class Fitness {

    public static int SPHERE_EVALUATOR = 1;
    public static int GRIEWANK_EVALUATOR = 2;
    public static int RASTRINGIN_EVALUATOR = 3;
    public static int ROSEMBROCK_EVALUATOR = 4;

    private final double[] dados;
    private final int funcaoFitness;

    public Fitness(double[] dados, int funcaoFitness) {
        this.dados = dados;
        this.funcaoFitness = funcaoFitness;
        
    }

    public double getFitness() {
        if(dados== null){
            throw new RuntimeException("Dados Null");
        }
        switch (funcaoFitness) {
            case 1:
                return getSphereEvaluator();
            case 2:
                return getGriewankEvaluator();
            case 3:
                return getRastriginEvaluator();
            case 4:
                return getRosenbrockEvaluator();
            default:
                break;
        }
        throw new RuntimeException("Nenhuma funcao de fitness selecionada");
    }

    

    public double getSphereEvaluator() {

        double somatorio = 0.0;
        for (Double dado : dados) {
            somatorio += Math.pow(dado, 2);
        }

        return somatorio;
    }

    public double getGriewankEvaluator() {
        double somatorio = 0;

        for (double var : dados) {
            somatorio += (Math.pow(var, 2)) / 4000;
        }
        double produtorio = 1;
        for (int i = 0; i < dados.length; i++) {
            produtorio *= Math.cos(dados[i] / Math.sqrt(i + 1));
        }
        return 1 + somatorio - produtorio;
    }

    public double getRastriginEvaluator() {
        double somatorio = 0;
        for (double x : dados) {
            somatorio += Math.pow(x, 2) - 10 * Math.cos(2 * Math.PI * x);
        }
        return 10 * dados.length + somatorio;

    }

    public double getRosenbrockEvaluator() {

        double somatorio = 0;
        for (int i = 0; i < dados.length-1;i++) {
            double exp1 = Math.pow(dados[i], 2);
            double exp2 = Math.pow(exp1 - dados[i+1], 2);
            double exp3 = Math.pow((dados[i] - 1), 2);
            somatorio += 100 * (exp2) + exp3;
        }
        
        return somatorio;

    }

}
