package delta.structures;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class Input extends InputAdapter {
    public static Vector2 mousePosition = new Vector2(0, 0);

    public Input() {

    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Input.mousePosition.x = screenX;
        Input.mousePosition.y = screenY;
        return true;
    }
}
