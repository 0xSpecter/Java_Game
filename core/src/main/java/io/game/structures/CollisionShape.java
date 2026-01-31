package io.game.structures;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;
import java.util.*;

public class CollisionShape {
    private Shape shape;
    private Obj parent;

    public CollisionShape(Shape shape, Obj parent) {
        this.shape = shape;
        this.parent = parent;
    }

    public boolean colliding(CollisionShape other) {
        return false;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        this.shape.draw(shapeRenderer, this.parent);
    }

    private static class SweepEdge {
        public float value;
        public CollisionShape origin;
        public boolean start;

        public SweepEdge(float value, CollisionShape origin, boolean start) {
            this.value = value;
            this.origin = origin;
            this.start = start;
        }

        public float getValue() {
            return this.value;
        }

        public boolean isStart() {
            return this.start;
        }
    }

    // broad phase collision
    public static Set<CollisionShape[]> sweep(Set<CollisionShape> possibleCandidates) {
        Set<CollisionShape[]> candidates = new HashSet<>();
        List<SweepEdge> edges = new ArrayList<>();
        Set<CollisionShape> active = new HashSet<>();

        for (CollisionShape pc : possibleCandidates) {
            edges.add(new SweepEdge(-pc.shape.scale, pc, true));
            edges.add(new SweepEdge(pc.shape.scale, pc, false));
        }

        edges.sort(Comparator.comparingDouble(SweepEdge::getValue));

        for (SweepEdge edge : edges) {
            if (edge.isStart()) {
                for (CollisionShape other : active) {
                    candidates.add(new CollisionShape[] { edge.origin, other });
                }
                active.add(edge.origin);
            } else {
                active.remove(edge.origin);
            }
        }

        return candidates;
    }

    public static Set<CollisionShape[]> narrow(Set<CollisionShape[]> pairedCandidates) {
        return pairedCandidates;
    }

    public boolean collide(CollisionShape other) {
        return false;
    }
}
