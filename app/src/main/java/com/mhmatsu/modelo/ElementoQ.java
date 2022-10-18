package com.mhmatsu.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public class ElementoQ implements Serializable {


    private String estado="";
    private double valorQ_acao[] = {0,0,0,0,0,0,0,0,0};
    private double valorMaximoQ=0;



    public  double[] getValorQ_acao() {
        return valorQ_acao;
    }

    public  void setValorQ_acao(double[] valorQ_acao) {
        valorQ_acao = valorQ_acao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }



    public int getAcaoMaximoValorQ() {
        int acaoMax;
        ArrayList<Integer> maximosDeQ = new ArrayList<Integer>();

        double maximoValorDeQ = getMaximoValorQ();

        for (int i = 0; i < valorQ_acao.length; i++) {//aqui a iteração irá ocorrer
            if (valorQ_acao[i] == maximoValorDeQ){ //caso o valor da posição i seja maior que o valor de max, max será substituído pelo valor da i-ésima posição.

                maximosDeQ.add(i);

            }
        }

        int escolha=new Random().nextInt(maximosDeQ.size());
        acaoMax=maximosDeQ.get(escolha);


        return acaoMax;
    }

    public double getMaximoValorQ() {

        valorMaximoQ = valorQ_acao[0];//aqui a variável max recebe o valor do primeiro item do array
        for (int i = 1; i < valorQ_acao.length; i++) {//aqui a iteração irá ocorrer
            if (valorQ_acao[i] > valorMaximoQ){ //caso o valor da posição i seja maior que o valor de max, max será substituído pelo valor da i-ésima posição.
                valorMaximoQ = valorQ_acao[i];
            }
        }
        return valorMaximoQ;
    }


    public void setNovoValorQ(int acaoAtual, double novoValor) {
        valorQ_acao[acaoAtual]=novoValor;
    }

    public int qtdValorQnaoZerado() {
        int naoZerados=0;
        for (double v: valorQ_acao){
            if (v!=0){
                naoZerados+=1;
            }
        }
        return naoZerados;
    }

    public void marcaEstadoComoUltimoEstado() {
        for (int i = 0; i < valorQ_acao.length; i++) {//aqui a iteração irá ocorrer
            valorQ_acao[i]=0.00000000001;
        }
    }
}




