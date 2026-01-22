package io.game.creatures;

import io.game.structures.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.Vector2;

public class Creature extends Obj {
    public float speed = 1000;
    public float hp = 100;
    public Vector2 velocity = new Vector2(0, 0);
    public Vector2 acceleration = new Vector2(0, 0);
    protected int max_slots = 2;
    protected Obj[] slots = {};
    protected Obj target = null;

    public Creature(float x, float y) {
        super(x, y);
    }
}
