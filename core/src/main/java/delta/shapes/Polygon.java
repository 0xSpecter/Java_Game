package delta.shapes;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.math.MathUtils;

import delta.structures.*;

public class Polygon extends Shape {
    private Vector2[] vertices;
    private Vector2 centroid;
    private Vector2[] normalsMidpoints;
    private Vector2[] normals;

    public Polygon(Vector2[] vertices, float scale, Obj parent) {
        this.vertices = vertices;
        this.parent = parent;
        this.scale = scale;
        this.updateCentroid();
        this.updateNormals();
    }

    /**
     * this constructor creates a copy of the given polygon and a new parent
     */
    public Polygon(Polygon polygon, Obj parent) {
        this(polygon.vertices.clone(), polygon.scale, parent);
    }

    /**
     * this constructor creates a copy of the given polygon with the same parent
     */
    public Polygon(Polygon polygon) {
        this(polygon.vertices.clone(), polygon.scale, polygon.parent);
    }

    public static Polygon rectangle(float scale, Obj parent) {
        return new Polygon(new Vector2[] {
                new Vector2(0f, 0f),
                new Vector2(1f, 0f),
                new Vector2(1f, 1f),
                new Vector2(0f, 1f),
        }, scale, parent);
    }

    public static Polygon triangle(float scale, Obj parent) {
        return new Polygon(new Vector2[] {
                new Vector2(0f, 0f),
                new Vector2(0.5f, 1f),
                new Vector2(1f, 0f),
        }, scale, parent);
    }

    public static Polygon pentagon(float scale, Obj parent) {
        return new Polygon(new Vector2[] {
                new Vector2(0.5f, 1f),
                new Vector2(1f, 0.65f),
                new Vector2(0.8f, 0f),
                new Vector2(0.2f, 0f),
                new Vector2(0f, 0.65f),
        }, scale, parent);
    }

    // :TODO:
    // needs to be centered on 0.5, 0.5 so kinda wacky
    public static Polygon rndConvexPolygon(float scale, int verticesCount, float[] minmax, Obj parent) {
        Vector2[] vertices = new Vector2[verticesCount];
        for (int i = 0; i < verticesCount; i++) {

        }

        return new Polygon(vertices, scale, parent);
    }

    public void rotate(Matrix3 rotationMatrix) {
        for (Vector2 vertex : this.vertices) {
            vertex.mul(rotationMatrix);
        }
        this.updateCentroid();
        this.updateNormals();
    }

    // returns the closest WORLD vertex to a world posistion
    public Vector2 closest(Vector2 pos) {
        Vector2 closest = null;
        float minDst = Float.MAX_VALUE;

        for (Vector2 vertex : this.toWorld(this.vertices)) {
            float dst = vertex.dst2(pos);

            if (dst < minDst) {
                minDst = dst;
                closest = vertex;
            }
        }

        return closest;
    }

    public void updateCentroid() {
        this.centroid = new Vector2();
        for (Vector2 v : this.vertices) {
            this.centroid.add(v);
        }
        this.centroid.scl(1f / this.vertices.length);
    }

    public void draw(ShapeRenderer shapeRenderer, Vector2 offset) {
        Vector2[] r = this.toWorld(this.vertices, offset);

        if (r.length == 3) {
            shapeRenderer.triangle(r[0].x, r[0].y, r[1].x, r[1].y, r[2].x, r[2].y);
        } else {
            float cx = this.parent.pos.x;
            float cy = this.parent.pos.y;
            for (int i = 0; i < r.length; i++) {
                Vector2 p1 = r[i];
                Vector2 p2 = (i >= r.length - 1) ? r[0] : r[i + 1];
                shapeRenderer.triangle(
                        cx, cy,
                        p1.x, p1.y,
                        p2.x, p2.y);
            }
        }

        // this.drawNormals(shapeRenderer);
    }

    public void drawNormals(ShapeRenderer shapeRenderer) {
        Vector2[] m = this.toWorld(this.normalsMidpoints);

        for (int i = 0; i < normals.length; i++) {
            Vector2 dir = new Vector2(normals[i]).scl(20f);
            shapeRenderer.line(
                    m[i].x, m[i].y,
                    m[i].x + dir.x, m[i].y + dir.y);
            shapeRenderer.triangle(
                    m[i].x + dir.x - dir.y / 8,
                    m[i].y + dir.y + dir.x / 8,
                    m[i].x + dir.x * 1.25f,
                    m[i].y + dir.y * 1.25f,
                    m[i].x + dir.x + dir.y / 8,
                    m[i].y + dir.y - dir.x / 8);
        }
    }

    public Vector2[] toWorld(Vector2[] vertices) {
        return this.toWorld(vertices, 0, 0);
    }

    public Vector2[] toWorld(Vector2[] vertices, Vector2 offset) {
        return this.toWorld(vertices, offset.x, offset.y);
    }

    public Vector2[] toWorld(Vector2[] vertices, float ox, float oy) {
        Vector2[] worldVertices = new Vector2[vertices.length];
        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(
                    (vertices[i].x - centroid.x) * scale + this.parent.pos.x + ox,
                    (vertices[i].y - centroid.y) * scale + this.parent.pos.y + oy);
        }
        return worldVertices;
    }

    public void updateNormals() {
        Vector2[] normals = new Vector2[this.vertices.length];
        Vector2[] normalMidpoints = new Vector2[this.vertices.length];
        for (int i = 0; i < this.vertices.length; i++) {
            Vector2 p1 = this.vertices[i];
            Vector2 p2 = (i < this.vertices.length - 1) ? this.vertices[i + 1] : this.vertices[0];
            Vector2 diff = new Vector2(p2.x - p1.x, p2.y - p1.y);
            Vector2 normal = new Vector2(diff.y, -diff.x).nor();
            Vector2 midpoint = new Vector2(p1.x + diff.x / 2, p1.y + diff.y / 2);

            if (normal.dot(new Vector2(centroid).sub(midpoint)) > 0) {
                normal.scl(-1);
            }

            normals[i] = normal;
            normalMidpoints[i] = midpoint;
        }
        this.normals = normals;
        this.normalsMidpoints = normalMidpoints;
    }

    public Vector2[] getNormals(Shape other) {
        return this.normals;
    }

    public float[] projection(Vector2 axis) {
        Vector2[] vertices = this.toWorld(this.vertices);
        float max = vertices[0].dot(axis);
        float min = max;
        for (Vector2 vertex : vertices) {
            float dist = vertex.dot(axis);
            if (dist < min)
                min = dist;
            if (dist > max)
                max = dist;
        }
        return new float[] { min, max };
    }

    /**
     */
    @Override
    public boolean contains(Vector2 point) {
        float minX = this.parent.pos.x - scale / 2;
        float maxX = this.parent.pos.x + scale / 2;
        float minY = this.parent.pos.y - scale / 2;
        float maxY = this.parent.pos.y + scale / 2;
        if (point.x < minX || point.x > maxX || point.y < minY || point.y > maxY) {
            return false;
        }
        return true;
    }
}
