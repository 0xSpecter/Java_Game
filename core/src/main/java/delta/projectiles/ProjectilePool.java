package delta.projectiles;

import java.util.ArrayDeque;
import java.util.HashSet;

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
        if (this.active.contains(projectile))
            throw new IllegalStateException("allocated already allocated projectile");
        this.free.add(projectile);
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
            this.free.pop();
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
}
