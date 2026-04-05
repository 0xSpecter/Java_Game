package delta.projectiles;

import com.badlogic.gdx.graphics.Color;

import delta.shapes.*;

/**
 * example class of how to implment a projectile
 */
public class BasicProjectile extends Projectile {
    public BasicProjectile() {
        super(3);
        this.maxSpeed = 2000;
        this.acceleration = 10000;
        this.figure = new Figure(new Circle(20f, this), sr -> sr.setColor(Color.SALMON));
    }

    @Override
    public Projectile clone() {
        return new BasicProjectile();
    }
}
