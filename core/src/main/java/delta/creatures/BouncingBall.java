package delta.creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import delta.structures.*;
import delta.shapes.*;

public class BouncingBall extends Creature {
    public BouncingBall(float x, float y) {
        super(x, y);
        this.acceleration = 1000f;
        this.moveDirection = new Vector2((float) Math.random() - 0.5f, (float) Math.random() - 0.5f);
        this.moveDirection.nor();
    }

    public void update() {
        this.accelerate();
        this.move();
    }
}
