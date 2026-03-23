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

        for (Obj obj : this.groups.get(Groups.Types.CREATURES)) {
            Creature creature = (Creature) obj;
            creature.update();
        }

        for (CollisionShape[] pair : this.collideCollisionGroups(Groups.Types.CREATURES)) {
            Creature c1 = (Creature) pair[0].parent;
            Creature c2 = (Creature) pair[1].parent;
            c1.moveDirection.scl(pair[1].shape.getNormals(pair[0].shape)[0]);
            c2.moveDirection.scl(pair[0].shape.getNormals(pair[1].shape)[0]);
        }

        player.update();

        this.worldCamera.position.set(this.player.pos.x, this.player.pos.y, 0);
        this.worldCamera.update();
    }

    /**
     * // sets the internal player variable and adds it to the relevent groups
     */
    public void setPlayer(Player player) {
        this.player = player;
        this.groups.add(Groups.Types.OBJECTS, player);
    }

    // draws collision shapes of all objects
    public void drawCollisionShapes() {
        this.worldRenderer.setProjectionMatrix(this.worldCamera.combined);

        this.worldRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.worldRenderer.setColor(0f, 0.65f, 1f, 0.75f);
        for (Obj obj : this.groups.get(Groups.Types.OBJECTS)) {
            obj.drawCollisionShape(this.worldRenderer);
        }

        this.worldRenderer.end();
    }

    public void draw() {
        // :TODO: Drawing will normaly work on a z-index sorted group but rn it just
        // draws everyting
        this.worldRenderer.setProjectionMatrix(this.worldCamera.combined);

        this.worldRenderer.begin(ShapeRenderer.ShapeType.Filled);
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

    public void dispose() {
        this.worldRenderer.dispose();
        this.ui.dispose();
    }
}
