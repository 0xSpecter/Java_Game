package delta.projectiles;

import delta.creatures.*;

/**
 * base projectile class
 */
public class Projectile extends Creature {
    public float range;
    public float travelDistance;

    public Projectile() {
        super(0, 0);
    }

    /**
     * returnes self for chaining
     */
    public Projectile reset() {
        this.pos.set(0, 0);
        this.travelDistance = 0;

        return this;
    }
}
