package delta.shapes;

import java.util.Vector;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;

import delta.structures.*;

/**
 * Represents a 2D circle shape centered on its parent {@link Obj}'s position.
 */
public class Circle extends Shape {
    /**
     * Creates a new circle with the given radius.
     *
     * @param scale the radius of the circle
     */
    public Circle(float scale, Obj parent) {
        this.scale = scale;
        this.parent = parent;
    }

    /**
     * creates a new circle from the given circle with a new parent
     */
    public Circle(Circle circle, Obj parent) {
        this(circle.scale, parent);
    }

    /**
     * creates a new circle from the given circle with the same parent
     */
    public Circle(Circle circle) {
        this(circle.scale, circle.parent);
    }

    /**
     * Renders the circle
     *
     * @param shapeRenderer the renderer used for drawing
     * @param offset        offset
     */
    public void draw(ShapeRenderer shapeRenderer, Vector2 offset) {
        shapeRenderer.circle(this.parent.pos.x + offset.x, this.parent.pos.y + offset.y, scale);
    }

    /**
     * Computes the collision normal between this circle and another shape.
     * <p>
     * The normal is calculated as the normalized vector from this circle's
     * center to the closest point on the other shape.
     *
     * @param other       the other shape involved in the collision
     * @param otherParent the parent of the other shape
     * @return an array containing the single collision normal vector
     */
    public Vector2[] getNormals(Shape other) {
        return new Vector2[] {
                other.closest(other.parent.pos).sub(this.parent.pos).nor()
        };
    }

    /**
     * Computes the closest point on the circle's perimeter to the given position.
     *
     * @param pos the target position in world space
     * @return the closest point on the circle's boundary
     */
    public Vector2 closest(Vector2 pos) {
        Vector2 dir = new Vector2(this.parent.pos).sub(pos).nor();
        return new Vector2(pos).add(dir.scl(this.scale));
    }

    /**
     * Projects the circle onto the given axis.
     * <p>
     * Used in Separating Axis Theorem (SAT) collision detection.
     * Since a circle is symmetric, the projection interval is simply the
     * center's projection ± the radius.
     *
     * @param axis the normalized axis to project onto
     * @return a float array of size 2 where:
     *         <ul>
     *         <li>index 0 = minimum projection value</li>
     *         <li>index 1 = maximum projection value</li>
     *         </ul>
     */
    public float[] projection(Vector2 axis) {
        float centerProjection = this.parent.pos.dot(axis);
        return new float[] {
                centerProjection - this.scale,
                centerProjection + this.scale,
        };
    }

    /**
     * <p>
     * This method has no effect because a circle is rotationally symmetric.
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
     * @return true if inside (or on boundary)
     */
    public boolean contains(Vector2 position) {
        return new Vector2(position).sub(this.parent.pos).len2() <= this.scale * this.scale;
    }

    @Override
    public String toString() {
        return "circle";
    }
}
