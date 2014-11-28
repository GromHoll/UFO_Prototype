package com.gromholl.UfoPrototype.model.entity;

import com.badlogic.gdx.math.Vector2;
import org.andengine.extension.physics.box2d.util.Vector2Pool;

/**
 * Created by GromHoll on 23.02.14.
 */
public abstract class MovableEntity extends Entity {

    protected Vector2 targetPosition;

    public MovableEntity(float x, float y) {
        super(x, y);
        targetPosition = Vector2Pool.obtain(getPosition());
    }

    public void setTargetPosition(float x, float y) {
        targetPosition.set(x, y);
    }

    public void setTargetPosition(Vector2 target) {
        targetPosition.set(target);
    }

    public Vector2 getTargetPosition() {
        return targetPosition;
    }

    public abstract void move(float secondsElapsed);

    public boolean isStoped() {
        return position.x == targetPosition.x && position.y == targetPosition.y;
    }
}
