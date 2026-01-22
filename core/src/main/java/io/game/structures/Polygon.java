package io.game.structures;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.Vector2;

public class Polygon extends Shape {
    private float[] vertices;

    public Polygon(float[] vertices, float scale) {
        this.vertices = vertices;
        this.scale = scale;
        this.setHighLows();
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

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.polygon(vertices);
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
