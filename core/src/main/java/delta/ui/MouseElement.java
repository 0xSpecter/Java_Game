package delta.ui;

import delta.structures.*;

/**
 * tracks the mouse
 */
public class MouseElement extends Element {
    public MouseElement() {
        super(Input.mouseUiPosition.x, Input.mouseUiPosition.y, 10, 10);
    }

    @Override
    public void update() {
        super.update();
        this.pos.set(Input.mouseUiPosition).sub(this.width / 2, this.height / 2);
    }
}
