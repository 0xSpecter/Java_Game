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

public class Ui {
    public Groups groups = new Groups();

    // renderers
    ShapeRenderer uiRenderer = new ShapeRenderer();

    // viewports and cameras
    public OrthographicCamera uiCamera;
    public Viewport uiViewport;

    public Ui(float worldWidth, float worldHeight) {
        this.uiCamera = new OrthographicCamera();

        this.uiCamera.zoom = 0.2f;
        this.uiCamera.update();

        // :TODO: what?
        this.uiViewport = new FillViewport(worldWidth, worldHeight, this.uiCamera);
        this.uiViewport.apply();
    }

    public void update() {
        Input.updateMouseUiPosition(this.uiViewport);
        this.uiCamera.update();
    }

    // draws collision shapes of all objects
    public void drawCollisionShapes() {
        this.uiRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.uiRenderer.setColor(0f, 1f, 0f, 1f);
        this.uiRenderer.circle(Input.mouseUiPosition.x, Input.mouseUiPosition.y, 5);
        this.uiRenderer.end();
    }

    public void draw() {

    }

    public void resize(int width, int height) {
        this.uiViewport.update(width, height, true);
        this.uiViewport.apply();
    }

    public void dispose() {
        this.uiRenderer.dispose();
    }
}
