package delta.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import delta.structures.*;

/**
 * draws a rectangle on closest enemy
 */
public class PlayerTarget extends Element {
    public PlayerTarget() {
        super(0, 0, 40, 40);
    }

    @Override
    public void update() {
        if (World.player.hasTarget()) {
            this.pos = Ui.instance.worldToUi(World.player.target.pos);
            this.pos.sub(this.width / 2, this.height / 2);
        }
        super.update();
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Vector2 offset) {
        if (World.player.hasTarget()) {
            super.draw(shapeRenderer, offset);
        }
    }
}
