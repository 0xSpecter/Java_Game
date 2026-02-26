package delta.shapes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;
import java.util.*;
import java.util.stream.Stream;
import delta.Utils;

import delta.structures.*;

public class CollisionShape {
    /** shape which is used */
    public Shape shape;
    /** used to resolve a collision and create a manifold */
    private CollisionData data;
    public Obj parent;

    public static class CollisionData {
        public Vector2 normal;
        public float overlap;

        public CollisionData() {
            this.overlap = Float.MAX_VALUE;
        }

        public CollisionData(CollisionData data) {
            this.normal = data.normal.cpy();
            this.overlap = data.overlap;
        }
    }

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

    public static Set<CollisionShape[]> sweepOneToMany(CollisionShape one, Set<CollisionShape> possibleCandidates) {
        Set<CollisionShape[]> candidates = new HashSet<>();
        List<SweepEdge> edges = new ArrayList<>();
        Set<CollisionShape> backlog = new HashSet<>();
        boolean active = false;

        for (CollisionShape pc : possibleCandidates) {
            edges.add(new SweepEdge(pc.parent.pos.x - pc.shape.scale, pc, true));
            edges.add(new SweepEdge(pc.parent.pos.x + pc.shape.scale, pc, false));
        }
        edges.add(new SweepEdge(one.parent.pos.x - one.shape.scale, one, true));
        edges.add(new SweepEdge(one.parent.pos.x + one.shape.scale, one, false));

        edges.sort(Comparator.comparingDouble(SweepEdge::getValue));

        for (SweepEdge edge : edges) {
            if (edge.origin == one) {
                if (edge.isStart()) {
                    active = true;
                    for (CollisionShape shape : backlog) {
                        candidates.add(new CollisionShape[] { shape, one });
                    }
                } else
                    break;
            }
            if (edge.isStart()) {
                if (active) {
                    candidates.add(new CollisionShape[] { edge.origin, one });
                } else {
                    backlog.add(edge.origin);
                }
            } else {
                backlog.remove(edge.origin);
            }
        }

        return candidates;
    }

    public static Set<CollisionShape[]> narrow(Set<CollisionShape[]> pairedCandidates) {
        Set<CollisionShape[]> collidingPairs = new HashSet<>();

        for (CollisionShape[] pair : pairedCandidates) {
            Vector2[] axesCombined = Stream.concat(
                    Arrays.stream(pair[0].shape.getNormals(pair[1].shape, pair[1].parent, pair[0].parent)),
                    Arrays.stream(pair[1].shape.getNormals(pair[0].shape, pair[0].parent, pair[1].parent)))
                    .toArray(Vector2[]::new);

            // :TODO: remove duplicate axes, could be imporved
            Set<Vector2> axes = new HashSet<>(Arrays.asList(axesCombined));

            boolean separated = false;
            CollisionData data = new CollisionData();
            for (Vector2 axis : axes) {
                float[] m1 = pair[0].shape.projection(axis, pair[0].parent);
                float[] m2 = pair[1].shape.projection(axis, pair[1].parent);

                float left = m2[1] - m1[0];
                float right = m1[1] - m2[0];

                float overlap = Math.min(left, right);

                if (left < 0 || right < 0) {
                    separated = true;
                    break;
                }

                if (overlap < data.overlap) {
                    data.overlap = overlap;

                    if (left < right) {
                        data.normal = axis.cpy();
                    } else {
                        data.normal = axis.cpy().scl(-1);
                    }
                }
            }

            if (!separated) {
                pair[0].data = new CollisionData(data);
                pair[1].data = new CollisionData(data);
                pair[1].data.normal.scl(-1f);
                collidingPairs.add(pair);
            }
        }

        return collidingPairs;
    }

    public static void resolveCollision(CollisionShape o1, CollisionShape o2, OrthographicCamera camera) {
        Vector2 mtv1 = o1.data.normal.cpy().scl(o1.data.overlap);
        Vector2 mtv2 = o2.data.normal.cpy().scl(o2.data.overlap);

        Utils.debugDrawVector(camera, mtv1, o1.parent.pos);
        Utils.debugDrawVector(camera, mtv2, o2.parent.pos);

        Vector2 c1 = o1.shape.closest(o1.parent, o2.parent.pos);
        Vector2 c2 = o2.shape.closest(o2.parent, o1.parent.pos);

        Utils.debugDrawPoint(camera, c1, 5);
        Utils.debugDrawPoint(camera, c2, 5);

        // o1.data = null;
        // o2.data = null;
    }

    public boolean contains(Vector2 position) {
        return this.shape.contains(new Vector2(position).sub(this.parent.pos));
    }
}
