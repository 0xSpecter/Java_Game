package delta.shapes;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.Vector2;

import delta.structures.*;

public class Circle extends Shape {
    public Circle(float scale) {
        this.scale = scale;
    }

    public void draw(ShapeRenderer shapeRenderer, Obj parent) {
        shapeRenderer.circle(parent.pos.x, parent.pos.y, scale);
    }

    public Vector2[] getNormals(Shape other, Obj otherParent, Obj parent) {
        return new Vector2[] {
                other.closest(otherParent, parent.pos).sub(parent.pos).nor()
        };
    }

    public Vector2 closest(Obj parent, Vector2 pos) {
        return new Vector2(pos).sub(parent.pos).nor().scl(this.scale).add(parent.pos);
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

    @Override
    public String toString() {
        return "circle";
    }
}
