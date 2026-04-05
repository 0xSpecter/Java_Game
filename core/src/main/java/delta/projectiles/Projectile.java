package delta.projectiles;

import delta.creatures.*;
import delta.Timer;

/**
 * base projectile class
 */
public abstract class Projectile extends Creature {
    public ProjectilePool pool;
    public float lifetime;
    public Timer lifetimeTimer;

    public Projectile(float lifetime) {
        super(0, 0);
        this.lifetime = lifetime;
        this.lifetimeTimer = new Timer(lifetime).setOnTriggered(this::free);
    }

    /**
     * returnes self for chaining
     */
    public Projectile reset() {
        this.pos.setZero();
        this.velocity.setZero();
        this.moveDir.setZero();
        this.lookDir.setZero();

        this.lifetimeTimer.start();

        return this;
    }

    /**
     * calls free on the bound projectile pool
     */
    public void free() {
        if (this.pool == null)
            throw new IllegalStateException("projectile has no pool to call free on");
        this.pool.free(this);
    }

    /**
     * updates timer and calls move
     */
    public void update() {
        this.lifetimeTimer.update();
        this.move();
    }

    public abstract Projectile clone();
}
