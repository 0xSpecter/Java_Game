package delta;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.ScreenUtils;

import delta.structures.*;
import delta.creatures.*;
import delta.shapes.*;

public class Main extends ApplicationAdapter {
    private World world;
    private Player player;

    @Override
    public void create() {
        world = new World();
        player = new Player(300f, 300f);
        player.addCollisionShape(new CollisionShape(Polygon.rectangle(50f), player));

        for (int i = 0; i < 30; i++) {
            // Obj obj = new Obj((int) (Math.random() * 2000 - 1000), (int) (Math.random() *
            // 2000 - 1000));
            Obj obj = new Obj((int) i * 1000, (int) 100f);
            if (Math.random() > 0.7)
                obj.addCollisionShape(new CollisionShape(Polygon.rectangle((int) (Math.random() * 300 + 10)), obj));
            else if (Math.random() > 0.7)
                obj.addCollisionShape(new CollisionShape(Polygon.triangle((int) (Math.random() * 300 + 10)), obj));
            else if (Math.random() > 0.50)
                obj.addCollisionShape(new CollisionShape(new Circle((int) (Math.random() * 300 + 10)), obj));
            else
                obj.addCollisionShape(new CollisionShape(Polygon.pentagon((int) (Math.random() * 300 + 10)), obj));
            world.groups.add(Groups.Types.OBJECTS, obj);
        }

        for (int i = 0; i < 100; i++) {
            Obj obj = new Obj((int) (Math.random() * 2000 - 1000), (int) (Math.random() * 2000 - 1000));
            obj.addCollisionShape(new CollisionShape(new Point(), obj));
            world.groups.add(Groups.Types.OBJECTS, obj);
        }

        world.setPlayer(player);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        this.world.update();

        this.world.drawCollisionShapes();
    }

    @Override
    public void resize(int width, int height) {
        world.resize(width, height);
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
