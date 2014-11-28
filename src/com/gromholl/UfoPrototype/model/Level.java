package com.gromholl.UfoPrototype.model;

import com.gromholl.UfoPrototype.model.entity.EntityState;
import com.gromholl.UfoPrototype.model.entity.Shoot;
import com.gromholl.UfoPrototype.model.entity.Ufo;
import org.andengine.util.adt.pool.Pool;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by GromHoll on 23.02.14.
 */
public class Level {

    private Ufo ufo;
    private List<Shoot> shoots;
    private Pool<Shoot> shootPool;

    public Level() {
        ufo = new Ufo(150, 150);
        shoots = new LinkedList<Shoot>();
        shootPool = new Pool<Shoot>() {
            @Override
            protected Shoot onAllocatePoolItem() {
                return new Shoot(0, 0);
            }
        };
    }

    public void update(float secondsElapsed) {
        ufo.move(secondsElapsed);

        List<Shoot> shootToRemove = new LinkedList<Shoot>();
        for (Shoot shoot : shoots) {
            shoot.move(secondsElapsed);
            if (shoot.getState() == EntityState.DESTROYED) {
                shootPool.recyclePoolItem(shoot);
                shootToRemove.add(shoot);
            } else if (shoot.isStoped()) {
                shoot.setState(EntityState.DESTROYED);
            }
        }
        shoots.removeAll(shootToRemove);
        shootToRemove.clear();
    }

    public Ufo getUfo() {
        return ufo;
    }

    public List<Shoot> getShoots() {
        return shoots;
    }

    public void makeShoot() {
        Shoot shoot = shootPool.obtainPoolItem();
        shoot.setPosition(ufo.getPositionX() + 25, ufo.getPositionY() + 10);
        shoot.setTargetPosition(ufo.getPositionX() + 25, 500);
        shoot.setState(EntityState.NEW);
        shoots.add(shoot);
    }
}
