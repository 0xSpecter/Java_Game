package io.game.structures;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Circle extends Shape {
    public Circle(float scale) {
        this.scale = scale;
    }

    public void draw(ShapeRenderer shapeRenderer, Obj parent) {
        shapeRenderer.circle(parent.pos.x, parent.pos.y, scale);
    }

    public Vector2[] getNormals(Shape other, Obj otherParent, Obj parent) {
        return new Vector2[] {
                other.closest(otherParent, parent).sub(parent.pos).nor()
        };
    }

    // could be improved
    public Vector2 closest(Obj parent, Obj otherParent) {
        return new Vector2(parent.pos);
    }

    public float[] projection(Vector2 axis, Obj parent) {
        float centerProjection = parent.pos.dot(axis);
        return new float[] {
                centerProjection - this.scale,
                centerProjection + this.scale
        };
    }

    public boolean contains(float x, float y) {
        return false;
    }
}
