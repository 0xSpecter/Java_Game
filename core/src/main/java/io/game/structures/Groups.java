package io.game.structures;

import java.util.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.*;

import io.game.creatures.*;

public class Groups {
    public static enum Types {
        OBJECTS,
        PROJECTILES,
        CREATURES,
    }

    private HashMap<Types, Group> groups = new HashMap<>();

    public class Group {
        public Types type;
        public Set<Obj> objects = new HashSet<>();
        public Set<CollisionShape> collisionShapes = new HashSet<>();

        public Group(Types type) {
            this.type = type;
        }

        public void add(Obj item) {
            this.objects.add(item);
            this.collisionShapes.add(item.collisionShape);
        }

        public void remove(Obj item) {
            this.objects.remove(item);
            this.collisionShapes.remove(item.collisionShape);
        }
    }

    // add element to group
    public void add(Types type, Obj obj) {
        this.groups.get(type).add(obj);
    }

    // remove an element from given group
    public void remove(Types type, Obj obj) {
        this.groups.get(type).remove(obj);
    }

    public Group get(Types type) {
        return this.groups.get(type);
    }

    public Groups() {
        // Generate groups
        for (Types type : Types.values()) {
            this.groups.put(type, new Group(type));
        }
    }
}
