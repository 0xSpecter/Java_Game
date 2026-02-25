package delta.structures;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.glutils.*;

import delta.shapes.*;

/**
 * Base class of anything thats going to be rendered
 *
 * <p>
 * only contains position and a collision shape
 * </p>
 */
public class Obj {
    public Vector2 pos = new Vector2(0, 0);
    public CollisionShape collisionShape = null;

    public Obj() {
    }

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
