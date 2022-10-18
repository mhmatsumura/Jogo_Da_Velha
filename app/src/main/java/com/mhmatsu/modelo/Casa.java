package com.mhmatsu.modelo;

import android.widget.ImageView;

import java.io.Serializable;


public class Casa implements Serializable {

    //private int posicaoX;
    //private int posicaoY;

    private String conteudo="";

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    private int i;
    private int j;

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}




