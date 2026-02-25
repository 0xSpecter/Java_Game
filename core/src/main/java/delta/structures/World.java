package delta.structures;

import java.util.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.input.RemoteSender;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.viewport.*;

import delta.creatures.*;
import delta.shapes.*;

public class World {
    public OrthographicCamera camera = new OrthographicCamera();
    private float worldWidth = 8000;
    private float worldHeight = 8000;
    public Viewport viewport;

    private Player player;
    public Groups groups = new Groups();

    // renderers
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    // input
    Input input = new Input();

    public World() {
        this.camera.setToOrtho(false, this.worldWidth, this.worldHeight);
        this.camera.zoom = 0.2f;
        this.camera.update();

        this.viewport = new FillViewport(this.worldWidth, this.worldHeight, this.camera);
        this.viewport.apply();
    }

    public void update() {
        this.player.update();
        this.camera.position.set(this.player.pos.x, this.player.pos.y, 0);
        this.camera.update();
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.groups.add(Groups.Types.OBJECTS, player);
    }

    // draws collision shapes of all objects
    public void drawCollisionShapes() {
        this.shapeRenderer.setProjectionMatrix(this.camera.combined);
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(0f, 0.65f, 1f, 0.75f);
        for (Obj obj : this.groups.get(Groups.Types.OBJECTS).objects) {
            obj.drawCollisionShape(this.shapeRenderer);
        }
        this.player.drawCollisionShape(this.shapeRenderer);
        this.shapeRenderer.end();

        this.collideCollisionGroups(Groups.Types.OBJECTS);

        this.shapeRenderer.setProjectionMatrix(this.camera.combined);
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(1f, 0.5f, 0.5f, 0.75f);
        for (CollisionShape[] shapes : this.collideCollisionGroups(Groups.Types.OBJECTS)) {
            CollisionShape.resolveCollision(shapes[0], shapes[1], camera);
            shapes[0].draw(shapeRenderer);
            shapes[1].draw(shapeRenderer);
        }
        this.shapeRenderer.end();
    }

    public void draw() {

    }

    public void resize(int width, int height) {
        this.viewport.update(width, height);
        this.viewport.apply();
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

    public void dispose() {
        this.shapeRenderer.dispose();
    }
}
