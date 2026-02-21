package delta.shapes;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;

import delta.structures.*;

public abstract class Shape {
    public float scale;

    public abstract void draw(ShapeRenderer shapeRenderer, Obj parent);

    public abstract boolean contains(float x, float y);

    public abstract Vector2[] getNormals(Shape other, Obj otherParent, Obj parent);

    public abstract Vector2 closest(Obj parent, Vector2 pos);

    public abstract float[] projection(Vector2 axis, Obj parent);
}
