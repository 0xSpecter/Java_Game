package delta.creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import delta.structures.*;
import delta.weapons.BasicWeapon;
import delta.shapes.*;

public class Player extends Creature {
    BasicWeapon weapon;

    public Player() {
        super(0, 0);
        this.maxSpeed = 2000;
        this.acceleration = 3000;
        this.weapon = new BasicWeapon(this);
    }

    public void update() {
        Vector2 dir = new Vector2(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            dir.x--;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            dir.x++;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            dir.y++;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            dir.y--;

        dir.nor();
        this.moveDir = dir.cpy();
        this.move();

        this.weapon.update();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (this.weapon.canFire()) {
                this.weapon.fire(this.target);
            }
        }
    }

    public void targetClosest(Groups.Group group) {
        Creature closestCreature = null;
        for (Obj obj : group) {
            Creature creature = (Creature) obj;
            if (closestCreature == null || creature.pos.dst2(this.pos) < closestCreature.pos.dst2(this.pos)) {
                closestCreature = creature;
            }
        }

        this.target = closestCreature;
    }

    @Override
    public void drawFigure(ShapeRenderer shapeRenderer) {
        super.drawFigure(shapeRenderer);
    }
}
