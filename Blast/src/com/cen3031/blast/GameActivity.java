package com.cen3031.blast;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.widget.Toast;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 15:13:46 - 15.06.2010
 */
public class GameActivity extends SimpleBaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private ITextureRegion mFaceTextureRegion;
	private ITextureRegion mFaceTextureRegion2;
	private RepeatingSpriteBackground mGrassBackground;
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		//Toast.makeText(this, "Touch & Drag the face!", Toast.LENGTH_LONG).show();

		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "tank.png", 0, 0);
		this.mFaceTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "tank.png", 0, 0);
		this.mGrassBackground = new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, this.getTextureManager(), AssetBitmapTextureAtlasSource.create(this.getAssets(), "gfx/background_grass.png"), this.getVertexBufferObjectManager());
		this.mBitmapTextureAtlas.load();
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		scene.setBackground(this.mGrassBackground);

		final float centerX = (CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2;
		final float centerY = (CAMERA_HEIGHT - this.mFaceTextureRegion.getHeight()) / 2;
		
		final Sprite tankBottom = new Sprite(centerX , CAMERA_HEIGHT/2 + 100, this.mFaceTextureRegion, this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				float posY = pSceneTouchEvent.getY() - this.getHeight() / 2;
				if  (posY < (CAMERA_HEIGHT / 2) + 1.6*this.getHeight()) {
					posY = (CAMERA_HEIGHT / 2 + (float)1.6*this.getHeight());
				}
				this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, posY);
				return true;
			}
		};
		
		final Sprite tankTop = new Sprite(centerX, CAMERA_HEIGHT/2 - 100, this.mFaceTextureRegion2, this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				float posY = pSceneTouchEvent.getY() - this.getHeight() / 2;
				if  (posY > CAMERA_HEIGHT / 2 - 1.6*this.getHeight()) {
					posY = CAMERA_HEIGHT / 2 - (float)1.6*this.getHeight();
				}
				this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, posY);
				return true;
			}
		};
		tankBottom.setScale(2);
		scene.attachChild(tankBottom);
		scene.registerTouchArea(tankBottom);
		
		tankTop.setScale(2);
		scene.attachChild(tankTop);
		scene.registerTouchArea(tankTop);
		tankTop.setRotation(180);
		
		scene.setTouchAreaBindingOnActionDownEnabled(true);

		return scene;  
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}