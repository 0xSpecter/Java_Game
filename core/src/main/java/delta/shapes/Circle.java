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
    public Circle(float scale) {
        this.scale = scale;
    }

    /**
     * Renders the circle
     *
     * @param shapeRenderer the renderer used for drawing
     * @param parent        the parent object that provides the world position
     */
    public void draw(ShapeRenderer shapeRenderer, Obj parent) {
        shapeRenderer.circle(parent.pos.x, parent.pos.y, scale);
    }

    /**
     * Computes the collision normal between this circle and another shape.
     * <p>
     * The normal is calculated as the normalized vector from this circle's
     * center to the closest point on the other shape.
     *
     * @param other       the other shape involved in the collision
     * @param otherParent the parent of the other shape
     * @param parent      the parent of this circle
     * @return an array containing the single collision normal vector
     */
    public Vector2[] getNormals(Shape other, Obj otherParent, Obj parent) {
        return new Vector2[] {
                other.closest(otherParent, parent.pos).sub(parent.pos).nor()
        };
    }

    /**
     * Computes the closest point on the circle's perimeter to the given position.
     *
     * @param parent the parent object providing the circle's center
     * @param pos    the target position in world space
     * @return the closest point on the circle's boundary
     */
    public Vector2 closest(Obj parent, Vector2 pos) {
        Vector2 dir = new Vector2(pos).sub(parent.pos).nor();
        return new Vector2(parent.pos).add(dir.scl(this.scale));
    }

    /**
     * Projects the circle onto the given axis.
     * <p>
     * Used in Separating Axis Theorem (SAT) collision detection.
     * Since a circle is symmetric, the projection interval is simply the
     * center's projection ± the radius.
     *
     * @param axis   the normalized axis to project onto
     * @param parent the parent providing the circle's world position
     * @return a float array of size 2 where:
     *         <ul>
     *         <li>index 0 = minimum projection value</li>
     *         <li>index 1 = maximum projection value</li>
     *         </ul>
     */
    public float[] projection(Vector2 axis, Obj parent) {
        float centerProjection = parent.pos.dot(axis);
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
        return position.len2() <= this.scale * this.scale;
    }

    @Override
    public String toString() {
        return "circle";
    }
}
