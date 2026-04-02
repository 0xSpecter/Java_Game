package delta.creatures;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import delta.structures.*;
import delta.shapes.*;

public class Creature extends Obj {
    public float hp = 100;
    protected int maxSlots = 2;
    protected Obj[] slots = new Obj[this.maxSlots];
    public Creature target = null;

    public float maxSpeed = 800;
    public float acceleration = 3000;
    public float deceleration = 0.8f;

    public Vector2 velocity = new Vector2();
    public Vector2 lookDir = new Vector2();
    public Vector2 moveDir = new Vector2();

    public Creature(float x, float y) {
        super(x, y);
    }

    public void move() {
        float delta = Gdx.graphics.getDeltaTime();

        if (!this.moveDir.isZero()) {
            this.velocity.add(this.moveDir.cpy().scl(this.acceleration).scl(delta));
            this.velocity.limit(this.maxSpeed);
        } else {
            float factor = (float) Math.pow(this.deceleration, delta * 60);
            this.velocity.scl(factor);
            if (this.velocity.len2() < 0.001f) {
                this.velocity.setZero();
            }
        }

        this.pos.add(this.velocity.cpy().scl(delta));
    }

    public void update() {

    }

    public Vector2 getTargetDirection() {
        return new Vector2(this.target.pos).sub(this.pos).nor();
    }

    public boolean hasTarget() {
        return this.target != null;
    }
}
