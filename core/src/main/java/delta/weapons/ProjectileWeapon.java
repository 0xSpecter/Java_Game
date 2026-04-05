package delta.weapons;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import delta.structures.Obj;

/**
 * base abstract class for all weapons
 */
public abstract class ProjectileWeapon extends Weapon {
    public float firerate;
    public boolean automatic = true;

    public ProjectileWeapon(float firerate, Obj parent) {
        super(parent, 60 / firerate);
        this.firerate = firerate;
    }

    public void fire(Obj target) {
        this.attack();
    };
}
