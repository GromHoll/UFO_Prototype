package com.gromholl.UfoPrototype.model.entity;

import com.badlogic.gdx.math.Vector2;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.util.adt.pool.PoolItem;

/**
 * Created by GromHoll on 23.02.14.
 */
public abstract class Entity extends PoolItem {

    protected EntityState state;
    protected Vector2 position;

    public Entity(float x, float y) {
        position = Vector2Pool.obtain(x, y);
        state = EntityState.NEW;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public float getPositionX() {
        return position.x;
    }

    public float getPositionY() {
        return position.y;
    }

    public EntityState getState() {
        return state;
    }

    public void setState(EntityState state) {
        this.state = state;
    }
}
