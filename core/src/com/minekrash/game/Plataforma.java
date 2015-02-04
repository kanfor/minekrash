/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.minekrash.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Random;

/**
 *
 * @author Kanfor
 */
public class Plataforma extends Main{
    private float x;
    private float y;
    private boolean isAlive;
    private float resetY, limitY;
    Random rnd;
    ShapeRenderer debug;
    
    Sprite plataforma;
    
    public Plataforma(Texture _imagen) {
        resetY = -(HEIGHT / 2) - _imagen.getHeight(); //Posición inicial de las plataformas al salir
        limitY = HEIGHT; //Máxima altura de la plataforma. Al cruzarla se inactiva
        isAlive = false;
        plataforma = new Sprite(_imagen);
        float scalaX2 = _imagen.getWidth() * ESCALA_METROS;
        float scalaY2 = _imagen.getHeight() * ESCALA_METROS;
        plataforma.setSize(scalaX2, scalaY2);
        plataforma.setOriginCenter();
        initPlataforma();
        plataforma.setBounds(x, y, scalaX2, scalaY2);
        makeRandomPosition(); 
        debug = new ShapeRenderer();
    }
    
    public void initPlataforma() {
        y = resetY;
        plataforma.setY(y);
        isAlive = false;
        rnd = new Random();
        makeRandomPosition(); //Asignamos su posición
    }
    
    public void setAlive(boolean _b) {
        isAlive = _b;
    }
    
    public void update(float _DT, float _speed) {
        if (isAlive) {
            y += _speed * _DT;
            if (y > limitY) {
                makeRandomPosition();
                isAlive = false;
            }
            plataforma.setPosition(x * ESCALA_METROS, y * ESCALA_METROS);
        }
    }
    
    public void draw(SpriteBatch g) {
        if (isAlive) {
            plataforma.draw(g);
            if (DEBUG) {
                debug.setProjectionMatrix(g.getProjectionMatrix());
                debug.begin(ShapeRenderer.ShapeType.Line);
                debug.setColor(Color.GREEN);
                debug.rect(plataforma.getX(), plataforma.getY(), plataforma.getWidth(), plataforma.getHeight());
                debug.end();
            }
        }
    }
    
    public boolean checkCollision(Sprite _sprite, float _speed) {
        if (isAlive) {
            if (plataforma.getBoundingRectangle().overlaps(_sprite.getBoundingRectangle())
                    && _sprite.getX() + _sprite.getWidth() - (_sprite.getWidth() / 4) > plataforma.getX()
                    && _sprite.getX() + (_sprite.getWidth() / 4) < plataforma.getX() + plataforma.getWidth()
                    
                    && _sprite.getY() > plataforma.getY() + (plataforma.getHeight() / 2)
                    
                    //&& _sprite.getY() - (_sprite.getHeight() /*/ 2*/) - _speed < plataforma.getY()
                    //&& _sprite.getY() > plataforma.getY()
                   ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    private void makeRandomPosition() {
        int start = -WIDTH / 2;
        int end = (int) ((WIDTH / 2) - (plataforma.getWidth() / ESCALA_METROS));
        x = getRandom(start, end);
        y = resetY;
    }
    
    private float getRandom(float _numeroMin, float _numeroMax) {
        int resultado = 0;
        resultado = (int) (Math.abs(rnd.nextInt() % (_numeroMax - _numeroMin + 1)) + _numeroMin);
        return resultado;
    }
    
    public void setXplat(float _x) {
        plataforma.setX(_x);
        x = _x / ESCALA_METROS;
    }
    
    public void setYplat(float _y) {
        plataforma.setY(_y);
        y = _y / ESCALA_METROS;
    }
    
    public float getYplat() {
        return y * ESCALA_METROS;
    }
    
    public float getXplat() {
        return x * ESCALA_METROS;
    }
    
    public float getHeight() {
        return plataforma.getHeight();
    }
}
