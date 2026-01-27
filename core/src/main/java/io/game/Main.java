package io.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import io.game.structures.*;
import io.game.creatures.*;

public class Main extends ApplicationAdapter {
    private World world;
    private Player player;

    @Override
    public void create() {
        world = new World();
        player = new Player(300f, 300f);
        player.addCollisionShape(new CollisionShape(Polygon.triangle(100f), player));

        for (int i = 0; i < 10; i++) {
            Obj obj = new Obj((int) (Math.random() * 1000 - 500), (int) (Math.random() * 1000 - 500));
            obj.addCollisionShape(new CollisionShape(Polygon.rectangle((int) (Math.random() * 150 + 10)), obj));
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

    }
}
