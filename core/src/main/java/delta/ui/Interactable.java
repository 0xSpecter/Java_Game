package delta.ui;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.glutils.*;
import delta.shapes.*;
import delta.structures.*;

/**
 * Base class of any ui element, extends obj for rendering
 */
public class Interactable extends Element {
    public boolean selected = false;
    public boolean hovered = false;

    public Interactable() {
        super();
    }

    public void update() {
        this.hovered = this.collisionShape.contains(Input.mouseUiPosition);
    }
}
