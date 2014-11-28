package com.gromholl.UfoPrototype.view;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by GromHoll on 02.02.14.
 */
public class Art {

    private BitmapTextureAtlas atlas;

    private TiledTextureRegion ufo;
    private TextureRegion button;
    private TextureRegion background;
    private TextureRegion shoot;

    public Art(final BaseGameActivity activity) {
        atlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        background = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, activity.getAssets(), "graphics/background.png", 0, 0);
        button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, activity.getAssets(), "graphics/button.png", 800, 0);
        shoot = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, activity.getAssets(), "graphics/shoot.png", 800, 64);
        ufo = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(atlas, activity.getAssets(), "graphics/ufo_sprite.png", 0, 480, 12, 1);


        atlas.load();
    }

    public TextureRegion getBackground(){
        return background;
    }

    public TiledTextureRegion getUfo(){
        return ufo;
    }

    public TextureRegion getButton(){
        return button;
    }

    public TextureRegion getShoot() {
        return shoot;
    }
}
