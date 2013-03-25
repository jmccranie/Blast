package com.cen3031.blast;

import java.util.LinkedList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
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
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.util.DisplayMetrics;

public class UnitAllocationActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener {
	// ===========================================================
	// Constants
	// ===========================================================

	private static int CAMERA_WIDTH;
	private static int CAMERA_HEIGHT;

	// ===========================================================
	// Fields
	// ===========================================================

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private BitmapTextureAtlas mBitmapTextureAtlas2;
	private TextureRegion mFaceTextureRegion;
	private TextureRegion mFaceTextureRegion2;
	private Sprite barricade;
	private RepeatingSpriteBackground mGrassBackground;
	
	Tank tank;
	float touchX;
	float touchY;
	static Scene scene;
	static LinkedList<Tank> tankList = new LinkedList<Tank>();
	private static final int MAX_TANKS = 5;


	@Override
	public EngineOptions onCreateEngineOptions() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		CAMERA_WIDTH = metrics.widthPixels;
		CAMERA_HEIGHT = metrics.heightPixels;

		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),32, 32, TextureOptions.BILINEAR);
		this.mBitmapTextureAtlas2 = new BitmapTextureAtlas(this.getTextureManager(),1024, 512, TextureOptions.BILINEAR);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "tank.png", 0, 0);
		this.mFaceTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "barricade.png", 0, 10);
		this.mGrassBackground = new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, this.getTextureManager(), AssetBitmapTextureAtlasSource.create(this.getAssets(), "gfx/background_grass.png"), this.getVertexBufferObjectManager());
		this.getEngine().getTextureManager().loadTexture(mBitmapTextureAtlas);
		this.getEngine().getTextureManager().loadTexture(mBitmapTextureAtlas2);
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
	
		scene = new Scene();
		scene.setBackground(this.mGrassBackground);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.setOnSceneTouchListener(this);
		
		final float centerX = (CAMERA_WIDTH - this.mFaceTextureRegion2.getWidth()) / 2;
        final float centerY = (CAMERA_HEIGHT - this.mFaceTextureRegion2.getHeight()) / 2;

        this.barricade = new Sprite(centerX, centerY, this.mFaceTextureRegion2, this.mEngine.getVertexBufferObjectManager());
        scene.attachChild(this.barricade);
		
		return scene;
		}
		

	@Override
	public boolean onSceneTouchEvent(final Scene pScene, TouchEvent pSceneTouchEvent) {
		//Checks each touch if the tank has been removed from the screen
		//Removes it from Linked List
		for(int i = 0; i < tankList.size(); i++){
			    if(tankList.get(i).removed){
			    	tankList.remove(i);
			    }
		   }
		
		//Checks if any other tanks are selected 
		//If they are deselect them
		checkOthersSelected();
		
		//Max tanks = 5
		if(tankList.size()<MAX_TANKS){
		boolean crash = false;
		if (pSceneTouchEvent.isActionDown()) {
			touchX = pSceneTouchEvent.getX();
	        touchY = pSceneTouchEvent.getY();
	      
	    tank = new Tank(touchX ,touchY,50,50, this.mFaceTextureRegion, this.getVertexBufferObjectManager());

		    float posY = touchY - tank.getHeight() / 2;
		   
		   if(posY >= (CAMERA_HEIGHT / 2) + 1.6*tank.getHeight() && tankList.size() < MAX_TANKS && !crash){
			   tankList.add(tank);
			   scene.registerTouchArea(tank); // register touch area , so this allows you to drag it
			   scene.attachChild(tank); //add it to the scene
		   }
		   return false;
		}
		}else{
			// CREATE POP UP DIALOG TO SUBMIT
		}
		
		if (pSceneTouchEvent.isActionUp()) {
			
			return false;
		}
		
		if (pSceneTouchEvent.isActionMove()) {
			
			return false;
		}
		
		
		
		
		
		return false;
	}


	// ===========================================================
	// Methods
	// ===========================================================
	public static void checkOthersSelected(){
		 for(int i = 0; i < tankList.size(); i++){
			    if(tankList.get(i).isSelected){
			    	tankList.get(i).isSelected = false;
			    	tankList.get(i).outline.setVisible(false);
			    }
		   }
	}

}


class Tank extends Sprite {
	float x,y;
	boolean removed,isSelected;
	Rectangle outline;
	public Tank(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion mFaceTextureRegion, VertexBufferObjectManager  vbom) {
		super(pX, pY, pWidth, pHeight, mFaceTextureRegion, vbom);
		x = pX;
		y = pY;
		removed = false;
		outline = new Rectangle (pX-2,pY-2,pWidth+4,pHeight+4, vbom);
		outline.setVisible(false);
		outline.setColor(1,1,0);
		UnitAllocationActivity.scene.attachChild(outline);
	}

	public Tank(float centerX, int i, ITextureRegion mFaceTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager) {
		super(centerX,i,mFaceTextureRegion,vertexBufferObjectManager);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) { 
		  if(pSceneTouchEvent.isActionDown()){
			 //Selects the Tank and highlights it
			  if(isSelected){
				  this.removed = true;
				  UnitAllocationActivity.scene.detachChild(outline);
				  this.detachSelf();
				  
			  }else{
				  UnitAllocationActivity.checkOthersSelected();
				  isSelected = true;
				  outline.setVisible(true);
			  }
		  }	
          return true;
        }
	  
	
};

