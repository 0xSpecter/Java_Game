package delta.weapons;

import delta.Timer;

import delta.structures.Obj;

/**
 * base abstract class for all weapons
 */
public abstract class Weapon {
    public Timer cooldown;
    public Obj parent;

    public Weapon(Obj parent, float cooldown) {
        this.parent = parent;
        this.cooldown = new Timer(cooldown);
    }

    public void attack() {
        if (!this.canFire()) {
            throw new IllegalStateException("cooldown is not finished");
        }
        this.cooldown.start();
    }

    public boolean canFire() {
        return this.cooldown.isFinished();
    }

    public void update() {
        this.cooldown.update();
    }
}
