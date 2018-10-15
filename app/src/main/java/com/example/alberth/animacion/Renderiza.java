package com.example.alberth.animacion;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.view.MotionEvent;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by alberth on 9/27/18.
 */
public class Renderiza extends GLSurfaceView implements GLSurfaceView.Renderer {
    /* Objeto */
    private Carretera carretera;
    private Lineas lineas;
    private RectanguloGrafico rectanguloGrafico;
    private Rectangulo rectanguloCoche1; //coliciones
    private Rectangulo rectanguloCoche2; //coliciones


    private float desplLineasY;
    private float velocidadCoche1;


    private float desplCoche1x;

    private float desplCoche2x;
    private float desplCoche2y;


    Random rand=new Random();

    public Renderiza(Context contexto) {
        super(contexto);
        this.setRenderer(this);
        this.requestFocus();
        this.setFocusableInTouchMode(true);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {

        carretera = new Carretera();
        lineas = new Lineas();
        rectanguloGrafico=new RectanguloGrafico();

        rectanguloCoche1=new Rectangulo(112,50,30,30);


		/* Color de fondo */
        gl.glClearColor(0, 1, 1, 0);


        desplLineasY = 0; //
        desplCoche1x=0;

        velocidadCoche1 = -5; // velocidad para la carretera movimiento de la carretera

        if(rand.nextInt(2)==0) {  // genra 0 o 1
            desplCoche2x = 0;
        }else{
            desplCoche2x=64;
        }
        desplCoche2y=840;
        rectanguloCoche2=new Rectangulo(112+ desplCoche2x,50+desplCoche2y,30,30);



    }
    public void dibujaCarretera(GL10 gl){
        gl.glLoadIdentity();
        carretera.dibuja(gl);

    }

    public void dibujaLineas(GL10 gl){
        gl.glLoadIdentity();
        gl.glTranslatef(0, desplLineasY, 0);
        lineas.dibuja(gl);

    }

    public void dibujaCoche1(GL10 gl){
        gl.glLoadIdentity();
        gl.glColor4f(1, 0, 0, 0);
        rectanguloCoche1.x=112+desplCoche1x;
        gl.glTranslatef(desplCoche1x,0,0);
        rectanguloGrafico.dibuja(gl);

    }

    public void dibujaCoche2(GL10 gl){
        gl.glLoadIdentity();
        gl.glColor4f(1, 1, 0, 0);

        rectanguloCoche2.x=112+desplCoche2x;

        rectanguloCoche2.y=50+desplCoche2y;

        gl.glTranslatef(desplCoche2x,desplCoche2y,0);
        rectanguloGrafico.dibuja(gl);

    }
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        dibujaCarretera(gl);
        dibujaLineas(gl);
        dibujaCoche1(gl);
        dibujaCoche2(gl);

        if(!seSobreponen(rectanguloCoche1,rectanguloCoche2)){


            desplLineasY = desplLineasY + velocidadCoche1;
            desplCoche2y=desplCoche2y-8;

            if(desplCoche2y<-60) {
                desplCoche2y = 840;
                if(rand.nextInt(2)==0)  // genra 0 o 1
                    desplCoche2x = 0;
                else
                    desplCoche2x=64;
            }

            if (desplLineasY < -60)
                desplLineasY = 0;
        }


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

		/* Ventana de despliegue */
        gl.glViewport(0, 0, width, height);

		/* Matriz de Proyección */
        gl.glMatrixMode(GL10.GL_PROJECTION);

		/* Inicializa la Matriz de Proyección */
        gl.glLoadIdentity();

		/* Proyección paralela */
        GLU.gluOrtho2D(gl, 0, 320, 0, 480);

		/* Matriz del Modelo-Vista */
        gl.glMatrixMode(GL10.GL_MODELVIEW);

		/* Inicializa la Matriz del Modelo-Vista */
        gl.glLoadIdentity();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        if(e.getAction()==MotionEvent.ACTION_UP){
            if(desplCoche1x==0)
                desplCoche1x=64;
            else
                desplCoche1x=0;
        }
        return true;
    }

    public boolean seSobreponen(Rectangulo r1, Rectangulo r2){
        return (r1.x < r2.x + r2.ancho && r2.x < r1.x + r1.ancho &&
                r1.y < r2.y + r2.alto && r2.y < r1.y + r1.alto);
    }
}