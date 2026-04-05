package delta.weapons;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import delta.creatures.*;
import delta.projectiles.*;
import delta.structures.*;

/**
 * example class of how to implement a basic ranged weapon
 */
public class BasicWeapon extends ProjectileWeapon {
    public static final ProjectilePool pool = new ProjectilePool();
    public static final Projectile projectile = new BasicProjectile();
    private int allocated;

    public BasicWeapon(Obj parent) {
        super(500, parent);
        this.allocated = BasicWeapon.pool.allocNecessary(this.firerate, BasicWeapon.projectile);
    }

    public void dispose() {
        BasicWeapon.pool.purge(this.allocated);
    }

    @Override
    public void fire(Obj target) {
        super.fire(target);

        Projectile projectile = BasicWeapon.pool.obtain();
        projectile.pos.set(this.parent.pos);
        projectile.target = (Creature) target;
        projectile.moveDir = projectile.getTargetDirection();
    }
}
