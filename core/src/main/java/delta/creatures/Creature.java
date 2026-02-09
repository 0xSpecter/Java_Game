package delta.creatures;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import delta.structures.*;
import delta.shapes.*;

public class Creature extends Obj {
    public float speed = 10000;
    public float hp = 100;
    public Vector2 velocity = new Vector2(0, 0);
    public Vector2 lookDirection = new Vector2(0, 0);
    public Vector2 moveDirection = new Vector2(0, 0);
    public float acceleration = 0;
    public float deAcceleration = 0;
    protected int max_slots = 2;
    protected Obj[] slots = new Obj[this.max_slots];
    protected Obj target = null;

    public Creature(float x, float y) {
        super(x, y);
    }

    public void accelerate() {
        float delta = Gdx.graphics.getDeltaTime();

        this.velocity.add(this.moveDirection.cpy().scl(acceleration).scl(delta));
        this.velocity.clamp(0, this.speed);
    }

    public void deAccelerate() {
        float delta = Gdx.graphics.getDeltaTime();

        this.velocity.sub(this.velocity.cpy().nor().scl(this.deAcceleration * delta));
        this.velocity.clamp(0, this.speed);
    }

    public void move() {
        float delta = Gdx.graphics.getDeltaTime();

        this.pos.add(this.velocity.cpy().scl(delta));
    }
}
