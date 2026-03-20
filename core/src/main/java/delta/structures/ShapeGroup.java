package delta.structures;

import java.util.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.*;

import delta.creatures.*;
import delta.shapes.*;

class ShapeGroup {
    List<Part> shapes;

    public static class Part {
        public Shape shape;
        public Vector2 offset;

        public Part(Shape shape, Vector2 offset) {
            this.offset = offset;
            this.shape = shape;
        }
    }

    public ShapeGroup() {
        this.shapes = new ArrayList<>();
    }

    public ShapeGroup(List<Part> shapes) {
        this.shapes = shapes;
    }

    public void add(Part shape) {
        this.shapes.add(shape);
    }

    public void insert(Part shape, int index) {
        if (index < 0 || index >= this.shapes.size())
            throw new IllegalArgumentException();

        this.shapes.set(index, shape);
    }
}
