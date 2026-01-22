package io.game.structures;

import java.util.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.*;

import io.game.creatures.*;

public class World {
    public OrthographicCamera camera = new OrthographicCamera();
    private float worldWidth = 8000;
    private float worldHeight = 8000;
    public Viewport viewport;

    // groups
    public enum Group {
        OBJECTS,
        PROJECTILES,
        CREATURES,
    }

    private class ObjGroup {
        public Group type;
        public Set<Obj> objects = new HashSet<>();
        public Set<CollisionShape> collisionShapes = new HashSet<>();

        public ObjGroup(Group type) {
            this.type = type;
        }

        public void add(Obj item) {
            this.objects.add(item);
            this.collisionShapes.add(item.collisionShape);
        }
    }

    private HashMap<Group, ObjGroup> groups = new HashMap<>();

    private Player player;

    // renderers
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    public World() {
        this.camera.setToOrtho(false, this.worldWidth, this.worldHeight);
        this.camera.zoom = 0.2f;
        this.camera.update();

        this.viewport = new FitViewport(this.worldWidth, this.worldHeight, this.camera);
        this.viewport.apply();

        for (Group type : Group.values()) {
            this.groups.put(type, new ObjGroup(type));
        }
    }

    public void update() {
        this.player.update();
        this.camera.position.set(this.player.pos.x, this.player.pos.y, 0);
        this.camera.update();
    }

    public void addObjToGroup(Group group, Obj obj) {
        this.groups.get(group).add(obj);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    // draws collision shapes of all objects
    public void drawCollisionShapes() {
        this.shapeRenderer.setProjectionMatrix(this.camera.combined);
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(0f, 0.65f, 1f, 0.75f);
        for (Obj obj : this.objects) {
            obj.drawCollisionShape(this.shapeRenderer);
        }
        this.player.drawCollisionShape(this.shapeRenderer);
        for (Obj obj : this.objects) {
            if (this.player.collisionShape.colliding(obj.collisionShape)) {
                this.shapeRenderer.setColor(0f, 1f, 1f, 1f);
                this.player.drawCollisionShape(this.shapeRenderer);
                this.shapeRenderer.setColor(0f, 0.65f, 1f, 0.75f);
            }
        }
        this.shapeRenderer.end();
    }

    public void draw() {

    }

    public void resize(int width, int height) {
        this.viewport.update(width, height);
        this.viewport.apply();
    }

    public void collideCollisionGroups(Group[] groups) {
        Set<CollisionShape> collisionShapes = new HashSet<>();

        for (Group group : groups) {
            collisionShapes.addAll(this.groups.get(group).collisionShapes);
        }

        Set<CollisionShape[]> pairs = CollisionShape.sweep(collisionShapes);
    }
}
