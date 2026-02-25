package delta.structures;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

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
     * This method should be called once per frame
     *
     * @param camera The camera used to render the game world.
     */
    public static void updateMouseWorldPosition(Camera camera) {
        Vector3 temp = new Vector3(mouseScreenPosition.x, mouseScreenPosition.y, 0);
        camera.unproject(temp);
        mouseWorldPosition.set(temp.x, temp.y);
    }
}
