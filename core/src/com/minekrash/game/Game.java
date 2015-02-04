/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.minekrash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Kanfor
 */
public class Game extends Main {
    boolean isInitGame;
    
    Texture fondo;
    float fondoX, fondoY;
    float deltaYfondo;
    float speedY = Define.SPEED_INICIAL; //Velocidad inicial en pixeles por segundo
    
    Texture brilloLava;

    float timeGame;
    int countTimeDiffult = Define.TIME_INCREMENT_SPEED; //Cada un segundo aumenta la dificultad
    float speedGameIncrement = Define.SPEED_INCREMENT;
    
    Texture platTexture;
    int nextPlataforma;
    int countTicks;
    float metros;
    
    float aceleration;
    float deltaAceleracion;
    final float gravedad = Define.GRAVITY;
    boolean colision;
    
    Texture barraMetros;
    Texture flechas;
    
    float alphaText;
    
    Sound fx_impacto;
    Sound fx_contador_metros;
    
    Music musicGameplay;
    
    public Game() {
        //Pared
        fondo = new Texture("gfx/fondo_mina.png");
        fondoX = -(WIDTH / 2);
        fondoY = fondo.getHeight();
        fondo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        
        //Brillo de lava
        brilloLava = new Texture("gfx/brillo_lava.png");
        brilloLava.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        brilloLava.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //Sprite vagonSprite
        vagon = new Vagon();
        
        //Sprite plataforma
        platTexture = new Texture("gfx/plataforma.png");
        platTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        plataforma = new Plataforma[20];
        for (int i = 0; i < plataforma.length; i++) {
            plataforma[i] = new Plataforma(platTexture);
        }
        plataforma[0].setAlive(true);
        
        //Marco de los metros
        barraMetros = new Texture("gfx/barra_metros.png");
        barraMetros.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        
        //Flechas
        flechas = new Texture("gfx/flechas.png");
        flechas.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        
        //Sonidos
        fx_impacto = Gdx.audio.newSound(Gdx.files.internal("sfx/impacto.ogg"));
        fx_contador_metros = Gdx.audio.newSound(Gdx.files.internal("sfx/contador.ogg"));
        
        //Música
        musicGameplay = Gdx.audio.newMusic(Gdx.files.internal("sfx/gameplay.ogg"));
        musicGameplay.setVolume(0.3f);
        musicGameplay.setLooping(true);
        
        alphaText = 0;
    }
    
    public void init() {
        timeGame = 0;
        aceleration = 0;
        deltaAceleracion = 0;
        alphaText = 0;
        nextPlataforma = 0;
        countTicks = 0;
        deltaYfondo = 0;
        metros = 0;
        speedY = Define.SPEED_INICIAL;
        controller.pressLeft = false;
        controller.pressRight = false;
        controller.keyPress = false;
        
        vagon.initVagon();

        for (int i=0; i < plataforma.length; i++) {
            plataforma[i].initPlataforma();
        }
        
        //Colocamos la primera plataforma justo debajo del vagón
        plataforma[0].setAlive(true);
        plataforma[0].setYplat(vagon.vagonY - plataforma[0].plataforma.getHeight());
        plataforma[0].setXplat(-(plataforma[0].plataforma.getWidth() / 2));
        nextPlataforma();
        
        //Reproducimos la canción
        musicGameplay.play();
        
        isInitGame = true;
    }
    
    public void render(SpriteBatch g) {
        if (isInitGame) {
            for (int i = 0; i < 14; i++) {
                paint.drawImage(g, fondo, fondoX, (fondoY * i) + deltaYfondo - (HEIGHT / 2) - fondoY);
            }

            vagon.draw(g);

            for (int i = 0; i < plataforma.length; i++) {
                plataforma[i].draw(g);
            }

            paint.drawImage(g, brilloLava, fondoX, -(HEIGHT / 2));

            //Textos
            switch (ESTADO_GAME) {
                case READY:
                    paint.drawText(g,
                    		fuenteGrande,
                    		Define.LISTO[Define.Lenguaje], 
                    		Color.WHITE,
                    		0,
                    		fuenteGrande.getCapHeight() * 2,
                    		BitmapFont.HAlignment.CENTER,
                    		alphaText);
                    break;
                case RUN:
                    //Flechas
                    paint.drawImageFlip(g,
                    		flechas,
                    		(WIDTH / 2) - flechas.getWidth(),
                    		-(HEIGHT / 2),
                    		false,
                    		controller.pressRight);
                    paint.drawImageFlip(g,
                    		flechas,
                    		-(WIDTH / 2) + flechas.getWidth(),
                    		-(HEIGHT / 2),
                    		true,
                    		controller.pressLeft);
                    //Marcador de metros
                    paint.drawImage(g,
                    		barraMetros,
                    		-(barraMetros.getWidth() / 2),
                    		(HEIGHT / 2) - barraMetros.getHeight());
                    paint.drawText(g,
                    		fuente,
                    		Define.METROS[Define.Lenguaje]+(int)metros,
                    		Color.WHITE, 0,
                    		((HEIGHT / 2) * ESCALA_METROS) - (fuente.getCapHeight()*1.5f),
                    		BitmapFont.HAlignment.CENTER,
                    		1f);
                    break;
                case GAME_OVER:
                    paint.drawText(g,
                    		fuenteGrande,
                    		Define.GAME_OVER,
                    		Color.WHITE,
                    		0,
                    		fuenteGrande.getCapHeight()*3,
                    		BitmapFont.HAlignment.CENTER,
                    		alphaText);
                    if (metros > maxMetros) {
                        paint.drawText(g,
                        		fuente,
                        		Define.NUEVO_RECORD[Define.Lenguaje],
                        		Color.RED,
                        		0,
                        		-fuente.getCapHeight()*3,
                        		BitmapFont.HAlignment.CENTER,
                        		alphaText);
                    }
                    paint.drawText(g,
                    		fuente,
                    		Define.LOGRO_1[Define.Lenguaje]+(int)metros+Define.LOGRO_2[Define.Lenguaje],
                    		Color.RED,
                    		0,
                    		-fuente.getCapHeight()*5,
                    		BitmapFont.HAlignment.CENTER,
                    		alphaText);
                    break;
            }
        }
    }
    
    public void update(float _DT) {
        switch (ESTADO_GAME) {
            case READY:
                if (!isInitGame) {
                    init();
                }
                if (alphaText < 1f) {
                    alphaText += 1 / Define.TIME_TEXT_ALPHA;
                }
                if (alphaText > 1f) {
                    alphaText = 1f;
                }
                if ((controller.keyPress || controller.touchPress) && alphaText > 0.3f) {
                    alphaText = 0;
                    ESTADO_GAME = RUN;
                }
                break;
            case RUN:
                //Fondo
                deltaYfondo += speedY * _DT;
                if (deltaYfondo > fondo.getHeight()) {
                    deltaYfondo = 0;
                    countTicks++;
                    metros += 0.5;
                    if (metros % 1 == 0) { //Reproducimos el sonido cuando "metros" sea un número entero
                    	fx_contador_metros.play();
                    }
                }
                
                //Vagon
                vagon.update(_DT);
                phisics(_DT);
                
                //Plataformas
                if (countTicks > 4) { //El tile del fondo ha avanzado cuatro veces su altura
                    nextPlataforma();
                    countTicks = 0;
                }
                
                colision = false; //Por defecto el vagón siempre cae
                for (int i = 0; i < plataforma.length; i++) {
                    plataforma[i].update(_DT, speedY);
                    if (plataforma[i].checkCollision(vagon.vagonSprite, speedY)) {
                        colision = true; //Si alguna plataforma colisiona con el vagón se parará¡
                        vagon.vagonY = plataforma[i].getYplat() + plataforma[i].getHeight();
                    }
                }
                
                //Dificultad. Tiempo del juego
                timeGame += _DT;
                if (timeGame > countTimeDiffult) {
                    speedY += speedGameIncrement;
                    timeGame = 0;
                }
                
                //Perdemos la partida
                if ((vagon.vagonY + vagon.vagonSprite.getHeight()) / ESCALA_METROS < -(HEIGHT / 2)
                        || vagon.vagonY / ESCALA_METROS > (HEIGHT / 2)
                        || vagon.vagonX / ESCALA_METROS > (WIDTH / 2)
                        || (vagon.vagonX + vagon.vagonSprite.getWidth()) / ESCALA_METROS < -(WIDTH / 2)
                        ) {
                    ESTADO_GAME = GAME_OVER;
                    controller.keyPress = false; //Evitamos que se salte el Game Over
                    controller.touchPress = false; //Evitamos que se salte el Game Over
                    if (!vagon.isDead) {
                        vagon.isDead = true;
                        deltaAceleracion = 0;
                        fx_impacto.play(1, 0.3f, 0);
                        //Gdx.input.vibrate(200); //Pequeña vibración
                        //Tipo de muerte//
                        //Muerte desde abajo
                        if ((vagon.vagonY + vagon.vagonSprite.getHeight()) / ESCALA_METROS < -(HEIGHT / 2)) {
                            aceleration = Define.MAX_ACELERATION; //Impulso hacia arriba
                            vagon.setAcelerationVagonX(0);
                        //Muerte desde arriba
                        } else if (vagon.vagonY / ESCALA_METROS > (HEIGHT / 2)) {
                            aceleration = 0;
                            vagon.setAcelerationVagonX(0);
                        //Muerte desde la derecha
                        } else if (vagon.vagonX / ESCALA_METROS > (WIDTH / 2)) {
                            aceleration = 0;
                            vagon.setAcelerationVagonX(-vagon.maxSpeedVagon);
                        //Muerte desde la izquierda
                        } else {
                            aceleration = 0;
                            vagon.setAcelerationVagonX(vagon.maxSpeedVagon);
                        }
                    }
                }
                break;
            case GAME_OVER:
                //Vagon
                vagon.update(_DT);
                phisics(_DT);
                
                if (alphaText < 1f) {
                    alphaText += 1 / Define.TIME_TEXT_ALPHA;
                }
                if (alphaText > 1f) {
                    alphaText = 1f;
                }
                if ((controller.keyPress || controller.touchPress) && alphaText > 0.3f) {
                	controller.keyPress = false; //Evitamos que se salte el Menú
                	controller.touchPress = false; //Evitamos que se salte el Menú
                    isInitGame = false;
                    musicGameplay.stop();
                    ESTADO_MAIN = MENU;
                    ESTADO_GAME = READY;
                    if (metros > maxMetros) {
                        maxMetros = (int)metros;
                        //Guardamos los datos
                        isSaving = true;
                    }
                }
                break;
        }
    }
    
    private void nextPlataforma() {
        nextPlataforma++;
        if (nextPlataforma > plataforma.length - 1) {
            nextPlataforma = 0;
        }
        plataforma[nextPlataforma].setAlive(true);
    }
    
    private void phisics(float _DT) {
        if (!vagon.isDead) {
            if (!colision) {
                if (aceleration < Define.MAX_ACELERATION) {
                    aceleration += deltaAceleracion * ESCALA_METROS;
                    deltaAceleracion += gravedad * ESCALA_METROS;
                }
                vagon.vagonY -= aceleration * _DT;
            } else {
                if (deltaAceleracion != 0 && metros != 0) {
                    vagon.putHumo();
                    fx_impacto.play(0.5f,1,0);
                }
                aceleration = -speedGameIncrement;
                deltaAceleracion = 0;
            }
        } else {
            aceleration += deltaAceleracion * ESCALA_METROS;
            deltaAceleracion -= gravedad * ESCALA_METROS;
            vagon.vagonY += aceleration * _DT;
            vagon.setAngle(Define.SPEED_ROTATION_DEAD);
        }
    }
}
