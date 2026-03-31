package delta.structures;

import java.util.*;
import delta.creatures.*;
import delta.shapes.*;

public class Groups {
    public enum Types {
        OBJECTS,
        PROJECTILES,
        CREATURES,
        ENEMIES,
    }

    private HashMap<Types, Group> groups = new HashMap<>();

    public static class Group implements Iterable<Obj> {
        public Types type;
        public HashSet<Obj> objects = new HashSet<>();
        public HashSet<CollisionShape> collisionShapes = new HashSet<>();

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

        @Override
        public Iterator<Obj> iterator() {
            return this.objects.iterator();
        }
    }

    public void add(Types type, Obj obj) {
        this.groups.get(type).add(obj);
    }

    public void remove(Types type, Obj obj) {
        this.groups.get(type).remove(obj);
    }

    public Group get(Types type) {
        return this.groups.get(type);
    }

    public Groups() {
        for (Types type : Types.values()) {
            this.groups.put(type, new Group(type));
        }
    }
}
