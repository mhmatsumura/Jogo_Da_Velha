package com.mhmatsu.jogo_da_velha;

import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import com.mhmatsu.modelo.Casa;
import com.mhmatsu.modelo.ElementoQ;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static final Casa[][] casas = new Casa[3][3];
    Casa[][] casas2 = new Casa[3][3];
    private static char[][] casas_char;
    static final ImageView[][] casas_image = new ImageView[3][3];

    private static Handler handler = new Handler(); //contador de tempo

    private static ProgressBar progressbar;
    private static TextView label_final_de_jogo;
    private static EditText edt_vitoria_x;
    private static EditText edt_vitoria_o;
    private static EditText edt_empate;
    private static EditText edt_jogos;
    private static EditText edt_zerar;
    private static RadioGroup grupo_radio;
    private static RadioButton rb_ha,rb_ar,rb_ta;
    private static Button botao_play;
    private static Button botao_zerar;
    private static boolean jogoFinalizado;
    private static Casa casa_elegida;
    private static int vitorias_x;
    private static int vitorias_o;
    private static int empates;
    private static int divisor;
    private static int contadorAcaoAprendiz,contadorAcaoTreinador;
    private static int ultimaAcao;
    private static double num_jogos,num_jogos_imutavel;
    private static int progresso;
    private static Casa casa_acao;
    private static ImageView casa_iv;
    private static ArrayList<ElementoQ> vetorQ = new ArrayList();
    private static String estadoAnterior,estadoAtual,estadoAtualAprendiz;
    private static ArrayList<Casa> cesta_de_elegidas;
    private static boolean vezDoHumano;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressbar = (ProgressBar) findViewById(R.id.progressBar);
        edt_zerar = (EditText) findViewById(R.id.editTextZerar);
        label_final_de_jogo = (TextView)findViewById(R.id.textView_final);
        edt_vitoria_o = (EditText) findViewById(R.id.editTextV1);
        edt_vitoria_x = (EditText) findViewById(R.id.editTextV2);
        edt_jogos = (EditText) findViewById(R.id.editTextNJ);
        edt_empate = (EditText) findViewById(R.id.editTextVE);
        grupo_radio = (RadioGroup) findViewById(R.id.radioGroupModo);
        rb_ar = (RadioButton) findViewById(R.id.rbAR);rb_ha = (RadioButton) findViewById(R.id.rbHA);rb_ta = (RadioButton) findViewById(R.id.rbTA);
        jogoFinalizado = false;
        casa_acao=new Casa();
        vitorias_o=0;vitorias_x=0;empates=0;progresso=0;
        botao_play = findViewById(R.id.btn_play);
        botao_zerar = findViewById(R.id.buttonZerar);
        estadoAnterior="VVVVVVVVV";estadoAtual="VVVVVVVVV";
        contadorAcaoAprendiz = 0;
        contadorAcaoTreinador = 0;
        ultimaAcao=0;
        vezDoHumano=false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                casas[i][j]=new Casa();
                casas[i][j].setConteudo("vazio");
                casas[i][j].setI(i);
                casas[i][j].setJ(j);
            }
        }



        inicializa_casa(casas[0][0],casas_image[0][0]=(ImageView)findViewById(R.id.imageView11));
        inicializa_casa(casas[0][1],casas_image[0][1]=(ImageView)findViewById(R.id.imageView12));
        inicializa_casa(casas[0][2],casas_image[0][2]=(ImageView)findViewById(R.id.imageView13));
        inicializa_casa(casas[1][0],casas_image[1][0]=(ImageView)findViewById(R.id.imageView21));
        inicializa_casa(casas[1][1],casas_image[1][1]=(ImageView)findViewById(R.id.imageView22));
        inicializa_casa(casas[1][2],casas_image[1][2]=(ImageView)findViewById(R.id.imageView23));
        inicializa_casa(casas[2][0],casas_image[2][0]=(ImageView)findViewById(R.id.imageView31));
        inicializa_casa(casas[2][1],casas_image[2][1]=(ImageView)findViewById(R.id.imageView32));
        inicializa_casa(casas[2][2],casas_image[2][2]=(ImageView)findViewById(R.id.imageView33));

        grupo_radio.check(R.id.rbHA);

        rb_ha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetaTotal();
                resetaTabuleiroImagem();
            }
        });

        rb_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetaTotal();
                resetaTabuleiroImagem();
            }
        });

        rb_ta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetaTotal();
                resetaTabuleiroImagem();
            }
        });




        botao_play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                progresso=0;
                resetaTotal();
                num_jogos = Integer.parseInt(edt_jogos.getText().toString());

                divisor=1;

                if (num_jogos > 1000000) {
                    divisor = 100000;
                }else if (num_jogos > 100000) {
                    divisor = 10000;
                }else if (num_jogos > 10000) {
                    divisor = 1000;
                }else if (num_jogos > 1000) {
                    divisor = 100;
                }else if (num_jogos > 100) {
                    divisor = 10;
                }

                progressbar.setMax((int) num_jogos);

                if (grupo_radio.getCheckedRadioButtonId()==R.id.rbTA) {

                    jogadorAprendiz();


                }else{
                     for (ElementoQ eQ : vetorQ) {
                        System.out.println(eQ.getEstado() + Arrays.toString(eQ.getValorQ_acao()));
                    }

                    jogadorQmax();
                }


            }


        });

        botao_zerar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                edt_zerar.setText("0");
                vetorQ.clear();

                /*ArrayList<String> Estados= new ArrayList<String>();


                for (int i = 0; i < 9; i++) {

                    converte_acao_casa(i).setConteudo("circulo");

                    for (int j = 0; j < 9; j++) {

                        if (converte_acao_casa(j).getConteudo().equals("vazio")){

                            converte_acao_casa(j).setConteudo("xis");
                            Estados.add(imprimeEstado());

                            for (int k = 0; k < 9; k++) {

                                if (converte_acao_casa(k).getConteudo().equals("vazio")) {
                                    converte_acao_casa(k).setConteudo("circulo");

                                    for (int l = 0; l < 9; l++) {

                                        if (converte_acao_casa(l).getConteudo().equals("vazio")) {

                                            converte_acao_casa(l).setConteudo("xis");
                                            Estados.add(imprimeEstado());



                                            for (int m = 0; m < 9; m++) {

                                                if (converte_acao_casa(m).getConteudo().equals("vazio")) {
                                                    converte_acao_casa(m).setConteudo("circulo");

                                                    for (int n = 0; n < 9; n++) {

                                                        if (converte_acao_casa(n).getConteudo().equals("vazio")) {

                                                            converte_acao_casa(n).setConteudo("xis");
                                                            Estados.add(imprimeEstado());

                                                            for (int o = 0; o < 9; o++) {

                                                                if (converte_acao_casa(o).getConteudo().equals("vazio")) {
                                                                    converte_acao_casa(o).setConteudo("circulo");

                                                                    for (int p = 0; p < 9; p++) {

                                                                        if (converte_acao_casa(p).getConteudo().equals("vazio")) {

                                                                            converte_acao_casa(p).setConteudo("xis");
                                                                            Estados.add(imprimeEstado());

                                                                            converte_acao_casa(p).setConteudo("vazio");
                                                                        }
                                                                    }

                                                                    converte_acao_casa(o).setConteudo("vazio");
                                                                }
                                                            }

                                                            converte_acao_casa(n).setConteudo("vazio");
                                                        }
                                                    }

                                                    converte_acao_casa(m).setConteudo("vazio");
                                                }
                                            }


                                            converte_acao_casa(l).setConteudo("vazio");
                                        }

                                    }

                                    converte_acao_casa(k).setConteudo("vazio");
                                }


                            }
                            converte_acao_casa(j).setConteudo("vazio");
                        }


                    }


                    converte_acao_casa(i).setConteudo("vazio");
                }

                String estadoAnterior2= "";


                Collections.sort(Estados);
                int cont=0;

                for (String estado : Estados) {

                    if (estado.equals(estadoAnterior2)){
                        //System.out.println(estado);
                        cont+=1;
                    }
                    estadoAnterior2=estado;

                }

                System.out.println("contador: "+cont);


                //for (String estado : Estados) {
                    //System.out.println(estado);
                //}
                System.out.println("total de estados: "+Estados.size());


*/
            }


        });


    }



    public static void atualizaQ(String estadoAnterior, String estadoAtual, int ultimaAcao){

        double Q_estadoAnterior_ultimaAcao = elementoQ(estadoAnterior).getValorQ_acao()[ultimaAcao];

        double maximo = elementoQ(estadoAtual).getMaximoValorQ();

        //Qt+1(st, at) = Qt(st, at) + [rt +  maxa Q(st+1,a) - Qt(st, at)]

        /*System.out.println("ultimaAcao "+ultimaAcao);
        System.out.println("Q_estadoAnterior_ultimaAcao "+Q_estadoAnterior_ultimaAcao);
        System.out.println("reforco(estadoAnterior,estadoAtual) "+reforco(estadoAnterior,estadoAtual));
        System.out.println("estadoAnterior "+estadoAnterior);
        System.out.println("estadoAtual "+estadoAtual);
        System.out.println("maximo "+maximo);
        System.out.println("");*/

        Q_estadoAnterior_ultimaAcao = Q_estadoAnterior_ultimaAcao+0.01*(reforco(estadoAnterior,estadoAtual) + (0.5* maximo)-Q_estadoAnterior_ultimaAcao);

        elementoQ(estadoAnterior).setNovoValorQ(ultimaAcao, Q_estadoAnterior_ultimaAcao);



    }

    private static int reforco(String estadoAnterior, String estadoAtual) {

        int pontuacaoEAnterior = calculaPontuacao(estadoAnterior);
        int pontuacaoEAtual = calculaPontuacao(estadoAtual);

       //System.out.println ("pontuacaoEAtual "+pontuacaoEAtual);
        //System.out.println ("pontuacaoEAnterior "+pontuacaoEAnterior);

        if(pontuacaoEAtual>9000){
            return  100;
        }else if(pontuacaoEAtual<-9000){
            return -100;
        }else if(pontuacaoEAtual>pontuacaoEAnterior){
            return   50;
        }else if(pontuacaoEAtual<pontuacaoEAnterior){
            return  -50;
        }else if(pontuacaoEAtual==pontuacaoEAnterior){
            return    0;
        }



        return    0;

    }

    private static int calculaPontuacao(String estado) {

        char[] caracteres = estado.toCharArray();
        casas_char= new char[][]{{caracteres[0], caracteres[1], caracteres[2]},
                                 {caracteres[3], caracteres[4], caracteres[5]},
                                 {caracteres[6], caracteres[7], caracteres[8]}};

        //System.out.println("estado "+estado);
        //for (char[] linhas : casas_char) {
            //for (char colunas : linhas) {
                //System.out.println(colunas);
            //}
        //}

        //System.out.println("casas_char[0][0],casas_char[0][1],casas_char[0][2] "+casas_char[0][0]+casas_char[0][1]+casas_char[0][2]);


        return (pontuacaoTupla(casas_char[0][0],casas_char[0][1],casas_char[0][2])+
        pontuacaoTupla(casas_char[0][0],casas_char[1][1],casas_char[2][2])+
        pontuacaoTupla(casas_char[0][0],casas_char[1][0],casas_char[2][0])+
        pontuacaoTupla(casas_char[2][0],casas_char[2][1],casas_char[2][2])+
        pontuacaoTupla(casas_char[0][2],casas_char[1][2],casas_char[2][2])+
        pontuacaoTupla(casas_char[0][2],casas_char[1][1],casas_char[2][0])+
        pontuacaoTupla(casas_char[1][0],casas_char[1][1],casas_char[1][2])+
        pontuacaoTupla(casas_char[0][1],casas_char[1][1],casas_char[2][1]));
    }

    private static int pontuacaoTupla(char c, char c1, char c2) {

        int numero_circulos=0;
        int numero_xis=0;
        int pontuacao = 0;

        if (c == 'O') {
            numero_circulos+=1;
        }else if(c == 'X'){
            numero_xis+=1;
        }
        if (c1 == 'O') {
            numero_circulos+=1;
        }else if(c1 == 'X'){
            numero_xis+=1;
        }
        if (c2 == 'O') {
            numero_circulos+=1;
        }else if(c2 == 'X'){
            numero_xis+=1;
        }

        if (numero_circulos==1){

            pontuacao+=10;

        }else if (numero_circulos==2){

            pontuacao+=100;

        }else if (numero_circulos==3){

            pontuacao+=10000;

        }

        if (numero_xis==1){

            pontuacao-=10;

        }else if (numero_xis==2){

            pontuacao-=100;

        }else if (numero_xis==3){

            pontuacao-=10000;

        }



       // System.out.println("tupla "+c+c1+c2+" numero_circulos "+numero_circulos+" numero_xis "+numero_xis+" pontuacao "+pontuacao);

        return pontuacao;

    }

    private static ElementoQ elementoQ(String estado) {
        for (ElementoQ eQ : vetorQ) {
            if (eQ.getEstado().equals(estado)){
                return eQ;
            }
        }

        ElementoQ eQ2= new ElementoQ();
        eQ2.setEstado(estado);
        vetorQ.add(eQ2);

        for (ElementoQ eQ : vetorQ) {
            if (eQ.getEstado().equals(estado)){
                return eQ;
            }
        }

        return null;
    }




    private static int converte_casa_acao(Casa casa) {

        if ((casa.getI()==0)&&(casa.getJ()==0)){
            return 0;
        }else if ((casa.getI()==0)&&(casa.getJ()==1)){
            return 1;
        }if ((casa.getI()==0)&&(casa.getJ()==2)){
            return 2;
        }if ((casa.getI()==1)&&(casa.getJ()==0)){
            return 3;
        }if ((casa.getI()==1)&&(casa.getJ()==1)){
            return 4;
        }if ((casa.getI()==1)&&(casa.getJ()==2)){
            return 5;
        }if ((casa.getI()==2)&&(casa.getJ()==0)){
            return 6;
        }if ((casa.getI()==2)&&(casa.getJ()==1)){
            return 7;
        }else{
            return 8;
        }
    }


    private static void jogadorQmax() {


        estadoAtual=imprimeEstado();

        int acaoComMaximoQ = elementoQ(estadoAtual).getAcaoMaximoValorQ();
        //System.out.println(estadoAtual+"elementoQ(estadoAtual).getAcaoMaximoValorQ() "+elementoQ(estadoAtual).getAcaoMaximoValorQ()+" "+Arrays.toString(elementoQ(estadoAtual).getValorQ_acao()));
        casa_elegida=converte_acao_casa(acaoComMaximoQ);

        while (!casa_elegida.getConteudo().equals("vazio")){

            elementoQ(estadoAtual).setNovoValorQ(acaoComMaximoQ,-10000);
            acaoComMaximoQ = elementoQ(estadoAtual).getAcaoMaximoValorQ();
            casa_elegida=converte_acao_casa(acaoComMaximoQ);
        }

        if (grupo_radio.getCheckedRadioButtonId()==R.id.rbHA) {
            casas_image[casa_elegida.getI()][casa_elegida.getJ()].setImageResource(R.drawable.circulo);
            vezDoHumano=true;

        }
            casa_elegida.setConteudo("circulo");


        //System.out.println(estadoAtual+" Casa elegida pelo Qmax: "+casa_elegida.getI()+casa_elegida.getJ());



            verificaVencedor();



        if (grupo_radio.getCheckedRadioButtonId()==R.id.rbAR) {
            if(!jogoFinalizado) {
                handler.post(MainActivity::jogadorInteligente); //conta 1 segundo
            }else if (num_jogos>1) {
                //System.out.println("*************************** COMECOU NOVO JOGO *****************************");
                num_jogos-=1;
                jogoFinalizado = false;
                estadoAnterior="VVVVVVVVV";
                estadoAtual="VVVVVVVVV";

                atualizaProgresso();

                handler.post(MainActivity::jogadorQmax); //conta 1 segundo

            }
        }



    }

    private static Casa converte_acao_casa(int acao) {

        switch(acao){

            case 0: return casas[0][0];
            case 1: return casas[0][1];
            case 2: return casas[0][2];
            case 3: return casas[1][0];
            case 4: return casas[1][1];
            case 5: return casas[1][2];
            case 6: return casas[2][0];
            case 7: return casas[2][1];
            case 8: return casas[2][2];


        }
        return null;
    }



    private static void jogadorInteligente() {

        casa_elegida=null;

        cesta_de_elegidas = new ArrayList();

        cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[0][1], casas[0][2], 1));
        cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[1][1], casas[2][2], 1));
        cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[1][0], casas[2][0], 1));
        cesta_de_elegidas.add(analisaTupla(casas[2][0], casas[2][1], casas[2][2], 1));
        cesta_de_elegidas.add(analisaTupla(casas[0][2], casas[1][2], casas[2][2], 1));
        cesta_de_elegidas.add(analisaTupla(casas[0][2], casas[1][1], casas[2][0], 1));
        cesta_de_elegidas.add(analisaTupla(casas[1][0], casas[1][1], casas[1][2], 1));
        cesta_de_elegidas.add(analisaTupla(casas[0][1], casas[1][1], casas[2][1], 1));

        Iterator<Casa> it = cesta_de_elegidas.iterator();
        while(it.hasNext()){
            Casa c = it.next();
            if(c == null){
                it.remove();
            }
        }


        if (cesta_de_elegidas.size() == 0) {

            cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[0][1], casas[0][2], 2));
            cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[1][1], casas[2][2], 2));
            cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[1][0], casas[2][0], 2));
            cesta_de_elegidas.add(analisaTupla(casas[2][0], casas[2][1], casas[2][2], 2));
            cesta_de_elegidas.add(analisaTupla(casas[0][2], casas[1][2], casas[2][2], 2));
            cesta_de_elegidas.add(analisaTupla(casas[0][2], casas[1][1], casas[2][0], 2));
            cesta_de_elegidas.add(analisaTupla(casas[1][0], casas[1][1], casas[1][2], 2));
            cesta_de_elegidas.add(analisaTupla(casas[0][1], casas[1][1], casas[2][1], 2));

            it = cesta_de_elegidas.iterator();
            while(it.hasNext()){
                Casa c = it.next();
                if(c == null){
                    it.remove();
                }
            }

            if (cesta_de_elegidas.size() == 0) {

                //tratando excecoes onde algoritmo nao eh eficiente
                if (casas[1][1].getConteudo().equals("circulo")){
                    if(casas[0][0].getConteudo().equals("vazio")){
                        casa_elegida=casas[0][0];
                    }else if(casas[2][0].getConteudo().equals("vazio")){
                        casa_elegida=casas[2][0];
                    }else if(casas[0][2].getConteudo().equals("vazio")){
                        casa_elegida=casas[0][2];
                    }else if(casas[2][2].getConteudo().equals("vazio")){
                        casa_elegida=casas[2][2];
                    }
                }else if ((casas[2][1].getConteudo().equals("circulo"))&&(casas[1][2].getConteudo().equals("circulo"))){
                    if(casas[2][2].getConteudo().equals("vazio")){
                        casa_elegida=casas[2][2];
                    }else if(casas[0][2].getConteudo().equals("vazio")){
                        casa_elegida=casas[0][2];
                    }else if(casas[2][0].getConteudo().equals("vazio")){
                        casa_elegida=casas[2][0];
                    }

                }else if ( (casas[0][2].getConteudo().equals("circulo"))&&(casas[2][0].getConteudo().equals("circulo")) ||
                        (casas[0][0].getConteudo().equals("circulo"))&&(casas[2][2].getConteudo().equals("circulo")) ){

                    if(casas[0][1].getConteudo().equals("vazio")){
                        casa_elegida=casas[0][1];
                    }else if(casas[1][0].getConteudo().equals("vazio")){
                        casa_elegida=casas[1][0];
                    }else if(casas[2][1].getConteudo().equals("vazio")){
                        casa_elegida=casas[2][1];
                    }else if(casas[1][2].getConteudo().equals("vazio")){
                        casa_elegida=casas[1][2];
                    }

                }else if ((casas[0][1].getConteudo().equals("circulo"))&&(casas[2][2].getConteudo().equals("circulo"))){
                    if(casas[0][2].getConteudo().equals("vazio")){
                        casa_elegida=casas[0][2];
                    }
                }else if ((casas[0][1].getConteudo().equals("circulo"))&&(casas[2][0].getConteudo().equals("circulo"))){
                    if(casas[0][0].getConteudo().equals("vazio")){
                        casa_elegida=casas[0][0];
                    }
                }else if ((casas[1][0].getConteudo().equals("circulo"))&&(casas[0][2].getConteudo().equals("circulo"))){
                    if(casas[0][0].getConteudo().equals("vazio")){
                        casa_elegida=casas[0][0];
                    }
                }else if ((casas[1][0].getConteudo().equals("circulo"))&&(casas[2][2].getConteudo().equals("circulo"))){
                    if(casas[2][0].getConteudo().equals("vazio")){
                        casa_elegida=casas[2][0];
                    }
                }else if ((casas[2][1].getConteudo().equals("circulo"))&&(casas[0][0].getConteudo().equals("circulo"))){
                    if(casas[2][0].getConteudo().equals("vazio")){
                        casa_elegida=casas[2][0];
                    }
                }else if ((casas[2][1].getConteudo().equals("circulo"))&&(casas[0][2].getConteudo().equals("circulo"))){
                    if(casas[2][2].getConteudo().equals("vazio")){
                        casa_elegida=casas[2][2];
                    }
                }else if ((casas[1][2].getConteudo().equals("circulo"))&&(casas[0][0].getConteudo().equals("circulo"))){
                    if(casas[0][2].getConteudo().equals("vazio")){
                        casa_elegida=casas[0][2];
                    }
                }else if ((casas[1][2].getConteudo().equals("circulo"))&&(casas[2][0].getConteudo().equals("circulo"))){
                    if(casas[2][2].getConteudo().equals("vazio")){
                        casa_elegida=casas[2][2];
                    }
                }

                if (casa_elegida == null) {

                    cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[0][1], casas[0][2], 3));
                    cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[1][1], casas[2][2], 3));
                    cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[1][0], casas[2][0], 3));
                    cesta_de_elegidas.add(analisaTupla(casas[2][0], casas[2][1], casas[2][2], 3));
                    cesta_de_elegidas.add(analisaTupla(casas[0][2], casas[1][2], casas[2][2], 3));
                    cesta_de_elegidas.add(analisaTupla(casas[0][2], casas[1][1], casas[2][0], 3));
                    cesta_de_elegidas.add(analisaTupla(casas[1][0], casas[1][1], casas[1][2], 3));
                    cesta_de_elegidas.add(analisaTupla(casas[0][1], casas[1][1], casas[2][1], 3));

                    it = cesta_de_elegidas.iterator();
                    while(it.hasNext()){
                        Casa c = it.next();
                        if(c == null){
                            it.remove();
                        }
                    }

                    if (cesta_de_elegidas.size() == 0) {


                        if (casas[1][1].getConteudo() == "vazio") {
                            casa_elegida = casas[1][1];
                        } else {
                            //System.out.println("-----------------------------------------------------------explorando(inteligente)");
                            casa_elegida = elegeCasaRandomicamente();
                            while (!casa_elegida.getConteudo().equals("vazio")) {
                                casa_elegida = elegeCasaRandomicamente();
                            }
                        }

                    }

                }

            }

        }

        if (casa_elegida == null) {
            //System.out.println("-----------------------------------------------------------explorando_casaselegidas(jogador inteligente)");
            int escolha=new Random().nextInt(cesta_de_elegidas.size());
            casa_elegida=cesta_de_elegidas.get(escolha);
        }else{
            //System.out.println("-----------------------------------------------------------inteligente(jogador inteligente)");
        }


        //System.out.println(estadoAtual+" Casa elegida pelo Inteligente: "+casa_elegida.getI()+casa_elegida.getJ());
        casa_elegida.setConteudo("xis");



        verificaVencedor();


            if (!jogoFinalizado) {

                handler.post(MainActivity::jogadorQmax); //conta 1 segundo

            }else if (num_jogos>1) {
                //System.out.println("*************************** COMECOU NOVO JOGO *****************************");
                num_jogos-=1;
                jogoFinalizado = false;
                estadoAnterior="VVVVVVVVV";
                estadoAtual="VVVVVVVVV";

                atualizaProgresso(); //conta 1 segundo

                handler.post(MainActivity::jogadorQmax); //conta 1 segundo

            }else{
                //System.out.println("quantidade de estados: "+vetorQ.size());
                //for (ElementoQ eQ : vetorQ) {

                    //System.out.println(eQ.getEstado()+Arrays.toString(eQ.getValorQ_acao()));

                //}
            }


    }

    private void jogadorAprendiz() {


        casa_elegida=elegeCasaRandomicamente();

        while (!casa_elegida.getConteudo().equals("vazio")){
            elementoQ(estadoAtual).setNovoValorQ(converte_casa_acao(casa_elegida),-10000);
            casa_elegida=elegeCasaRandomicamente();
        }

        casa_elegida.setConteudo("circulo");

        ultimaAcao=converte_casa_acao(casa_elegida);

        estadoAtualAprendiz=imprimeEstado();
        verificaVencedor();

        if(!jogoFinalizado) {
            handler.post(MainActivity.this::jogadorTreinador); //conta 1 segundo
        }else {

            estadoAnterior = estadoAtual;
            estadoAtual = estadoAtualAprendiz;

            atualizaQ(estadoAnterior, estadoAtual, ultimaAcao);

            elementoQ(estadoAtual).marcaEstadoComoUltimoEstado();

            if (num_jogos>1) {
                //System.out.println("*************************** COMECOU NOVO JOGO *****************************");
                num_jogos-=1;
                jogoFinalizado = false;
                estadoAnterior="VVVVVVVVV";

                estadoAtual="VVVVVVVVV";

                atualizaProgresso();

                handler.post(MainActivity.this::jogadorAprendiz); //conta 1 segundo

            }else{
                //System.out.println("quantidade de estados: "+vetorQ.size());
                Toast.makeText(MainActivity.this, "TREINAMENTO FINALIZADO!\nQuantidade de estados: "+vetorQ.size(), Toast.LENGTH_SHORT).show();
                edt_zerar.setText(""+andamentoTreinamento()+"%");
                //System.out.println("Porcentagem treinado: "+andamentoTreinamento());
                //System.out.println("estado VXVXVOOXO: " + Arrays.toString(elementoQ("VXVXVOOXO").getValorQ_acao()));
               // for (ElementoQ eQ : vetorQ) {
                    //System.out.println(eQ.getEstado() + Arrays.toString(eQ.getValorQ_acao()));
                //}

            }
        }

    }



    private void jogadorTreinador() {

        if (contadorAcaoTreinador < 7){
            //System.out.println("-----------------------------------------------------------explorando(jogador Aprendiz)");
            casa_elegida=elegeCasaRandomicamente();

            while (!casa_elegida.getConteudo().equals("vazio")){

                casa_elegida=elegeCasaRandomicamente();
            }

        }else if (contadorAcaoTreinador >= 7){
            casa_elegida=null;

            cesta_de_elegidas = new ArrayList();

            cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[0][1], casas[0][2], 1));
            cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[1][1], casas[2][2], 1));
            cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[1][0], casas[2][0], 1));
            cesta_de_elegidas.add(analisaTupla(casas[2][0], casas[2][1], casas[2][2], 1));
            cesta_de_elegidas.add(analisaTupla(casas[0][2], casas[1][2], casas[2][2], 1));
            cesta_de_elegidas.add(analisaTupla(casas[0][2], casas[1][1], casas[2][0], 1));
            cesta_de_elegidas.add(analisaTupla(casas[1][0], casas[1][1], casas[1][2], 1));
            cesta_de_elegidas.add(analisaTupla(casas[0][1], casas[1][1], casas[2][1], 1));

            Iterator<Casa> it = cesta_de_elegidas.iterator();
            while(it.hasNext()){
                Casa c = it.next();
                if(c == null){
                    it.remove();
                }
            }


            if (cesta_de_elegidas.size() == 0) {

                cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[0][1], casas[0][2], 2));
                cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[1][1], casas[2][2], 2));
                cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[1][0], casas[2][0], 2));
                cesta_de_elegidas.add(analisaTupla(casas[2][0], casas[2][1], casas[2][2], 2));
                cesta_de_elegidas.add(analisaTupla(casas[0][2], casas[1][2], casas[2][2], 2));
                cesta_de_elegidas.add(analisaTupla(casas[0][2], casas[1][1], casas[2][0], 2));
                cesta_de_elegidas.add(analisaTupla(casas[1][0], casas[1][1], casas[1][2], 2));
                cesta_de_elegidas.add(analisaTupla(casas[0][1], casas[1][1], casas[2][1], 2));

                it = cesta_de_elegidas.iterator();
                while(it.hasNext()){
                    Casa c = it.next();
                    if(c == null){
                        it.remove();
                    }
                }

                if (cesta_de_elegidas.size() == 0) {

                    //tratando excecoes onde algoritmo nao eh eficiente
                    if (casas[1][1].getConteudo().equals("circulo")){
                        if(casas[0][0].getConteudo().equals("vazio")){
                            casa_elegida=casas[0][0];
                        }else if(casas[2][0].getConteudo().equals("vazio")){
                            casa_elegida=casas[2][0];
                        }else if(casas[0][2].getConteudo().equals("vazio")){
                            casa_elegida=casas[0][2];
                        }else if(casas[2][2].getConteudo().equals("vazio")){
                            casa_elegida=casas[2][2];
                        }
                    }else if ((casas[2][1].getConteudo().equals("circulo"))&&(casas[1][2].getConteudo().equals("circulo"))){
                        if(casas[2][2].getConteudo().equals("vazio")){
                            casa_elegida=casas[2][2];
                        }else if(casas[0][2].getConteudo().equals("vazio")){
                            casa_elegida=casas[0][2];
                        }else if(casas[2][0].getConteudo().equals("vazio")){
                            casa_elegida=casas[2][0];
                        }

                    }else if ( (casas[0][2].getConteudo().equals("circulo"))&&(casas[2][0].getConteudo().equals("circulo")) ||
                            (casas[0][0].getConteudo().equals("circulo"))&&(casas[2][2].getConteudo().equals("circulo")) ){

                        if(casas[0][1].getConteudo().equals("vazio")){
                            casa_elegida=casas[0][1];
                        }else if(casas[1][0].getConteudo().equals("vazio")){
                            casa_elegida=casas[1][0];
                        }else if(casas[2][1].getConteudo().equals("vazio")){
                            casa_elegida=casas[2][1];
                        }else if(casas[1][2].getConteudo().equals("vazio")){
                            casa_elegida=casas[1][2];
                        }

                    }else if ((casas[0][1].getConteudo().equals("circulo"))&&(casas[2][2].getConteudo().equals("circulo"))){
                        if(casas[0][2].getConteudo().equals("vazio")){
                            casa_elegida=casas[0][2];
                        }
                    }else if ((casas[0][1].getConteudo().equals("circulo"))&&(casas[2][0].getConteudo().equals("circulo"))){
                        if(casas[0][0].getConteudo().equals("vazio")){
                            casa_elegida=casas[0][0];
                        }
                    }else if ((casas[1][0].getConteudo().equals("circulo"))&&(casas[0][2].getConteudo().equals("circulo"))){
                        if(casas[0][0].getConteudo().equals("vazio")){
                            casa_elegida=casas[0][0];
                        }
                    }else if ((casas[1][0].getConteudo().equals("circulo"))&&(casas[2][2].getConteudo().equals("circulo"))){
                        if(casas[2][0].getConteudo().equals("vazio")){
                            casa_elegida=casas[2][0];
                        }
                    }else if ((casas[2][1].getConteudo().equals("circulo"))&&(casas[0][0].getConteudo().equals("circulo"))){
                        if(casas[2][0].getConteudo().equals("vazio")){
                            casa_elegida=casas[2][0];
                        }
                    }else if ((casas[2][1].getConteudo().equals("circulo"))&&(casas[0][2].getConteudo().equals("circulo"))){
                        if(casas[2][2].getConteudo().equals("vazio")){
                            casa_elegida=casas[2][2];
                        }
                    }else if ((casas[1][2].getConteudo().equals("circulo"))&&(casas[0][0].getConteudo().equals("circulo"))){
                        if(casas[0][2].getConteudo().equals("vazio")){
                            casa_elegida=casas[0][2];
                        }
                    }else if ((casas[1][2].getConteudo().equals("circulo"))&&(casas[2][0].getConteudo().equals("circulo"))){
                        if(casas[2][2].getConteudo().equals("vazio")){
                            casa_elegida=casas[2][2];
                        }
                    }

                    if (casa_elegida == null) {

                        cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[0][1], casas[0][2], 3));
                        cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[1][1], casas[2][2], 3));
                        cesta_de_elegidas.add(analisaTupla(casas[0][0], casas[1][0], casas[2][0], 3));
                        cesta_de_elegidas.add(analisaTupla(casas[2][0], casas[2][1], casas[2][2], 3));
                        cesta_de_elegidas.add(analisaTupla(casas[0][2], casas[1][2], casas[2][2], 3));
                        cesta_de_elegidas.add(analisaTupla(casas[0][2], casas[1][1], casas[2][0], 3));
                        cesta_de_elegidas.add(analisaTupla(casas[1][0], casas[1][1], casas[1][2], 3));
                        cesta_de_elegidas.add(analisaTupla(casas[0][1], casas[1][1], casas[2][1], 3));

                        it = cesta_de_elegidas.iterator();
                        while(it.hasNext()){
                            Casa c = it.next();
                            if(c == null){
                                it.remove();
                            }
                        }

                        if (cesta_de_elegidas.size() == 0) {


                            if (casas[1][1].getConteudo() == "vazio") {
                                casa_elegida = casas[1][1];
                            } else {
                                //System.out.println("-----------------------------------------------------------explorando(inteligente)");
                                casa_elegida = elegeCasaRandomicamente();
                                while (!casa_elegida.getConteudo().equals("vazio")) {
                                    casa_elegida = elegeCasaRandomicamente();
                                }
                            }

                        }

                    }

                }

            }

            if (casa_elegida == null) {
                //System.out.println("-----------------------------------------------------------explorando_casaselegidas(jogador inteligente)");
                int escolha=new Random().nextInt(cesta_de_elegidas.size());
                casa_elegida=cesta_de_elegidas.get(escolha);
            }

        }

        contadorAcaoTreinador +=1;

        if (contadorAcaoTreinador > 9){
            contadorAcaoTreinador = 0;
        }


        //System.out.println(estadoAtual+" Casa elegida pelo Inteligente: "+casa_elegida.getI()+casa_elegida.getJ());
        casa_elegida.setConteudo("xis");

        estadoAnterior = estadoAtual;
        estadoAtual = imprimeEstado();

        atualizaQ(estadoAnterior, estadoAtual, ultimaAcao);

        verificaVencedor();

            if (!jogoFinalizado) {

                handler.post(MainActivity.this::jogadorAprendiz);

            }else {

                elementoQ(estadoAtual).marcaEstadoComoUltimoEstado();

                if (num_jogos > 1) {
                    //System.out.println("*************************** COMECOU NOVO JOGO *****************************");
                    num_jogos -= 1;
                    jogoFinalizado = false;
                    estadoAnterior = "VVVVVVVVV";
                    estadoAtual = "VVVVVVVVV";

                    atualizaProgresso(); //conta 1 segundo

                    handler.post(MainActivity.this::jogadorAprendiz); //conta 1 segundo

                } else {
                    //.out.println("quantidade de estados: " + vetorQ.size());
                    Toast.makeText(MainActivity.this, "TREINAMENTO FINALIZADO!\nQuantidade de estados: " + vetorQ.size(), Toast.LENGTH_SHORT).show();
                    edt_zerar.setText(""+andamentoTreinamento()+"%");
                    //System.out.println("Porcentagem treinado: " + andamentoTreinamento());
                    //System.out.println("estado VXVXVOOXO: " + Arrays.toString(elementoQ("VXVXVOOXO").getValorQ_acao()));
                   // for (ElementoQ eQ : vetorQ) {

                        //System.out.println(eQ.getEstado() + Arrays.toString(eQ.getValorQ_acao()));

                    //}
                }
            }
    }

    private int andamentoTreinamento() {
        int valoresQtotal = (3381*9);//3381 é o maximo de estados existentes
        int valoresQEncontrado=1;
        for (ElementoQ eq :vetorQ){
            valoresQEncontrado+=eq.qtdValorQnaoZerado();
        }

        return (100*valoresQEncontrado)/valoresQtotal;
    }

    private static void atualizaProgresso() {

            if ((progresso += 1) % divisor == 0) {
                progressbar.setProgress(progresso);
                edt_empate.setText("" + empates);
                edt_vitoria_o.setText("" + vitorias_o);
                edt_vitoria_x.setText("" + vitorias_x);
            }

    }

    private static void atualizaProgressoImagem() {

            edt_empate.setText("" + empates);
            edt_vitoria_o.setText("" + vitorias_o);
            edt_vitoria_x.setText("" + vitorias_x);

    }

    private static boolean verificaVencedor() {

        if (!comparaCasas(casas[0][0],casas[0][1],casas[0][2])){
            if (!comparaCasas(casas[0][0],casas[1][1],casas[2][2])){
                if (!comparaCasas(casas[0][0],casas[1][0],casas[2][0])){
                    if (!comparaCasas(casas[2][0],casas[2][1],casas[2][2])){
                        if (!comparaCasas(casas[0][2],casas[1][2],casas[2][2])){
                            if (!comparaCasas(casas[0][2],casas[1][1],casas[2][0])){
                                if (!comparaCasas(casas[1][0],casas[1][1],casas[1][2])){
                                    if (!comparaCasas(casas[0][1],casas[1][1],casas[2][1])){
                                        if(quantidadeDeVazios()==0){
                                            jogoFinalizado = true;
                                            empates+=1;
                                            //System.out.println("Vencedor"+imprimeEstado());

                                            if (grupo_radio.getCheckedRadioButtonId()==R.id.rbHA) {
                                                atualizaProgressoImagem();
                                                handler.postDelayed(MainActivity::escreveFinal, 300); //conta 1 segundo
                                                handler.postDelayed(MainActivity::resetaTabuleiroImagem, 2000); //conta 1 segundo
                                            }else{
                                                resetaTabuleiro();
                                            }

                                            return true;

                                        }else{

                                            return false;
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        jogoFinalizado = true;
        //System.out.println("Vencedor"+imprimeEstado());

        if (grupo_radio.getCheckedRadioButtonId()==R.id.rbHA) {
            atualizaProgressoImagem();
            handler.postDelayed(MainActivity::escreveFinal, 300); //conta 1 segundo
            handler.postDelayed(MainActivity::resetaTabuleiroImagem, 2000); //conta 1 segundo
        }else{
            resetaTabuleiro();
        }

        return true;
    }

    private static void resetaTotal() {
        resetaTabuleiro();
        vitorias_o=0;
        vitorias_x=0;
        empates=0;
        edt_empate.setText(""+empates);
        edt_vitoria_o.setText(""+vitorias_o);
        edt_vitoria_x.setText(""+vitorias_x);
        progressbar.setProgress(0);
        jogoFinalizado = false;
        estadoAnterior="VVVVVVVVV";
        estadoAtual="VVVVVVVVV";
        vezDoHumano=false;
    }

    private static void resetaTabuleiro() {
        for (Casa[] linhas : casas) {
            for (Casa colunas : linhas) {
                colunas.setConteudo("vazio");
            }
        }

    }

    private static void resetaTabuleiroImagem() {
        for (Casa[] linhas : casas) {
            for (Casa colunas : linhas) {
                colunas.setConteudo("vazio");
            }
        }

        for (ImageView[] linhas : casas_image) {
            for (ImageView colunas : linhas) {
                colunas.setImageResource(0);
            }
        }

        label_final_de_jogo.setVisibility(View.INVISIBLE);
        jogoFinalizado = false;
        vezDoHumano=false;

        handler.postDelayed(MainActivity::jogadorQmax, 300);

    }

    private void inicializa_casa(Casa casas,ImageView iv) {
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (grupo_radio.getCheckedRadioButtonId()==R.id.rbHA) {
                    if (vezDoHumano) {

                        if ((casas.getConteudo().equals("vazio")) && (!jogoFinalizado)) {
                            iv.setImageResource(R.drawable.xis);
                            casas.setConteudo("xis");
                            vezDoHumano=false;

                            if ((!verificaVencedor()) && (quantidadeDeVazios() > 0)) {
                                handler.postDelayed(MainActivity::jogadorQmax, 300); //conta 1 segundo

                            }
                        }

                    }
                }
            }
        });
    }






    private static void escreveFinal() {
        label_final_de_jogo.setVisibility(View.VISIBLE);
    }

    private static boolean comparaCasas(Casa casa, Casa casa1, Casa casa2) {
        if (!casa.getConteudo().equals("vazio")){
            if ((casa.getConteudo().equals(casa1.getConteudo())) && (casa1.getConteudo().equals(casa2.getConteudo()))){

                if (casa.getConteudo().equals("xis")) {
                    vitorias_x+=1;
                }else{
                    vitorias_o+=1;

                }
                return true;

            }
        }

        return false;
    }

    private static String imprimeEstado() {
        String estado="";
        for (Casa[] linhas : casas) {
            for (Casa colunas : linhas) {
                if(colunas.getConteudo().equals("vazio")){
                    estado+="V";
                }else if(colunas.getConteudo().equals("circulo")){
                    estado+="O";
                }else if(colunas.getConteudo().equals("xis")){
                    estado+="X";
                }
            }
        }
        return estado;
    }



    private static Casa analisaTupla(Casa casa, Casa casa1, Casa casa2, int prioridade) {

        int numero_circulos=0;int numero_xis=0;

        if (casa.getConteudo().equals("circulo")) {
            numero_circulos+=1;
        }else if (casa.getConteudo().equals("xis")) {
            numero_xis+=1;
        }

        if (casa1.getConteudo().equals("circulo")) {
            numero_circulos+=1;
        }else if (casa1.getConteudo().equals("xis")) {
            numero_xis+=1;
        }

        if (casa2.getConteudo().equals("circulo")) {
            numero_circulos+=1;
        }else if (casa2.getConteudo().equals("xis")) {
            numero_xis+=1;
        }



        switch (prioridade){
            case 1:

                if (numero_xis==2){
                    if (numero_circulos==1) {
                        return null;
                    }else if(casa.getConteudo().equals("vazio")){
                        return casa;
                    }else if(casa1.getConteudo().equals("vazio")){
                        return casa1;
                    }else if(casa2.getConteudo().equals("vazio")){
                        return casa2;
                    }
                }
                break;
            case 2:
                if (numero_circulos==2){
                    if (numero_xis==1) {
                        return null;
                    }else if(casa.getConteudo().equals("vazio")){
                        return casa;
                    }else if(casa1.getConteudo().equals("vazio")){
                        return casa1;
                    }else if(casa2.getConteudo().equals("vazio")){
                        return casa2;
                    }
                }
                break;
            case 3:
                if (numero_xis==1){
                    if (numero_circulos>0){
                        return null;
                    }else if(casa.getConteudo().equals("vazio")){
                        return casa;
                    }else if(casa1.getConteudo().equals("vazio")){
                        return casa1;
                    }

                }
                break;
        }

        return null;

    }




    private static int quantidadeDeVazios() {
        int contador=0;
        for (Casa[] linhas : casas) {
            for (Casa colunas : linhas) {
                if (colunas.getConteudo().equals("vazio")){
                    contador += 1;
                }
            }
        }

        return contador;
    }

    private static Casa elegeCasaRandomicamente() {
        int i=new Random().nextInt(3);
        int j=new Random().nextInt(3);

        return casas[i][j];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}