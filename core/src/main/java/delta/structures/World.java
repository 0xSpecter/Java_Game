package delta.structures;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.utils.viewport.*;

import delta.creatures.*;
import delta.shapes.*;

public class World {
    public static World instance;
    public static Player player = new Player();
    public Groups groups = new Groups();

    // renderers
    ShapeRenderer worldRenderer = new ShapeRenderer();

    // viewports and cameras
    public OrthographicCamera worldCamera;
    public Viewport worldViewport;

    public World() {
        World.instance = this;
        this.groups.add(Groups.Types.OBJECTS, World.player);

        this.worldCamera = new OrthographicCamera();

        this.worldCamera.setToOrtho(false);
        this.worldCamera.zoom = 3f;
        this.worldCamera.update();

        this.worldViewport = new FitViewport(Constants.worldWidth, Constants.worldHeight, this.worldCamera);
        this.worldViewport.apply();
    }

    public void update() {
        Input.updateMouseWorldPosition(this.worldViewport);

        // ai
        for (Obj obj : this.groups.get(Groups.Types.ENEMIES)) {
            Enemy enemy = (Enemy) obj;
            enemy.ai();
        }

        for (Obj obj : this.groups.get(Groups.Types.CREATURES)) {
            Creature creature = (Creature) obj;
            creature.update();
        }

        player.update();
        player.targetClosest(this.groups.get(Groups.Types.ENEMIES));

        this.worldCamera.position.set(this.player.pos.x, this.player.pos.y, 0);
        this.worldCamera.update();
    }

    // draws collision shapes of all objects
    public void drawCollisionShapes() {
        this.renderSetup();

        this.worldRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.worldRenderer.setColor(0f, 0.65f, 1f, 0.75f);
        for (Obj obj : this.groups.get(Groups.Types.OBJECTS)) {
            obj.drawCollisionShape(this.worldRenderer);
        }

        this.worldRenderer.end();
    }

    private void renderSetup() {
        this.worldViewport.apply();
        this.worldRenderer.setProjectionMatrix(this.worldCamera.combined);
    }

    public void draw() {
        // :TODO: Drawing will normaly work on a z-index sorted group but rn it just
        // draws everyting
        this.renderSetup();

        this.worldRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // draws a checkerboard map for testing
        int scale = 300;
        float zoom = this.worldCamera.zoom;
        int width = (int) (this.worldViewport.getWorldWidth() * zoom + scale * 5) / scale;
        int height = (int) (this.worldViewport.getWorldHeight() * zoom + scale * 5) / scale;
        int camX = (int) (this.worldCamera.position.x / scale);
        int camY = (int) (this.worldCamera.position.y / scale);

        int x = -width / 2 + camX;
        int y = -height / 2 + camY;
        while (true) {
            this.worldRenderer.setColor((x + y) % 2 == 0 ? Color.BLACK : Color.WHITE);
            this.worldRenderer.rect(x * scale, y * scale, scale, scale);
            x += 1;
            if (x >= width / 2 + camX) {
                y += 1;
                x = -width / 2 + camX;
                if (y >= height / 2 + camY) {
                    break;
                }
            }
        }

        for (Obj obj : this.groups.get(Groups.Types.OBJECTS)) {
            obj.drawFigure(this.worldRenderer);
        }

        this.worldRenderer.end();
    }

    public void resize(int width, int height) {
        this.worldViewport.update(width, height);
        this.worldViewport.apply();
    }

    public Set<CollisionShape[]> collideCollisionGroups(Groups.Types[] groups) {
        Set<CollisionShape> collisionShapes = new HashSet<>();

        for (Groups.Types group : groups) {
            collisionShapes.addAll(this.groups.get(group).collisionShapes);
        }

        Set<CollisionShape[]> pairs = CollisionShape.sweep(collisionShapes);
        return CollisionShape.narrow(pairs);
    }

    public Set<CollisionShape[]> collideCollisionGroups(Groups.Types groups) {
        return this.collideCollisionGroups(new Groups.Types[] { groups });
    }

    public Set<CollisionShape[]> collideOneToMany(CollisionShape one, Groups.Types[] groups) {
        Set<CollisionShape> collisionShapes = new HashSet<>();

        for (Groups.Types group : groups) {
            collisionShapes.addAll(this.groups.get(group).collisionShapes);
        }

        Set<CollisionShape[]> pairs = CollisionShape.sweepOneToMany(one, collisionShapes);
        return CollisionShape.narrow(pairs);
    }

    public Set<CollisionShape[]> collideOneToMany(CollisionShape one, Groups.Types groups) {
        return this.collideOneToMany(one, new Groups.Types[] { groups });
    }

    public static float getDelta() {
        return Gdx.graphics.getDeltaTime();
    }

    public void dispose() {
        this.worldRenderer.dispose();
    }
}
