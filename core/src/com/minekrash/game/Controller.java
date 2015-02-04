/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.minekrash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 *
 * @author Kanfor
 */
public class Controller extends InputAdapter {
    
    public boolean pressRight, pressLeft, keyPress, touchPress;
    public float touchX, touchY;
    
    public Controller() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        keyPress = true;
        switch(keycode) {
            case Input.Keys.RIGHT:
                pressRight = true;
                break;
            case Input.Keys.LEFT:
                pressLeft = true;
                break;
        }
        return false;
    }
    
    @Override
    public boolean keyUp(int keycode) {
        keyPress = false;
        switch(keycode) {
            case Input.Keys.RIGHT:
                pressRight = false;
                break;
            case Input.Keys.LEFT:
                pressLeft = false;
                break;  
        }
        return false;
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    	touchPress = true;
    	return false;
    };
    
    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        touchX = Gdx.input.getX();
        touchY = Gdx.input.getY();
        return false;
    }
    
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPress = false;
        touchX = 0;
        touchY = 0;
        return false;
    }
}
