package com.gromholl.UfoPrototype.view.containers;

import com.gromholl.UfoPrototype.model.entity.Entity;
import com.gromholl.UfoPrototype.model.entity.EntityState;
import org.andengine.entity.sprite.Sprite;

/**
 * Created by GromHoll on 03.03.14.
 */
public class SpriteContainer implements EntityContainer {

    private Sprite sprite;
    private Entity entity;

    public SpriteContainer(Sprite sprite, Entity entity) {
        this.sprite = sprite;
        this.entity = entity;
    }

    @Override
    public void update(float secondsElapsed) {
        sprite.setPosition(entity.getPositionX(), entity.getPositionY());
    }

    @Override
    public EntityState getState() {
        return entity.getState();
    }
}
