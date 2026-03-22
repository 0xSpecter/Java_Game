package delta.structures;

import java.util.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.input.RemoteSender;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.viewport.*;

import delta.creatures.*;
import delta.shapes.*;

public class World {
    private float worldWidth = 8000;
    private float worldHeight = 8000;

    public Ui ui;

    private Player player;
    public Groups groups = new Groups();

    // renderers
    ShapeRenderer worldRenderer = new ShapeRenderer();

    // viewports and cameras
    public OrthographicCamera worldCamera;
    public Viewport worldViewport;

    public World() {
        this.ui = new Ui(this.worldWidth, this.worldHeight);
        this.worldCamera = new OrthographicCamera();

        this.worldCamera.setToOrtho(false, this.worldWidth, this.worldHeight);
        this.worldCamera.zoom = 0.2f;
        this.worldCamera.update();

        this.worldViewport = new FillViewport(this.worldWidth, this.worldHeight, this.worldCamera);
        this.worldViewport.apply();
    }

    public void update() {
        Input.updateMouseWorldPosition(this.worldViewport);
        this.ui.update();
        this.player.update();
        this.worldCamera.position.set(this.player.pos.x, this.player.pos.y, 0);
        this.worldCamera.update();
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.groups.add(Groups.Types.OBJECTS, player);
    }

    // draws collision shapes of all objects
    public void drawCollisionShapes() {
        this.worldRenderer.setProjectionMatrix(this.worldCamera.combined);

        this.worldRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.worldRenderer.setColor(0f, 0.65f, 1f, 0.75f);
        for (Obj obj : this.groups.get(Groups.Types.OBJECTS).objects) {
            obj.drawCollisionShape(this.worldRenderer);
        }
        this.player.drawCollisionShape(this.worldRenderer);
        this.worldRenderer.end();

        this.worldRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.worldRenderer.setColor(1f, 0.5f, 0.5f, 0.75f);
        for (CollisionShape[] shapes : this.collideCollisionGroups(Groups.Types.OBJECTS)) {
            CollisionShape.resolveCollision(shapes[0], shapes[1], worldCamera);
            shapes[0].draw(worldRenderer);
            shapes[1].draw(worldRenderer);
        }
        this.worldRenderer.end();

        this.worldRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.worldRenderer.setColor(1f, 0f, 0.0f, 1f);
        this.worldRenderer.circle(Input.mouseWorldPosition.x, Input.mouseWorldPosition.y, 5);

        this.worldRenderer.setColor(0f, 1f, 0.3f, 1f);
        for (CollisionShape shape : this.groups.get(Groups.Types.OBJECTS).collisionShapes) {
            if (shape.contains(Input.mouseWorldPosition)) {
                shape.draw(worldRenderer);
            }
        }
        this.worldRenderer.end();
    }

    public void draw() {
        this.worldRenderer.setProjectionMatrix(this.worldCamera.combined);

        this.worldRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Obj obj : this.groups.get(Groups.Types.OBJECTS).objects) {
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

    public void dispose() {
        this.worldRenderer.dispose();
        this.ui.dispose();
    }
}
