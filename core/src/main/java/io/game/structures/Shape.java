package io.game.structures;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;

public abstract class Shape {
    public float scale;
    public Vector2[] normals;

    public abstract void draw(ShapeRenderer shapeRenderer, Obj parent);

    public abstract boolean contains(float x, float y);

    public abstract void updateNormals();

    public abstract float[] projection(Vector2 axis, Obj parent);
}
