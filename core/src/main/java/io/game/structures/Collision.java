package io.game.structures;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.Vector2;

public class CollisionShape {
    public int radius = -1;
    public int width = -1;
    public int height = -1;
    private Obj parent;
    public int xOffset = 0;
    public int yOffset = 0;

    public CollisionShape(int radius, int xOffset, int yOffset, Obj parent) {
        this.radius = radius;
        this.parent = parent;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public CollisionShape(int radius, Obj parent) {
        this.radius = radius;
        this.parent = parent;
    }

    public CollisionShape(int width, int height, Obj parent) {
        this.width = width;
        this.height = height;
        this.parent = parent;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        if (this.radius == -1) {
            shapeRenderer.rect(this.parent.pos.x - this.width / 2, this.parent.pos.y - this.height / 2, this.width,
                    this.height);
        } else {
            shapeRenderer.circle(this.parent.pos.x, this.parent.pos.y, this.radius);
        }
    }

    public Vector2 getPosition() {
        return this.parent.pos;
    }

    public boolean colliding(CollisionShape other) {
        Vector2 otherPos = other.getPosition();
        float x = this.parent.pos.x;
        float y = this.parent.pos.y;
        float ox = otherPos.x;
        float oy = otherPos.y;

        if (this.radius != -1) {
            if (other.radius != -1) {
                float dx = x - ox;
                float dy = y - oy;
                float r = this.radius + other.radius;
                return dx * dx + dy * dy <= r * r;
            } else {
                float closestX = Math.max(ox - other.width / 2, Math.min(x, ox + other.width / 2));
                float closestY = Math.max(oy - other.height / 2, Math.min(y, oy + other.height / 2));

                float dx = x - closestX;
                float dy = y - closestY;

                return dx * dx + dy * dy <= this.radius * this.radius;
            }
        } else {
            if (other.radius != -1) {
                float closestX = Math.max(x - this.width / 2, Math.min(ox, x + this.width / 2));
                float closestY = Math.max(y - this.height / 2, Math.min(oy, y + this.height / 2));

                float dx = ox - closestX;
                float dy = oy - closestY;

                return dx * dx + dy * dy <= other.radius * other.radius;
            } else {
                return (x + this.width / 2 >= ox - other.width / 2 &&
                        x - this.width / 2 <= ox + other.width / 2 &&
                        y + this.height / 2 >= oy - other.height / 2 &&
                        y - this.height / 2 <= oy + other.height / 2);
            }
        }
    }
}
