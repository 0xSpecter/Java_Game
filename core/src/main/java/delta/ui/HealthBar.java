package delta.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import delta.creatures.Creature;
import delta.structures.*;

/**
 * draws a rectangle on closest enemy
 */
public class HealthBar extends Element {
    private Creature target;

    public HealthBar(Creature creature) {
        super(0, 0);
        this.target = creature;
        this.height = 10f;
    }

    @Override
    public void update() {
        this.width = this.target.hp;
        this.pos = Ui.instance.worldToUi(this.target.pos).sub(this.width / 2, -10);
        super.update();
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Vector2 offset) {
        if (this.target.hp > 0) {
            super.draw(shapeRenderer, offset);
        }
    }
}
