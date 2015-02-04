package com.minekrash.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Main extends ApplicationAdapter {
    
    static Paint paint;
    static Menu menu;
    static Controller controller;
    static Game game;
    static Plataforma plataforma[];
    static Vagon vagon;
    
    static float DT;
    static int ESTADO_MAIN = 0;
    static final int MENU = 0,
                     GAME = 1;
    static int ESTADO_GAME;
    static final int READY = 0,
              	     RUN = 1,
                     GAME_OVER = 2;
    static final float ESCALA_METROS = Define.ESCALA_METRO;
    static final boolean DEBUG = false;

    static BitmapFont fuente, fuenteGrande;
    static final int CENTER = 0,
                     LEFT = 1,
                     RIGHT = 2;
    public static int maxMetros;
    public static boolean isSaving;

    static final int HEIGHT = 800;
    static final int WIDTH = 480;
    
    @Override
    public void create() {
    	controller = new Controller();
        paint = new Paint();
        menu = new Menu();
        game = new Game();
        //Fuentes
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/fuente.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.size = 30;
        fuente = generator.generateFont(parameter); //Asignamos 30 al tamaño de fuente
        fuente.setScale(ESCALA_METROS);
        parameter.size = 60;
        fuenteGrande = generator.generateFont(parameter); //Asignamos 60 al tamaño de fuente
        fuenteGrande.setScale(ESCALA_METROS);
        generator.dispose();
    }

    @Override
    public void render() {
        DT = Gdx.graphics.getDeltaTime();
        if (DT > Define.MAX_DELTATIME) {
        	DT = 0;
        }
        //Logic
        switch (ESTADO_MAIN) {
            case MENU:
                menu.update(DT);
                break;
            case GAME:
                game.update(DT);
                break;
        }
        //Graphics
        paint.render();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
