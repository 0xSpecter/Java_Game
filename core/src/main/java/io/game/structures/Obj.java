package io.game.structures;

import java.lang.ref.SoftReference;
import java.time.chrono.ThaiBuddhistChronology;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.glutils.*;

public class Obj {
    public Vector2 pos = new Vector2(0, 0);
    protected CollisionShape collisionShape = null;

    public Obj(float x, float y) {
        this.pos.x = x;
        this.pos.y = y;
    }

    public void drawCollisionShape(ShapeRenderer shapeRenderer) {
        if (this.collisionShape != null) {
            this.collisionShape.draw(shapeRenderer);
        }
    }

    public void addCollisionShape(CollisionShape shape) {
        this.collisionShape = shape;
    }
}
