package io.game.structures;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Polygon extends Shape {
    private Vector2[] vertices;
    private Vector2[] realsize;
    private Vector2 centroid;

    public Polygon(Vector2[] vertices, float scale) {
        this.vertices = vertices;
        this.scale = scale;
        this.updateCentroid();
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
        Vector2[] r = this.worldVertices(parent);
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
    }

    public Vector2[] worldVertices(Obj parent) {
        Vector2[] vertices = new Vector2[this.vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vector2(
                    (this.vertices[i].x - centroid.x) * scale + parent.pos.x,
                    (this.vertices[i].y - centroid.y) * scale + parent.pos.y);
        }
        return vertices;
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
