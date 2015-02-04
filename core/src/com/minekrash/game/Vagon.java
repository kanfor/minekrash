/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.minekrash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 *
 * @author Kanfor
 */
public class Vagon extends Main{
    Sprite vagonSprite;
    Animation vagonAnim;
    float tempoAnim;
    TextureRegion vagonFrame; //Fotograma que sirve de buffer
    TextureRegion[] vagonAllFrames; //Todos los fotogramas
    Texture imagenHumo;
    Humo[] humo;
    int nextHumo;
    float vagonX, vagonY;
    float maxSpeedVagon = Define.MAX_SPEED_VAGON;
    float acelerationVagonX;
    float acelerationVagonY;
    final float strongAcelerationVagon = Define.STRONG_ACELERATION_VAGON;
    boolean isDead;
    ShapeRenderer debug;
    float angle;
    
    public Vagon() {
        Texture textureTemp = new Texture("gfx/vagon.png");
        textureTemp.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion[][] textureRegionTemp = TextureRegion.split(textureTemp, textureTemp.getWidth() / 2, textureTemp.getHeight());
        vagonAllFrames = new TextureRegion[2];
        vagonAllFrames[0] = textureRegionTemp[0][0];
        vagonAllFrames[1] = textureRegionTemp[0][1];
        vagonAnim = new Animation(0f, vagonAllFrames); //Velocidad e imagen
        vagonSprite = new Sprite(vagonAllFrames[0]);
        float scalaX = vagonAllFrames[0].getRegionWidth() * ESCALA_METROS;
        float scalaY = vagonAllFrames[0].getRegionHeight() * ESCALA_METROS;
        vagonSprite.setSize(scalaX, scalaY);
        vagonSprite.setOriginCenter();
        initVagon();
        vagonSprite.setBounds(vagonX, vagonY, scalaX, scalaY);

        debug = new ShapeRenderer();
        
        humo = new Humo[10];
        imagenHumo = new Texture("gfx/humo.png");
        imagenHumo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        for (int i=0; i<humo.length; i++) {
            humo[i] = new Humo(imagenHumo);
        }
        nextHumo = 0;
    }
    
    public void initVagon() {
        angle = 0;
        vagonSprite.setRotation(angle);
        isDead = false;
        vagonX = -(vagonSprite.getWidth() / 2);
        vagonY = -vagonSprite.getHeight();
        vagonSprite.setX(vagonX);
        vagonSprite.setY(vagonY);
        vagonSprite.rotate(angle);
        acelerationVagonX = 0;
        vagonAnim.setFrameDuration(0.01f);
    }
    
    public void draw(SpriteBatch g) {
        vagonSprite.draw(g);
        //Humo
        for (int i=0; i<humo.length; i++) {
            humo[i].draw(g);
        }
        if (DEBUG) {
            debug.setProjectionMatrix(g.getProjectionMatrix());
            debug.begin(ShapeRenderer.ShapeType.Line);
            debug.setColor(Color.GREEN);
            debug.rect(vagonSprite.getX(), vagonSprite.getY(), vagonSprite.getWidth(), vagonSprite.getHeight());
            debug.end();
        }
    }
    
    public void update(float _DT) {
        tempoAnim += _DT;
        vagonAnim.setFrameDuration(frameDuration(acelerationVagonX));
        vagonFrame = vagonAnim.getKeyFrame(tempoAnim, true);
        vagonSprite.setRegion(vagonFrame);
        vagonSprite.setPosition(0, 0);
        vagonSprite.rotate(angle * _DT);

        if (!isDead) {
            if (controller.pressLeft) {
                if (acelerationVagonX > -maxSpeedVagon) {
                    acelerationVagonX -= strongAcelerationVagon;
                }
            }
            if (controller.pressRight) {
                if (acelerationVagonX < maxSpeedVagon) {
                    acelerationVagonX += strongAcelerationVagon;
                }
            }
            if (!controller.pressLeft && !controller.pressRight) {
                if (acelerationVagonX > strongAcelerationVagon) {
                    acelerationVagonX -= strongAcelerationVagon / 4;
                } else if (acelerationVagonX < -strongAcelerationVagon) {
                    acelerationVagonX += strongAcelerationVagon / 4;
                } else {
                    acelerationVagonX = 0;
                }
            }
            //Táctil
            if (controller.touchX < Gdx.graphics.getWidth() / 3
            		&& controller.touchY > Gdx.graphics.getHeight() / 2) {
                controller.pressLeft = true;
            }
            if (controller.touchX > Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 3)
            		&& controller.touchY > Gdx.graphics.getHeight() / 2) {
            	controller.pressRight = true;
            }
            if (!controller.touchPress && !controller.keyPress) {
            	controller.pressLeft = false;
            	controller.pressRight = false;
            }
        }

        vagonX += acelerationVagonX * _DT;
        vagonSprite.setPosition(vagonX, vagonY);
        
        //Humo
        for (int i=0; i<humo.length; i++) {
            humo[i].update(_DT);
        }
    }
    
    public void setAcelerationVagonX(float _strong) {
        acelerationVagonX = _strong;
    }
    
    public void setAngle(float _angle) {
        angle = _angle;
    }
    
    public float frameDuration(float _speed) {
        float duration = 0;
        float speed = Math.abs(_speed);
        if (_speed != 0) {
            if (speed < 5) {
                duration = 0.1f;
            } else if (speed >= 5 && speed < 10) {
                duration = 0.05f;
            } else if (speed >= 10 && speed < 20) {
                duration = 0.01f;
            } else if (speed >= 20 && speed < 30) {
                duration = 0.008f;
            } else if (speed >= 30) {
                duration = 0.005f;
            }
        }
        return duration;
    }
    
    public void putHumo() {
        //Humo 1
        if (nextHumo < humo.length - 1) {
            nextHumo++;
        } else {
            nextHumo = 0;
        }
        humo[nextHumo].setAlive(true);
        humo[nextHumo].setFacingRight(true);
        //Humo2
        if (nextHumo < humo.length - 1) {
            nextHumo++;
        } else {
            nextHumo = 0;
        }
        humo[nextHumo].setAlive(true);
        humo[nextHumo].setFacingRight(false);
    }
    
    public class Humo {
        float x;
        float y;
        boolean isAlive;
        boolean isFacingRight;
        Texture imagenHumo;
        float alpha;
        float size;
        public Humo(Texture _texture) {
            imagenHumo = _texture;
            isAlive = false;
            x = 0;
            y = 0;
            alpha = 1f;
        }
        
        public void update(float _DT) {
            if (isAlive) {
                if (isFacingRight) {
                    x += Define.SPEED_HUMO * _DT;
                } else {
                    x -= Define.SPEED_HUMO * _DT;
                }
                alpha -= 2f * _DT;
                size += 5f * _DT;
                if (alpha <= 0) {
                    isAlive = false;
                }
                y = vagonSprite.getY();
            }
        }
        
        public void draw(SpriteBatch g) {
            if (isAlive) {
                paint.drawImageAlpha(g, imagenHumo, x, y, size, alpha);
            }
        }
        
        public void setAlive(boolean _b) {
            isAlive = true;
            y = vagonSprite.getY();
            x = vagonSprite.getX() + (vagonSprite.getWidth() / 2);
            alpha = 1f;
            size = 0f;
        }
        
        public void setFacingRight(boolean _b) {
            isFacingRight = _b;
        }
    }
}
