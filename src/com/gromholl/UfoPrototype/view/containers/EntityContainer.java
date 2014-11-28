package com.gromholl.UfoPrototype.view.containers;

import com.gromholl.UfoPrototype.model.entity.EntityState;

/**
 * Created by GromHoll on 03.03.14.
 */
public interface EntityContainer {
    public void update(float secondsElapsed);
    public EntityState getState();
}
