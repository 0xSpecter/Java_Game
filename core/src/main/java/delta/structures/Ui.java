package delta.structures;

import java.util.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.*;

import delta.creatures.*;
import delta.shapes.*;
import delta.ui.*;

public class Ui {
    public static Ui instance;
    // renderers
    ShapeRenderer uiRenderer = new ShapeRenderer();

    // viewports and cameras
    public OrthographicCamera uiCamera;
    public Viewport uiViewport;

    public ArrayList<Element> topLevel = new ArrayList<>();

    public Ui() {
        Ui.instance = this;
        this.uiCamera = new OrthographicCamera();
        this.uiCamera.setToOrtho(true, Constants.worldWidth, Constants.worldHeight);

        this.uiCamera.zoom = 1f;
        this.uiCamera.update();

        this.uiViewport = new FitViewport(Constants.worldWidth, Constants.worldHeight, this.uiCamera);
        this.uiViewport.apply();

        this.topLevel.add(new Element(50, 50, 300, 100));
        this.topLevel.add(new Element(0, Constants.worldHeight - 50, 500, 50));
        this.topLevel.add(new Element(Constants.worldWidth - 100, 300, 50, 300));
        this.topLevel.add(new MouseElement());
        this.topLevel.add(new PlayerTarget());
    }

    public void update() {
        Input.updateMouseUiPosition(this.uiViewport);
        this.uiCamera.update();
        for (Element elm : this.topLevel) {
            elm.update();
        }
    }

    private void renderSetup() {
        this.uiViewport.apply();
        this.uiRenderer.setProjectionMatrix(this.uiCamera.combined);
    }

    // draws collision shapes of all objects
    public void drawCollisionShapes() {
        this.renderSetup();
        this.uiRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.uiRenderer.setColor(0f, 1f, 0f, 1f);
        this.uiRenderer.circle(Input.mouseUiPosition.x, Input.mouseUiPosition.y, 5);
        this.uiRenderer.end();
    }

    public void draw() {
        this.renderSetup();
        this.uiViewport.apply();
        this.uiRenderer.setProjectionMatrix(this.uiCamera.combined);
        this.uiRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.uiRenderer.setColor(0f, 0.65f, 1f, 0.75f);

        for (Element elm : this.topLevel) {
            elm.draw(this.uiRenderer, new Vector2());
        }

        this.uiRenderer.end();
    }

    public void resize(int width, int height) {
        this.uiViewport.update(width, height, true);
        this.uiViewport.apply();
    }

    public void dispose() {
        this.uiRenderer.dispose();
    }

    /**
     * returns the ui position from a world position
     */
    public Vector2 worldToUi(Vector2 pos) {
        Vector2 uiPos = World.instance.worldViewport.project(pos.cpy());
        uiPos = Ui.instance.uiViewport.unproject(uiPos);
        // flip y
        uiPos.y = Ui.instance.uiViewport.getWorldHeight() - uiPos.y;

        return uiPos;
    }
}
