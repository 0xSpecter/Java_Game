package delta.shapes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;

import delta.structures.*;

/**
 *
 *
 *
 */
public class Figure {
    // :TODO: Maybe implement sprite version on segment
    static Comparator<Figure.Segment> comparator = Comparator.comparingInt(s -> s.z);
    public ArrayList<Segment> segments;

    public Figure(Shape shape, Consumer<ShapeRenderer> options) {
        this.segments = new ArrayList<>(Arrays.asList(new Figure.Segment(shape, options)));
    }

    public Figure(ArrayList<Segment> segments) {
        this.segments = segments;
    }

    static public class Segment {
        Shape shape;
        Vector2 offset;
        int z;
        Consumer<ShapeRenderer> options;

        public Segment(Shape shape, Consumer<ShapeRenderer> options) {
            this(shape, new Vector2(0, 0), 1, options);
        }

        public Segment(Shape shape, Vector2 offset, Consumer<ShapeRenderer> options) {
            this(shape, offset, 1, options);
        }

        public Segment(Shape shape, Vector2 offset, int z, Consumer<ShapeRenderer> options) {
            this.shape = shape;
            this.offset = offset;
            this.z = z;
            this.options = options;
        }

        public void draw(ShapeRenderer shapeRenderer) {
            this.options.accept(shapeRenderer);
            this.shape.draw(shapeRenderer, offset);
        }
    }

    public void sort(boolean reverseOrder) {
        this.segments.sort(reverseOrder ? Figure.comparator.reversed() : Figure.comparator);
    }

    public void draw(ShapeRenderer shapeRenderer) {
        for (Segment segment : this.segments) {
            segment.draw(shapeRenderer);
        }
    }
}
