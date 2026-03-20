package delta.ui;

import com.badlogic.gdx.math.*;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.glutils.*;
import delta.shapes.*;
import delta.structures.*;

/**
 * Base class of any ui element, extends obj for rendering
 */
public class Element extends Obj {
    private List<Element> children;
    public CollisionShape collisionShape;
    public boolean selected = false;
    public boolean hovered = false;
    public boolean row = true;
    public float gap = 0;

    public Element() {
        super();
        this.children = new ArrayList<>();
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

    public void update() {
        this.hovered = this.collisionShape.contains(Input.mouseUiPosition);
    }
}
