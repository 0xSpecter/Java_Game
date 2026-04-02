package delta.creatures;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import delta.structures.World;
import delta.Utils;

public class Enemy extends Creature {
    private Vector2 dest;
    private float sleep;

    public Enemy(float x, float y) {
        super(x, y);
        this.newDest();
    }

    public void update() {
        this.lookDir = this.getTargetDirection();
        if (this.sleep > 0) {
            this.sleep -= World.getDelta();
        } else if (this.dest != null) {
            this.moveDir = this.dest.cpy().sub(this.pos).nor();
            this.move();
        }
    }

    public void ai() {
        this.target = World.player;
        if (this.pos.dst2(this.dest) < 50 * 50) {
            this.velocity.setZero();
            this.sleep = MathUtils.random(0, 2);
            this.newDest();
        }
    }

    private void newDest() {
        if (MathUtils.random() < 0.1 && this.target != null) {
            this.dest = this.target.pos.cpy();
        } else {
            this.dest = this.pos.cpy().add(Utils.randomDirection().scl(MathUtils.random(300, 800)));
        }
    }
}
