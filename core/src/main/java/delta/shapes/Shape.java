package delta.shapes;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;

import delta.structures.*;

/**
 * Base class for all 2D shapes used for rendering and collision detection.
 * <p>
 * Shapes are positioned using their parent {@link Obj}. Implementations
 */
public abstract class Shape {
    /**
     * Uniform scale value
     */
    public float scale;

    /**
     * Renders the shape.
     *
     * @param shapeRenderer renderer used for drawing
     * @param parent        object providing world position/transform
     */
    public abstract void draw(ShapeRenderer shapeRenderer, Obj parent);

    /**
     * Checks whether a local-space position lies inside the shape.
     *
     * @param position position in local space
     * @return true if inside (or on boundary)
     */
    public abstract boolean contains(Vector2 position);

    /**
     * Returns collision normals between this shape and another.
     *
     * @param other       the other shape
     * @param otherParent parent of the other shape
     * @param parent      parent of this shape
     * @return array of candidate separating axes
     */
    public abstract Vector2[] getNormals(Shape other, Obj otherParent, Obj parent);

    /**
     * Returns the closest point on this shape to a given world-space position.
     *
     * @param parent parent providing world position
     * @param pos    target world-space position
     * @return closest point on the shape
     */
    public abstract Vector2 closest(Obj parent, Vector2 pos);

    /**
     * Projects the shape onto the given axis
     *
     * @param axis   normalized axis
     * @param parent parent providing world position
     * @return float[2] where index 0 = min, index 1 = max
     */
    public abstract float[] projection(Vector2 axis, Obj parent);

    /**
     * Applies a rotation transform to the shape.
     *
     * @param rotationMatrix rotation matrix
     */
    public abstract void rotate(Matrix3 rotationMatrix);

    /**
     * Rotates the shape by radians.
     *
     * @param radians rotation angle in radians
     */
    public void rotateRad(float radians) {
        this.rotate(new Matrix3().setToRotationRad(radians));
    }

    /**
     * Rotates the shape by degrees.
     *
     * @param degrees rotation angle in degrees
     */
    public void rotateDeg(float degrees) {
        this.rotate(new Matrix3().setToRotation(degrees));
    }
}
