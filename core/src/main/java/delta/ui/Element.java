package delta.ui;

import com.badlogic.gdx.math.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import delta.shapes.*;
import delta.structures.*;

/**
 * Base class of any ui element, extends obj for rendering
 */
public class Element extends Obj {
    protected float originX;
    protected float originY;

    // :TODO: make private
    public ArrayList<Element> children = new ArrayList<>();
    public Float width;
    public Float height;

    public CollisionShape collisionShape;
    public boolean selected = false;
    public boolean active = true;
    public boolean hovered = false;
    public boolean relative = true;

    public Element(float x, float y) {
        this(x, y, 0, 0);
    }

    public Element(float x, float y, float width, float height) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.originX = x;
        this.originY = y;
        this.update();
    }

    public void addChild(Element child) {
        this.children.add(child);
    }

    public void removeChild(Element child) {
        this.children.remove(child);
    }

    public void removeChild(int index) {
        this.children.remove(index);
    }

    public void draw(ShapeRenderer shapeRenderer, Vector2 offset) {
        Vector2 pos = new Vector2(this.pos);
        if (this.relative)
            pos.add(offset);
        shapeRenderer.rect(pos.x, pos.y, this.width, this.height);
        for (Element child : this.children) {
            child.draw(shapeRenderer, pos);
        }
    }

    public void update() {
        if (!this.active)
            return;

        if (this.collisionShape != null) {
            this.hovered = this.collisionShape.contains(Input.mouseUiPosition);
        }
        for (Element child : this.children) {
            child.update();
        }
    }

    public float childrenWidth() {
        return (float) this.children.stream().mapToDouble(c -> c.width).sum();
    }

    public float childrenHeight() {
        return (float) this.children.stream().mapToDouble(c -> c.height).sum();
    }
}
