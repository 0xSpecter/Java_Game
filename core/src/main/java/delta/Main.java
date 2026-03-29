package delta;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import delta.structures.*;
import delta.creatures.*;
import delta.shapes.*;

public class Main extends ApplicationAdapter {
    Ui ui;
    World world;
    Input input;
    Player player;

    @Override
    public void create() {
        world = new World();
        ui = new Ui();
        input = new Input();
        Gdx.input.setInputProcessor(this.input);

        player = new Player(0f, 0f);
        player.setCollisionShape(new CollisionShape(Polygon.rectangle(50f, player), player));

        player.setFigure(new Figure(new ArrayList<>(Arrays.asList(
                new Figure.Segment(Polygon.rectangle(50f, player), shapeRenderer -> {
                    shapeRenderer.setColor(Color.MAROON);
                }),
                new Figure.Segment(new Circle(10f, player), new Vector2(0, 0), shapeRenderer -> {
                    shapeRenderer.setColor(Color.WHITE);
                })))));

        for (int i = 0; i < 30; i++) {
            BouncingBall ball = new BouncingBall((int) (Math.random() * 2000 - 1000), (int) (Math.random() *
                    2000 - 1000));

            Circle circle = new Circle(50f, ball);
            ball.setFigure(new Figure(new Circle(circle), shapeRenderer -> shapeRenderer.setColor(Color.LIME)));
            ball.setCollisionShape(new CollisionShape(new Circle(circle)));

            world.groups.add(Groups.Types.OBJECTS, ball);
            world.groups.add(Groups.Types.CREATURES, ball);
        }

        world.setPlayer(player);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.05f, 0.05f, 0.05f, 1f);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        this.world.update();
        this.ui.update();

        this.world.draw();
        this.ui.draw();
        // this.world.drawCollisionShapes();
    }

    @Override
    public void resize(int width, int height) {
        world.resize(width, height);
        ui.resize(width, height);
    }

    @Override
    public void dispose() {
        world.dispose();
        ui.dispose();
        Utils.dispose();
    }
}
