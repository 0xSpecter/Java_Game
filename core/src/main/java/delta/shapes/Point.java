package delta.shapes;

import java.util.Vector;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;

import delta.structures.*;

/**
 * Represents a point on {@link Obj}'s position.
 * is essensaly a circle with scale of 1
 * usefull as its computasjonoly cheaper
 */
public class Point extends Shape {
    /**
     * Creates a new point
     */
    public Point() {
        this.scale = 1;
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
        return parent.pos;
    }

    public float[] projection(Vector2 axis, Obj parent) {
        float projection = parent.pos.dot(axis);
        return new float[] {
                projection,
                projection
        };
    }

    /**
     * <p>
     * This method has no effect because a point is cannot be rotated.
     * It exists for compatibility with other {@link Shape}
     * implementations that require rotation.
     *
     * @param rotationMatrix the rotation matrix to apply (ignored)
     */
    public void rotate(Matrix3 rotationMatrix) {
    }

    /**
     * Determines whether a given position lies inside this circle.
     * <p>
     * Uses squared distance comparison to avoid the computational cost of
     * a square root operation.
     *
     * @param position position
     * @param origin   origin for comparison
     * @return true if inside (or on boundary)
     */
    public boolean contains(Vector2 position, Vector2 origin) {
        float dx = position.x - origin.x;
        float dy = position.x - origin.x;

        return dx + dy == 0;
    }

    @Override
    public String toString() {
        return "point";
    }
}
