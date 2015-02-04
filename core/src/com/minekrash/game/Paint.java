/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.minekrash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 *
 * @author Kanfor
 */
public class Paint extends Main {
    
    SpriteBatch batch;
    OrthographicCamera camera;
    
    public Paint() {
        batch = new SpriteBatch();
        float w = WIDTH * ESCALA_METROS; //Precalculamos esto para que los cálculos posteriores se hagan correctamente
        float h = HEIGHT * ESCALA_METROS;
        camera = new OrthographicCamera(w, h);
        camera.position.set(0, 0, 0);
        batch.setProjectionMatrix(camera.combined);
    }
    
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        switch (ESTADO_MAIN) {
            case MENU:
                menu.render(batch);
                break;
            case GAME:
                game.render(batch);
                break;
        }
        batch.end();
    }
    
    public void drawImage(SpriteBatch g, Texture _image, float _x, float _y) {
    	g.draw(_image,
    			_x * ESCALA_METROS,
    			_y * ESCALA_METROS,
    			_image.getWidth() * ESCALA_METROS,
    			_image.getHeight() * ESCALA_METROS);
    }
    
    public void drawImageAlpha(SpriteBatch g, Texture _image, float _x, float _y, float _size, float _alpha) {
        g.setColor(g.getColor().r, g.getColor().g, g.getColor().b, _alpha);
        g.draw(_image,
        		_x - ((_image.getWidth() / 2) * ESCALA_METROS),
        		_y,
        		(_image.getWidth() * ESCALA_METROS) + _size,
        		(_image.getHeight() * ESCALA_METROS) + _size);
        g.setColor(g.getColor().r, g.getColor().g, g.getColor().b, 1f);
    }
    
    public void drawImageFlip(SpriteBatch g, Texture _image, float _x, float _y, boolean _flipHorizontal, boolean _pressing) {
        if (!_pressing) {
            g.setColor(g.getColor().r, g.getColor().g, g.getColor().b, 0.3f);
        } else {
            g.setColor(g.getColor().r, g.getColor().g, g.getColor().b, 1f);
        }
        if (_flipHorizontal) {
            g.draw(_image,
            		_x * ESCALA_METROS,
            		_y * ESCALA_METROS,
            		-_image.getWidth() * ESCALA_METROS,
            		_image.getHeight() * ESCALA_METROS);
        } else {
            g.draw(_image,
            		_x * ESCALA_METROS,
            		_y * ESCALA_METROS,
            		_image.getWidth() * ESCALA_METROS,
            		_image.getHeight() * ESCALA_METROS);
        }
        g.setColor(g.getColor().r, g.getColor().g, g.getColor().b, 1f);
    }
    
    public void drawBackground(SpriteBatch g, Texture _image, float _alpha) {
        g.setColor(g.getColor().r, g.getColor().g, g.getColor().b, _alpha);
        g.draw(_image,
        		-(_image.getWidth() / 2) * ESCALA_METROS,
        		-(_image.getHeight() / 2) * ESCALA_METROS,
        		_image.getWidth() * ESCALA_METROS,
        		_image.getHeight() * ESCALA_METROS);
    }
    
    public void drawText(SpriteBatch g, BitmapFont _font, String _texto, Color _color,
    		float _x, float _y,BitmapFont.HAlignment _alineacion, float _alphaText) {
        _font.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, _alphaText);
        _font.drawMultiLine(g, _texto, _x, _y - 0.2f, 0, _alineacion);
        _font.setColor(_color.r, _color.g, _color.b, _alphaText);
        _font.drawMultiLine(g, _texto, _x, _y, 0, _alineacion);
    }
    
    private static float rot;
    public float shakeEffect(float _dt) {
        rot = (rot + Gdx.graphics.getDeltaTime() * 50f) % 360; //Velocidad
        float shake = MathUtils.sin(rot) * 2f; //Fuerza
        return shake;
    }
}
