package io.game.structures;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Polygon extends Shape {
    private Vector2[] vertices;
    private Vector2[] realsize;

    public Polygon(Vector2[] vertices, float scale) {
        this.vertices = vertices;
        this.scale = scale;
        this.updateRealsize();
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

    public void draw(ShapeRenderer shapeRenderer, Obj parent) {
        float xoff = parent.pos.x - this.scale / 2;
        float yoff = parent.pos.y - this.scale / 2;
        Vector2[] result = new Vector2[this.realsize.length];
        for (int i = 0; i < this.realsize.length; i++) {
            result[i] = new Vector2(this.realsize[i].x + xoff, this.realsize[i].y + yoff);
        }
        // render
    }

    public void updateRealsize() {
        Vector2[] result = new Vector2[this.vertices.length];
        for (int i = 0; i < this.vertices.length; i++) {
            result[i].x = this.vertices[i].x * this.scale;
            result[i].y = this.vertices[i].y * this.scale;
        }
        this.realsize = result;
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
