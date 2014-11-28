package com.gromholl.UfoPrototype;

import com.badlogic.gdx.math.Vector2;
import com.gromholl.UfoPrototype.model.Level;
import com.gromholl.UfoPrototype.model.entity.Entity;
import com.gromholl.UfoPrototype.model.entity.EntityState;
import com.gromholl.UfoPrototype.model.entity.Shoot;
import com.gromholl.UfoPrototype.model.entity.Ufo;
import com.gromholl.UfoPrototype.view.Art;
import com.gromholl.UfoPrototype.view.Sounds;
import com.gromholl.UfoPrototype.view.containers.SpriteContainer;
import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.util.LinkedList;
import java.util.List;

public class UfoGameActivity extends SimpleBaseGameActivity implements IUpdateHandler, IOnSceneTouchListener {
    public static final int DEFAULT_FPS = 60;
    private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;

    private Scene scene;

    private final LinkedList<Vector2> moveTouchList = new LinkedList<Vector2>();
    private volatile boolean isShoot = false;

    private Art art;
    private Sounds sounds;
    private ButtonSprite button;
    private List<SpriteContainer> spriteContainers;

    private Level level;
    private Ufo ufo;

    @Override
    public EngineOptions onCreateEngineOptions() {
        Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), camera);
        options.getAudioOptions().setNeedsSound(true);
        options.getAudioOptions().setNeedsMusic(true);
        return options;
    }

    @Override
    public Engine onCreateEngine(EngineOptions engineOptions) {
        return new LimitedFPSEngine(engineOptions, DEFAULT_FPS);
    }

    @Override
    protected void onCreateResources() {
        art = new Art(this);
        sounds = new Sounds(this);
        spriteContainers = new LinkedList<SpriteContainer>();
        level = new Level();
        ufo = level.getUfo();
        button = new ButtonSprite(0, CAMERA_HEIGHT - art.getButton().getHeight() - 100, art.getButton(), getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent event, float touchAreaLocalX, float touchAreaLocalY) {
                if(event.isActionDown()) {
                    isShoot = true;
                }
                return super.onAreaTouched(event, touchAreaLocalX, touchAreaLocalY);
            }
        };
    }

    @Override
    protected Scene onCreateScene() {
        scene = new Scene();
        scene.setBackground(new SpriteBackground(new Sprite(0, 0, art.getBackground(), getVertexBufferObjectManager())));
        scene.registerUpdateHandler(this);
        scene.setOnSceneTouchListener(this);
        scene.attachChild(button);
        scene.registerTouchArea(button);
        return scene;
    }

    @Override
    public void onUpdate(float secondsElapsed) {
        onInputUpdate();
        onModelUpdate(secondsElapsed);
        onViewUpdate(secondsElapsed);
    }

    @Override
    public synchronized void onGameCreated() {
        sounds.getMusic().play();
        super.onGameCreated();
    }

    @Override
    public synchronized void onResumeGame() {
        if (sounds != null) {
            sounds.getMusic().resume();
        }
        super.onResumeGame();
    }

    @Override
    protected void onPause() {
        if (sounds != null) {
            sounds.getMusic().pause();
        }
        super.onPause();
    }

    private void onInputUpdate() {
        synchronized (moveTouchList) {
            if (!moveTouchList.isEmpty()) {
                ufo.setTargetPosition(moveTouchList.getLast());
            }
            for (Vector2 touch : moveTouchList) {
                Vector2Pool.recycle(touch);
            }
            moveTouchList.clear();
        }
        if (isShoot) {
            level.makeShoot();
            isShoot = false;
        }
    }

    private void onModelUpdate(float secondsElapsed) {
        level.update(secondsElapsed);
    }

    private void onViewUpdate(float secondsElapsed) {
        processUfo();
        processShoots();

        List<SpriteContainer> containerToRemove = new LinkedList<SpriteContainer>();
        for (SpriteContainer container : spriteContainers) {
            if (container.getState() == EntityState.DESTROYED) {
                containerToRemove.add(container);
            } else {
                container.update(secondsElapsed);
            }
        }
        spriteContainers.removeAll(containerToRemove);
    }

    private void processUfo() {
        if (ufo.getState() == EntityState.NEW) {
            SpriteContainer container = createAnimatedSpriteContainer(ufo, art.getUfo());
            spriteContainers.add(container);
            ufo.setState(EntityState.ALIVE);
        }
    }

    private void processShoots() {
        for (Shoot shoot : level.getShoots()) {
            if (shoot.getState() == EntityState.NEW) {
                SpriteContainer container = createdSpriteContainer(shoot, art.getShoot());
                spriteContainers.add(container);
                shoot.setState(EntityState.ALIVE);
                sounds.getShot().play();
            }
        }

    }

    private SpriteContainer createAnimatedSpriteContainer(Entity entity, ITiledTextureRegion tiledTextureRegion) {
        AnimatedSprite sprite = new AnimatedSprite(entity.getPositionX(), entity.getPositionY(),
                tiledTextureRegion, getVertexBufferObjectManager());
        sprite.animate(50);
        scene.attachChild(sprite);
        return new SpriteContainer(sprite, entity);
    }

    private SpriteContainer createdSpriteContainer(Entity entity, ITextureRegion textureRegion) {
        Sprite sprite = new Sprite(entity.getPositionX(), entity.getPositionY(),
                textureRegion, getVertexBufferObjectManager());
        scene.attachChild(sprite);
        return new SpriteContainer(sprite, entity);
    }

    @Override
    public void reset() {
        /* Do Nothing */
    }

    @Override
    public boolean onSceneTouchEvent(Scene scene, TouchEvent event) {
        synchronized (moveTouchList) {
            moveTouchList.add(Vector2Pool.obtain(event.getX(), event.getY()));
        }
        return true;
    }

}