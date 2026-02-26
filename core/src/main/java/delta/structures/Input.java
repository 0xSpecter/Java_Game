package delta.structures;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Handles input for the game.
 */
public class Input extends InputAdapter {

    /** Mouse position in screen coordinates (top-left origin). */
    public static Vector2 mouseScreenPosition = new Vector2(0, 0);

    /**
     * Mouse position in world coordinates (matches game objects' coordinate space).
     */
    public static Vector2 mouseWorldPosition = new Vector2(0, 0);

    /**
     * Mouse position in ui coordinates (screen cords for rendering).
     */
    public static Vector2 mouseUiPosition = new Vector2(0, 0);

    /**
     * Adapter override for mouse Position
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseScreenPosition.set(screenX, screenY);
        return true;
    }

    /**
     * Updates the world mouse position based on the current screen mouse position
     * and camera.
     * <p>
     *
     * @param camera The camera used to render the game world.
     */
    public static void updateMouseWorldPosition(Viewport viewport) {
        Vector3 temp = new Vector3(mouseScreenPosition.x, mouseScreenPosition.y, 0);
        viewport.unproject(temp);
        mouseWorldPosition.set(temp.x, temp.y);
    }

    /**
     * Updates the ui mouse position based on the current screen mouse position
     * and camera.
     * <p>
     *
     * @param camera The camera used to render the game world.
     */
    public static void updateMouseUiPosition(Viewport viewport) {
        Vector3 temp = new Vector3(mouseScreenPosition.x, mouseScreenPosition.y, 0);
        viewport.unproject(temp);
        mouseUiPosition.set(temp.x, temp.y);
    }
}
