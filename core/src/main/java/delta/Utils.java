package delta;

import java.util.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.math.*;

import delta.creatures.*;
import delta.shapes.*;

public class Utils {
    private static final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public static void debugDrawLine(OrthographicCamera camera, Vector2 p1, Vector2 p2) {
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        shapeRenderer.line(p1.x, p1.y, p2.x, p2.y);
        shapeRenderer.end();
    }

    public static void debugDrawVector(OrthographicCamera camera, Vector2 vector, Vector2 pos) {
        if (vector.isZero())
            return;
        float length = vector.len();

        Vector2 dir = new Vector2(vector).nor().scl(length);

        float endX = pos.x + dir.x;
        float endY = pos.y + dir.y;

        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        shapeRenderer.line(pos.x, pos.y, endX, endY);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float arrowWidth = Math.min(4f, length * 0.1f);
        float arrowTip = Math.min(length * 0.25f, length * 0.3f);

        Vector2 perp = new Vector2(-dir.y, dir.x).nor().scl(arrowWidth);

        shapeRenderer.triangle(
                endX - perp.x, endY - perp.y,
                endX + perp.x, endY + perp.y,
                endX + dir.x * arrowTip / length, endY + dir.y * arrowTip / length);

        shapeRenderer.end();
    }

    public static void debugDrawPoint(OrthographicCamera camera, Vector2 pos, float radius) {
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        shapeRenderer.circle(pos.x, pos.y, radius);
        shapeRenderer.end();
    }

    public static void dispose() {
        shapeRenderer.dispose();
    }

    public static Vector2 randomDirection() {
        float angle = MathUtils.random(MathUtils.PI2);
        return new Vector2(MathUtils.cos(angle), MathUtils.sin(angle));
    }
}
