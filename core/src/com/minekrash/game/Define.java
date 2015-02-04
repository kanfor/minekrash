/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.minekrash.game;

/**
 *
 * @author Kanfor
 */
public class Define {
    public final static float ESCALA_METRO = 0.1f; //Escala del juego. 100 píxeles es un METRO
    public final static float SPEED_INICIAL = 50f; //Velocidad inicial del juego
    public final static int TIME_INCREMENT_SPEED = 1; //Cada cuántos SEGUNDOS aumenta la velocidad
    public final static float SPEED_INCREMENT = 4f; //Incremento de la velocidad que se produce cada X tiempo
    public final static float GRAVITY = 5f; //Gravedad del juego
    public final static float MAX_SPEED_VAGON = 40f; //Velocidad máxima del vagón
    public final static float MAX_ACELERATION = 100f; //Aceleración máxima al caer
    public final static float STRONG_ACELERATION_VAGON = 0.5f; //Fuerza de la aceleración al moverse izquierda y derecha
    public final static float TIME_TEXT_ALPHA = 100; //Tiempo en centésimas de segundo que dura la transición del canal alpha de los textos READY y GAME OVER
    public final static float SPEED_ROTATION_DEAD = 200; //Fuerza de la rotación al morir
    public final static float SPEED_HUMO = 10; //Velocidad del humo
    public final static float MAX_DELTATIME = 0.03f; //Valor máximo de delta time
    
    //Textos
    public static int Lenguaje = 0;
    public final static int ESPANOL = 0,
                            INGLES = 1;
    public final static String LISTO[] = {"¿Listo?","Ready?"};
    public final static String METROS[] = {"Metros: ","Meters: "};
    public final static String GAME_OVER = "GAME\nOVER";
    public final static String NUEVO_RECORD[] = {"¡NUEVO RECORD!","NEW RECORD!"};
    public final static String LOGRO_1[] = {"Has logrado bajar\n","You have reach\n"};
    public final static String LOGRO_2[] = {" metros"," meters"};
    public final static String RECORD[] = {"Tu record actual es:\n","Your actual record is:\n"};
}
