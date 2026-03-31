package delta.creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import delta.structures.*;
import delta.shapes.*;

public class Enemy extends Creature {
    public Enemy(float x, float y) {
        super(x, y);
        this.speed = 1000;
        this.acceleration = 1000;
    }

    public void update() {
        this.lookDirection = this.getTargetDirection();

        this.moveDirection = this.lookDirection.cpy();
        if (this.moveDirection.isZero())
            this.deAccelerate();
        else {
            this.accelerate();
        }
        this.move();
    }

    public void ai(World world) {
        this.target = world.getPlayer();
    }
}
