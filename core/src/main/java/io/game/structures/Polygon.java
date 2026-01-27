package io.game.structures;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Polygon extends Shape {
    private float[] vertices;
    private float[] realsize;

    public Polygon(float[] vertices, float scale) {
        this.vertices = vertices;
        this.scale = scale;
        this.updateRealsize();
    }

    public static Polygon rectangle(float scale) {
        return new Polygon(new float[] {
                0f, 0f,
                1f, 0f,
                1f, 1f,
                0f, 1f,
        }, scale);
    }

    public static Polygon triangle(float scale) {
        return new Polygon(new float[] {
                0.5f, 0.5f,
                1f, 1f,
                0f, 1f,
        }, scale);
    }

    public void draw(ShapeRenderer shapeRenderer, Obj parent) {
        float xoff = parent.pos.x - this.scale / 2;
        float yoff = parent.pos.y - this.scale / 2;
        float[] result = new float[this.realsize.length];
        for (int i = 0; i < this.realsize.length; i += 2) {
            result[i] = this.realsize[i] + xoff;
            result[i + 1] = this.realsize[i + 1] + yoff;
        }
        shapeRenderer.polygon(result);
    }

    public void updateRealsize() {
        float[] result = new float[this.vertices.length];
        for (int i = 0; i < this.vertices.length; i++) {
            result[i] = this.vertices[i] * this.scale;
        }
        this.realsize = result;
    }

    public boolean contains(float x, float y) {
        int next = 0;
        for (int current = 0; current < this.vertices.length; current += 2) {
            next = current + 2;
            if (next == vertices.length)
                next = 0;

            float cx = this.vertices[current];
            float cy = this.vertices[current + 1];
            float nx = this.vertices[next];
            float ny = this.vertices[next + 1];

            boolean yTest = (cy >= y && ny < y) || (cy < y && ny >= y);
        }
        return false;
    }
}
