package io.game.structures;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;
import java.util.*;

public class CollisionShape {
    private Shape shape;

    public CollisionShape(Shape shape) {
        this.shape = shape;
    }

    public boolean colliding(CollisionShape other) {
        return false;
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

    public static Set<CollisionShape[]> sweep(Set<CollisionShape> possibleCandidates) {
        Set<CollisionShape[]> pairs = new HashSet<>();
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
                    pairs.add(new CollisionShape[] { edge.origin, other });
                }
                active.add(edge.origin);
            } else {
                active.remove(edge.origin);
            }
        }

        return pairs;
    }
}
