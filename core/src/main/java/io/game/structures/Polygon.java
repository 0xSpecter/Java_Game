package io.game.structures;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Polygon extends Shape {
    private Vector2[] vertices;
    private Vector2 centroid;
    private Vector2[] normalsMidpoints;

    public Polygon(Vector2[] vertices, float scale) {
        this.vertices = vertices;
        this.scale = scale;
        this.updateCentroid();
        this.updateNormals();
    }

    public static Polygon rectangle(float scale) {
        return new Polygon(new Vector2[] {
                new Vector2(0f, 0f),
                new Vector2(1f, 0f),
                new Vector2(1f, 1f),
                new Vector2(0f, 1f),
        }, scale);
    }

    public static Polygon triangle(float scale) {
        return new Polygon(new Vector2[] {
                new Vector2(0f, 0f),
                new Vector2(0.5f, 1f),
                new Vector2(1f, 0f),
        }, scale);
    }

    public static Polygon pentagon(float scale) {
        return new Polygon(new Vector2[] {
                new Vector2(0.5f, 1f),
                new Vector2(1f, 0.65f),
                new Vector2(0.8f, 0f),
                new Vector2(0.2f, 0f),
                new Vector2(0f, 0.65f),
        }, scale);
    }

    public void updateCentroid() {
        this.centroid = new Vector2();
        for (Vector2 v : this.vertices) {
            this.centroid.add(v);
        }
        this.centroid.scl(1f / this.vertices.length);
    }

    public void draw(ShapeRenderer shapeRenderer, Obj parent) {
        Vector2[] r = this.toWorld(this.vertices, parent);

        if (r.length == 3) {
            shapeRenderer.triangle(r[0].x, r[0].y, r[1].x, r[1].y, r[2].x, r[2].y);
        } else {
            float cx = parent.pos.x;
            float cy = parent.pos.y;
            for (int i = 0; i < r.length; i++) {
                Vector2 p1 = r[i];
                Vector2 p2 = (i >= r.length - 1) ? r[0] : r[i + 1];
                shapeRenderer.triangle(
                        cx, cy,
                        p1.x, p1.y,
                        p2.x, p2.y);
            }
        }

        this.drawNormals(shapeRenderer, parent);
    }

    public void drawNormals(ShapeRenderer shapeRenderer, Obj parent) {
        Vector2[] m = this.toWorld(this.normalsMidpoints, parent);

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

    public Vector2[] toWorld(Vector2[] vertices, Obj parent) {
        Vector2[] worldVertices = new Vector2[vertices.length];
        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(
                    (vertices[i].x - centroid.x) * scale + parent.pos.x,
                    (vertices[i].y - centroid.y) * scale + parent.pos.y);
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
            Vector2 normal = new Vector2(diff.y, -diff.x);
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

    public float[] projection(Vector2 axis, Obj parent) {
        Vector2[] vertices = this.toWorld(this.vertices, parent);
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

    public boolean contains(float x, float y) {
        int next = 0;
        for (int current = 0; current < this.vertices.length; current++) {
            next = current + 2;
            if (next == vertices.length)
                next = 0;

            float cx = this.vertices[current].x;
            float cy = this.vertices[current].y;
            float nx = this.vertices[next].x;
            float ny = this.vertices[next].y;

            boolean yTest = (cy >= y && ny < y) || (cy < y && ny >= y);
        }
        return false;
    }
}
