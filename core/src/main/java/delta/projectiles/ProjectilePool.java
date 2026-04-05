package delta.projectiles;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import delta.weapons.*;

/**
 * an optimasajon class to make projectiles less prosessing intensive
 */
public class ProjectilePool {
    private HashSet<Projectile> active = new HashSet<>();
    private ArrayDeque<Projectile> free = new ArrayDeque<>();

    public ProjectilePool() {

    }

    /**
     * allocate an instance of a projectile to free pool
     */
    public void alloc(Projectile projectile) {
        if (this.active.contains(projectile) || this.free.contains(projectile))
            throw new IllegalStateException("allocated already allocated projectile");
        projectile.pool = this;
        this.free.add(projectile);
    }

    public int allocNecessary(float firerate, Projectile projectile) {
        int allocated = (int) (projectile.lifetime * (firerate / 60)) + 1;
        for (int i = 0; i < allocated; i++) {
            this.alloc(projectile.clone());
        }

        return allocated;
    }

    /**
     * retures a free projectile and calls reset on it
     */
    public Projectile obtain() {
        if (free.isEmpty())
            throw new IllegalStateException("Pool has run out of free projectiles");
        Projectile projectile = free.pop().reset();
        this.active.add(projectile);
        return projectile;
    }

    /**
     * frees a projectile from active pool and adds it to free pool
     */
    public void free(Projectile projectile) {
        if (this.active.remove(projectile))
            this.free.add(projectile);
    }

    /**
     * removes count amount of free projectiles from Pool
     */
    public int purge(int count) {
        int removed = 0;
        while (!this.free.isEmpty() && removed < count) {
            Projectile proj = this.free.pop();
            proj.pool = null;
            removed++;
        }

        return removed;
    }

    /**
     * returns how many free projectiles there are
     */
    public int freeCount() {
        return free.size();
    }

    /**
     * returns count of active projectiles
     */
    public int activeCount() {
        return active.size();
    }

    public void update() {
        // idk fikser at man kan fjerne elementer mens loopen pågår
        for (Projectile projectile : new ArrayList<>(this.active)) {
            projectile.update();
        }
    }

    public void draw(ShapeRenderer shapeRenderer) {
        for (Projectile projectile : this.active) {
            projectile.drawFigure(shapeRenderer);
        }
    }
}
