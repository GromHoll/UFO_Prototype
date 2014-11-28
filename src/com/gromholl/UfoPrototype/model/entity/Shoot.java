package com.gromholl.UfoPrototype.model.entity;

import com.badlogic.gdx.math.Vector2;
import org.andengine.extension.physics.box2d.util.Vector2Pool;

/**
 * Created by GromHoll on 03.03.14.
 */
public class Shoot extends MovableEntity {

    public static final float DEFAULT_SPEED = 300;
    private float speed = DEFAULT_SPEED;

    public Shoot(float x, float y) {
        super(x, y);
    }

    @Override
    public void move(float secondsElapsed) {
        if (!isStoped()) {
            Vector2 newPosition = Vector2Pool.obtain(position);
            Vector2 direction = Vector2Pool.obtain(targetPosition);

            direction = direction.sub(position).nor().mul(speed * secondsElapsed);
            newPosition.add(direction);

            if (targetPosition.dst(newPosition) >= targetPosition.dst(position)) {
                newPosition.set(targetPosition);
            }
            position.set(newPosition);

            Vector2Pool.recycle(newPosition);
            Vector2Pool.recycle(direction);
        }
    }


}
