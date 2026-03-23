package delta.shapes;

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

    public Vector2[] getNormals(Shape other) {
        return new Vector2[] {
                other.closest(other.parent.pos).sub(this.parent.pos).nor()
        };
    }

    public Vector2 closest(Vector2 pos) {
        return this.parent.pos;
    }

    public float[] projection(Vector2 axis) {
        float projection = this.parent.pos.dot(axis);
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
    public boolean contains(Vector2 position) {
        float dx = position.x - this.parent.pos.x;
        float dy = position.y - this.parent.pos.y;

        return dx + dy == 0;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Vector2 offset) {
        shapeRenderer.circle(this.parent.pos.x, this.parent.pos.y, 1);
    }

    @Override
    public String toString() {
        return "point";
    }
}
