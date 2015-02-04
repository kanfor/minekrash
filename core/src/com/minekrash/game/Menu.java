/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.minekrash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author Kanfor
 */
public class Menu extends Main {
    
    Texture cover;
    Texture logo;
    Texture marco;
    Sprite sLogo;
    Texture humoMenu;
    float logoX, logoY;
    float alphaMenu;
    float timeShake;
    boolean isInitMenu;
    float humoX;
    Music musicMenu;
    
    public Menu() {
        cover = new Texture("gfx/cover.png");
        cover.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        marco = new Texture("gfx/marco.png");
        marco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        humoMenu = new Texture("gfx/humo_menu.png");
        humoMenu.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        logo = new Texture("gfx/minekrash.png");
        logo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(logo, 0, 0, logo.getWidth(), logo.getHeight());
        sLogo = new Sprite(region);
        sLogo.setSize(sLogo.getWidth() * ESCALA_METROS, sLogo.getHeight() * ESCALA_METROS);
        sLogo.setOrigin(sLogo.getWidth() / 2, sLogo.getHeight() / 2);
        logoX = -(sLogo.getWidth() / 2);
        logoY = ((HEIGHT / 2) * ESCALA_METROS) - (sLogo.getHeight() * 1.5f);
        sLogo.setPosition(logoX, logoY);
        System.out.println("Altura: "+logoY);
        musicMenu = Gdx.audio.newMusic(Gdx.files.internal("sfx/menu.ogg"));
        musicMenu.setVolume(0.3f);
        musicMenu.setLooping(true);
        initMenu();
    }
    
    public void initMenu() {
        alphaMenu = 0f;
        isInitMenu = true;
        humoX = 0;
        //Reproducimos la canción del menú
        musicMenu.play();
    }
    
    public void render(SpriteBatch g) {
        if (isInitMenu) {
            paint.drawBackground(g, cover, alphaMenu);
            paint.drawImage(g, marco, -(marco.getWidth() / 2), -(marco.getHeight() / 2));
            sLogo.setAlpha(alphaMenu);
            sLogo.draw(g);
            paint.drawImage(g, humoMenu, -(WIDTH / 2) - humoX, -(HEIGHT / 2));
            paint.drawImage(g, humoMenu, (WIDTH / 2) - humoX, -(HEIGHT / 2));
            paint.drawText(g,
            		fuente, 																		//Tipo de fuente
            		Define.RECORD[Define.Lenguaje]+(int)maxMetros+Define.LOGRO_2[Define.Lenguaje],  //Texto a mostrar
            		Color.BLUE,																		//Color
            		0,																				//Posición X
            		fuenteGrande.getCapHeight() - (fuenteGrande.getCapHeight() / 4),				//Posición Y
            		BitmapFont.HAlignment.CENTER,													//Alineación. Se centrará en X
            		alphaMenu);																		//Transparencia
        }
    }
    
    public void update(float DT) {
        if (!isInitMenu) {
            initMenu();
        }
        if (alphaMenu < 1f) {
            alphaMenu += 1 / Define.TIME_TEXT_ALPHA;
        }
        if (alphaMenu > 1f) {
            alphaMenu = 1f;
        }
        if ((controller.keyPress || controller.touchPress) && alphaMenu > 0.3f) {
            isInitMenu = false;
            musicMenu.stop();
            ESTADO_MAIN = GAME;
            ESTADO_GAME = READY;
            controller.keyPress = false; //Evitamos que se salte el Ready
            controller.touchPress = false; //Evitamos que se salte el Ready
        }
        //Efecto shake del logo
        timeShake += DT;
        if (timeShake > 1) {
            sLogo.setRotation(paint.shakeEffect(DT));
            sLogo.setSize(sLogo.getWidth() + sLogo.getRotation(), sLogo.getHeight());
            if (timeShake > 1.3f) {
                timeShake = 0;
                sLogo.setRotation(0);
                sLogo.setSize(logo.getWidth() * ESCALA_METROS, logo.getHeight() * ESCALA_METROS);
            }
            sLogo.setPosition(-(sLogo.getWidth() / 2), sLogo.getY());
        }
        //Humo
        humoX += 100f * DT;
        if (humoX > WIDTH) {
            humoX = 0;
        }
    }
}
