package com.example.alberth.animacion;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by alberth on 9/27/18.
 */

public class RectanguloGrafico {
    private float vertices[] = new float [] {
           112,50, //01
            142,50, //02
            142,80, //03
            112,80 //04
    };

    FloatBuffer bufVertices;
    public RectanguloGrafico() {
/* Lee los vértices */
        ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
        bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden del byte nativo
        bufVertices = bufByte.asFloatBuffer(); // Convierte de byte a float
        bufVertices.put(vertices);
        bufVertices.rewind(); // puntero al principio del buffer
    }
    public void dibuja(GL10 gl) {
/* Activa el arreglo de vértices */
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
/* Apunta a los datos del arreglo de vértices */
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);
/* Se establece el color en (r,g,b,a) */
     // sacando el color
/* Renderiza las primitivas desde los datos del arreglo de vértices */
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
/* Desactiva el arreglo de vértices */
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}


