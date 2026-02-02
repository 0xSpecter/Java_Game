package io.game.structures;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;
import java.util.*;
import java.util.stream.Stream;

public class CollisionShape {
    private Shape shape;
    public Obj parent;

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
            edges.add(new SweepEdge(pc.parent.pos.x - pc.shape.scale, pc, true));
            edges.add(new SweepEdge(pc.parent.pos.x + pc.shape.scale, pc, false));
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

    // :TODO: add collision data
    public static Set<CollisionShape[]> narrow(Set<CollisionShape[]> pairedCandidates) {
        Set<CollisionShape[]> collidingPairs = new HashSet<>();
        for (CollisionShape[] pair : pairedCandidates) {
            Vector2[] axesCombined = Stream.concat(
                    Arrays.stream(pair[0].shape.getNormals(pair[1].shape, pair[1].parent, pair[0].parent)),
                    Arrays.stream(pair[1].shape.getNormals(pair[0].shape, pair[0].parent, pair[1].parent)))
                    .toArray(Vector2[]::new);

            // remove duplicate axes, could be imporved
            Set<Vector2> axes = new HashSet<>(Arrays.asList(axesCombined));

            boolean separated = false;
            for (Vector2 axis : axes) {
                float[] m1 = pair[0].shape.projection(axis, pair[0].parent);
                float[] m2 = pair[1].shape.projection(axis, pair[1].parent);
                if (m1[1] < m2[0] || m2[1] < m1[0]) {
                    separated = true;
                    break;
                }
            }
            if (!separated)
                collidingPairs.add(pair);
        }

        return collidingPairs;
    }
}
