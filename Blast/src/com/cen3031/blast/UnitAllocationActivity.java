package com.cen3031.blast;

import java.util.LinkedList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
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
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Looper;
import android.util.DisplayMetrics;
<<<<<<< HEAD
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
=======
import android.util.Log;
>>>>>>> 916d227f72400b8fac790d461ea46cd8d48da265

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
	private TextureRegion mBarricadeTextureRegion;
	private TextureRegion mFaceTextureRegion;
<<<<<<< HEAD
	private TextureRegion mFaceTextureRegion2;
	private TextureRegion mCircleTextureRegion;
	private TextureRegion mExplosionTextureRegion;
=======
	private TextureRegion explosion;
>>>>>>> 916d227f72400b8fac790d461ea46cd8d48da265
	private Sprite barricade;
	private RepeatingSpriteBackground mGrassBackground;
	
	Tank tank;
	float touchX;
	float touchY;
	static Scene scene;
	static LinkedList<Tank> tankList = new LinkedList<Tank>();
<<<<<<< HEAD
	static LinkedList<Tank> tankList2 = new LinkedList<Tank>();
	static LinkedList<Sprite> circleList = new LinkedList<Sprite>();
	private static final int MAX_TANKS = 5;
	static boolean player1 ;
	boolean turn1mes;
	boolean turn2mes;
	boolean fire;
	boolean canFire;
	boolean move;
	static boolean gameStart;
	
=======
	static final int MAX_TANKS = 5;
>>>>>>> 916d227f72400b8fac790d461ea46cd8d48da265


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
<<<<<<< HEAD
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),32, 32, TextureOptions.BILINEAR);
		this.mBitmapTextureAtlas2 = new BitmapTextureAtlas(this.getTextureManager(),1024, 1024, TextureOptions.BILINEAR);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "tank.png", 0, 0);
		this.mFaceTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "barricade.png", 0, 0);
		this.mExplosionTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "explosion.png", 0, 50);
		this.mCircleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "small_circle.png", 0, 50);
=======
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),512, 512, TextureOptions.BILINEAR);
		this.mBitmapTextureAtlas2 = new BitmapTextureAtlas(this.getTextureManager(),1024, 512, TextureOptions.BILINEAR);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "tank.png", 0, 0);
		this.mBarricadeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "barricade.png", 0, 10);
		this.explosion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "explosion.png", 25, 0);
>>>>>>> 916d227f72400b8fac790d461ea46cd8d48da265
		this.mGrassBackground = new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, this.getTextureManager(), AssetBitmapTextureAtlasSource.create(this.getAssets(), "gfx/background_grass.png"), this.getVertexBufferObjectManager());
		this.getEngine().getTextureManager().loadTexture(mBitmapTextureAtlas);
		this.getEngine().getTextureManager().loadTexture(mBitmapTextureAtlas2);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		tankList.clear();
		tankList2.clear();
	}
	@Override
	public Scene onCreateScene() {
		
		
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		scene = new Scene();
		scene.setBackground(this.mGrassBackground);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.setOnSceneTouchListener(this);
		
		final float centerX = (CAMERA_WIDTH - this.mBarricadeTextureRegion.getWidth()) / 2;
        final float centerY = (CAMERA_HEIGHT - this.mBarricadeTextureRegion.getHeight()) / 2;

        this.barricade = new Sprite(centerX, centerY, this.mBarricadeTextureRegion, this.mEngine.getVertexBufferObjectManager());
        scene.attachChild(this.barricade);
        
        player1 = true;
    	gameStart = false;
    	


		this.gameToast("Tap screen to place Units");
		return scene;
		}
	 
	//Toast Messages in onTouch events
	 public void gameToast(final String msg) {
		    this.runOnUiThread(new Runnable() {
		        @Override
		        public void run() {
		           Toast.makeText(UnitAllocationActivity.this, msg, Toast.LENGTH_SHORT).show();
		        }
		    });
		}
	 
	 public void gameDialog(final int id) {
		    this.runOnUiThread(new Runnable() {
		        @Override
		        public void run() {
		        	switch (id) {
		        	  case 1:
		        		  AlertDialog.Builder alert = new AlertDialog.Builder(UnitAllocationActivity.this);                 
		        		  alert.setTitle("Player1 Submit Side");  
		        		  alert.setMessage("Are you finished setting your side?");                
		        		  alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
		        			  public void onClick(DialogInterface dialog, int whichButton) {  		        			
		        				  gameToast("Player2 Turn to place Units");
		        				  return;                  
		        			  }  
		        		  });  

		        		  alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		        			  public void onClick(DialogInterface dialog, int which) {
		     	        	 	player1 = true;
		     	        	 	return;   
		     	         }
		     	     });
		     	             alert.show(); 
		     	             break;
		        	  case 2: 
		        		  AlertDialog.Builder alert2 = new AlertDialog.Builder(UnitAllocationActivity.this);                 
		        		  alert2.setTitle("Player2 Submit Side");  
		        		  alert2.setMessage("Are you finished setting your side?");                
		        		  alert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
		        			  public void onClick(DialogInterface dialog, int whichButton) {  
		        				  gameStart = true;
		        				  player1 = true;
		        				  turn1mes = false;
		        				  gameToast("Game Started!");
		        				  gameToast("Player1 turn");
		        				  gameDialog(3);
		        				  return;                  
		        			  }  
		        		  });  

		        		  alert2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		        			  public void onClick(DialogInterface dialog, int which) {
		        				gameStart = false;
		     	        	 	return;   
		     	         }
		     	     });
		     	             alert2.show(); 
		     	             break;
		        	  case 3:
		        		  AlertDialog.Builder alert3 = new AlertDialog.Builder(UnitAllocationActivity.this);                 
		        		  alert3.setMessage("Would You Like to Fire or Move Units");                
		        		  alert3.setPositiveButton("FIRE", new DialogInterface.OnClickListener() {  
		        			  public void onClick(DialogInterface dialog, int whichButton) {  
		        				      fire = true;
		        				      move = false;
		        			  }  
		        		  });  

		        		  alert3.setNegativeButton("MOVE", new DialogInterface.OnClickListener() {
		        			  public void onClick(DialogInterface dialog, int which) {
		        				  fire = false;
	        				      move = true;
		     	        	 	return;   
		     	         }
		     	     });
		        		  alert3.show(); 
		        		  break;	
		     	   }
		        	
		        }
		    });
		}

	@Override
	public boolean onSceneTouchEvent(final Scene pScene, TouchEvent pSceneTouchEvent) {
<<<<<<< HEAD
		
		//Max tanks = 5
		//PLAYER1 SET UP SIDE
		if(player1 && !gameStart){
			//Checks each touch if the tank has been removed from the screen
			//Removes it from Linked List
			checkRemove(tankList);
			//Checks if any other tanks are selected 
			//If they are deselect them
			checkOthersSelected(tankList);
			if(tankList.size()<MAX_TANKS){
				if (pSceneTouchEvent.isActionDown()) {
					touchX = pSceneTouchEvent.getX();
					touchY = pSceneTouchEvent.getY();
=======
		//Checks each touch if the tank has been removed from the screen
		//Removes it from Linked List
		for(int i = 0; i < tankList.size(); i++){
			    if(tankList.get(i).removed){
			    	tankList.remove(i);
			    }
		   }
		//Checks if any other tanks are selected 
		//If they are deselect them
		//checkOthersSelected();
		
		//Max tanks = 5
		if(tankList.size()<MAX_TANKS){
	    checkOthersSelected();
		boolean crash = false;
		if (pSceneTouchEvent.isActionDown()) {
			touchX = pSceneTouchEvent.getX();
	        touchY = pSceneTouchEvent.getY();
>>>>>>> 916d227f72400b8fac790d461ea46cd8d48da265
	      
					tank = new Tank(touchX ,touchY,50,50, this.mFaceTextureRegion, this.getVertexBufferObjectManager());

					float posY = touchY - tank.getHeight() / 2;
		   
					if(posY >= (CAMERA_HEIGHT / 2) + 1.6*tank.getHeight()){
						tankList.add(tank);
						scene.registerTouchArea(tank); // register touch area , so this allows you to drag it
						scene.attachChild(tank); //add it to the scene
					}
				return false;
				} 
			}else {
				player1 = false;
				gameDialog(1);
			}
		}
<<<<<<< HEAD
		//PLAYER2 SET UP SIDE
		else if(!player1 && !gameStart){
=======
		}else{
			if (pSceneTouchEvent.isActionDown()) {
				touchX = pSceneTouchEvent.getX();
		        touchY = pSceneTouchEvent.getY();
			}
			reflect(touchX,touchY);
			checkOthersSelected();
>>>>>>> 916d227f72400b8fac790d461ea46cd8d48da265
			// CREATE POP UP DIALOG TO SUBMIT
			//Player 2 turn
			if(tankList2.size()<MAX_TANKS){
				//Checks each touch if the tank has been removed from the screen
				//Removes it from Linked List
				checkRemove(tankList2);
				//Checks if any other tanks are selected 
				//If they are deselect them
				checkOthersSelected(tankList2);
				if (pSceneTouchEvent.isActionDown()) {
					touchX = pSceneTouchEvent.getX();
					touchY = pSceneTouchEvent.getY();
	      
					tank = new Tank(touchX ,touchY,50,50, this.mFaceTextureRegion, this.getVertexBufferObjectManager());

					float posY = touchY - tank.getHeight() / 2;
		   
					if(posY <= (CAMERA_HEIGHT / 2) + 1.6*tank.getHeight() ){
						tankList2.add(tank);
						tank.setRotation(180);
						scene.registerTouchArea(tank); // register touch area , so this allows you to drag it
						scene.attachChild(tank); //add it to the scene
						
					}
				return false;
				} 
			}else{
				gameStart = true;
				gameDialog(2);
			}
		}else{
			//Game Started Player 1 turn
			if(player1){
				if(turn1mes){
					gameToast("Player1 turn");
					turn1mes = false;
					turn2mes = true;
					gameDialog(3);
				}
				//User selects fire on dialog
				if(fire){
					canFire = false;
					for(int i = 0;i < tankList.size();i++){
						if(tankList.get(i).isSelected){
							tankList.get(i).isSelected = false;
							tankList.get(i).outline.setVisible(false);
							canFire = true;
						}
					}
					if(canFire){
						//Makes sure a tank is selected to fire
						
					if (pSceneTouchEvent.isActionDown()) {
						touchX = pSceneTouchEvent.getX();
						touchY = pSceneTouchEvent.getY();
						float posY = touchY - tank.getHeight() / 2;
						   
						if(posY >= (CAMERA_HEIGHT / 2)){
							Sprite circle = new Sprite(touchX, touchY, this.mCircleTextureRegion, this.mEngine.getVertexBufferObjectManager());
							scene.attachChild(circle);
							Sprite circle2 = new Sprite(touchX,CAMERA_HEIGHT-touchY, this.mCircleTextureRegion, this.mEngine.getVertexBufferObjectManager());
							scene.attachChild(circle2);
							for(int i = 0; i < UnitAllocationActivity.tankList2.size();i++){  
								if( circle2.collidesWith(tankList2.get(i)) ){
									scene.detachChild(tankList2.get(i));
									scene.detachChild(circle2);
									tankList2.remove(i);
									Sprite explosion = new Sprite(touchX,CAMERA_HEIGHT-touchY, this.mExplosionTextureRegion, this.mEngine.getVertexBufferObjectManager());
									scene.attachChild(explosion);
								}
							}
							if(player1win()){
								turn2mes = false;
								gameToast("Player1 WINS!!");
							}else{
								turn2mes = true;
							}
							registerItems(tankList2);
							player1 = false;
							fire = false;
						}else{
							registerItems(tankList);
						}
					}
					}
				}
				//IF USER PICKS MOVE
				else if(move){
					
					move = false;
				}
			}else{
				if(turn2mes){
					gameToast("Player2 turn");
					turn2mes = false;
					gameDialog(3);
				}
					
					//IF USER SELECTS FIRE
					if(fire){
						canFire = false;
						//Makes sure a tank is selected to fire
						for(int i = 0;i < tankList2.size();i++){
							if(tankList2.get(i).isSelected){
								tankList2.get(i).isSelected = false;
								tankList2.get(i).outline.setVisible(false);
								canFire = true;
							}
						}
						if(canFire){
						
						if (pSceneTouchEvent.isActionDown()) {
							touchX = pSceneTouchEvent.getX();
							touchY = pSceneTouchEvent.getY();
							float posY = touchY - tank.getHeight() / 2;  
							if(posY <= (CAMERA_HEIGHT / 2)){
								Sprite circle = new Sprite(touchX, touchY, this.mCircleTextureRegion, this.mEngine.getVertexBufferObjectManager());
								scene.attachChild(circle);
								Sprite circle2 = new Sprite(touchX,CAMERA_HEIGHT-touchY, this.mCircleTextureRegion, this.mEngine.getVertexBufferObjectManager());
								scene.attachChild(circle2);
								for(int i = 0; i < UnitAllocationActivity.tankList.size();i++){  
									if( circle2.collidesWith(tankList.get(i)) ){
										scene.detachChild(tankList.get(i));
										scene.detachChild(circle2);
										tankList.remove(i);
										Sprite explosion = new Sprite(touchX,CAMERA_HEIGHT-touchY, this.mExplosionTextureRegion, this.mEngine.getVertexBufferObjectManager());
										scene.attachChild(explosion);
									}
								}
								if(player1win()){
									turn1mes = false;
									gameToast("Player2 WINS!!");
								}else{
									turn1mes = true;
								}
								registerItems(tankList);
								player1 = true;
								fire = false;
							}else{
								registerItems(tankList2);
							}
						}
					}
					}
					//IF USER SELECTS MOVE
					else if(move){
						
						move = false;
					}
			
				}
			
		}
		
		if (pSceneTouchEvent.isActionUp()) {
			
			return false;
		}
		
		if (pSceneTouchEvent.isActionMove()) {
			
			return false;
		}
		
		
		
		
		
		return false;
	}
	
	@Override
	protected void onPause() {
	        if (mEngine.isRunning()) {
	        	mEngine.stop();
	        }
	    super.onPause();
	}
	
	@Override
	public void onResumeGame() {
		super.onResumeGame();
		//mEngine.start(); //apparently it automatically starts it anyway.
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		tankList.clear(); //clear list
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public static void checkOthersSelected(LinkedList<Tank> list){
		 for(int i = 0; i < list.size(); i++){
			    if(list.get(i).isSelected){
			    	list.get(i).isSelected = false;
			    	list.get(i).outline.setVisible(false);
			    }
		   }
	}
	
<<<<<<< HEAD
	public static void checkRemove(LinkedList<Tank> list){
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).removed){
				list.remove(i);
			}
		}
	}
	
	public static void unregisterItems(LinkedList<Tank> list){
		for(int i = 0; i < list.size(); i++){
				scene.unregisterTouchArea(list.get(i));
			
		}
	}
	
	public static void registerItems(LinkedList<Tank> list){
		for(int i = 0; i < list.size(); i++){
				scene.registerTouchArea(list.get(i));
			
		}
	}
	
	public boolean player1win(){
		if(tankList2.isEmpty()){
			return true;
		}
		return false;
	}
}
/*****************************************************
* Tank Class when selected is highlighted by
* a rectangle object
******************************************************/
class Tank extends Sprite {
	float x,y;
	boolean removed,isSelected;
	boolean fire,move;
	Rectangle outline;
	public Tank(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion mFaceTextureRegion, VertexBufferObjectManager  vbom) {
		super(pX, pY, pWidth, pHeight, mFaceTextureRegion, vbom);
		x = pX;
		y = pY;
		removed = false;
		fire = true;
		move = true;
		outline = new Rectangle (pX-2,pY-2,pWidth+4,pHeight+4, vbom);
		outline.setVisible(false);
		outline.setColor(1,1,0);
		UnitAllocationActivity.scene.attachChild(outline);
=======
	public void reflect(float fireXPos, float fireYPos){
		//check if a tank is selected
		Tank aTankSelected = null;
		for(int i = 0; i < tankList.size(); i++){
		    if(tankList.get(i).isSelected){
		    	aTankSelected = tankList.get(i);
		    }
	    }
		//tank selected
		if((aTankSelected != null) && (aTankSelected.isSelected == true)){
			Log.d("DEBUG","Reflect fired!" + fireXPos + " " + fireYPos);
			float refYPos = (CAMERA_HEIGHT/2) - (fireYPos - (CAMERA_HEIGHT/2));
			Sprite explo = new Sprite(fireXPos, refYPos, this.explosion, this.mEngine.getVertexBufferObjectManager());
			scene.attachChild(explo);
		}
>>>>>>> 916d227f72400b8fac790d461ea46cd8d48da265
	}
	class Tank extends Sprite {
		float originX, originY;
		boolean removed,isSelected, validMove;
		Rectangle outline;
		public Tank(float pX, float pY, float pWidth, float pHeight,
				ITextureRegion mFaceTextureRegion, VertexBufferObjectManager  vbom) {
			super(pX, pY, pWidth, pHeight, mFaceTextureRegion, vbom);
			originX = pX;
			originY = pY;
			removed = false;
			validMove = false;
			outline = new Rectangle (pX-2,pY-2,pWidth+4,pHeight+4, vbom);
			outline.setVisible(false);
			outline.setColor(1,1,0);
			UnitAllocationActivity.scene.attachChild(outline);
		}

<<<<<<< HEAD
	public Tank(float centerX, int i, ITextureRegion mFaceTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager) {
		super(centerX,i,mFaceTextureRegion,vertexBufferObjectManager);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) { 
         
		if(pSceneTouchEvent.isActionDown()){
			 //Selects the Tank and highlights it
			 if(fire){
				// if(isSelected){
					 //this.removed = true;
					 //UnitAllocationActivity.scene.detachChild(outline);
					 //this.detachSelf();
			//	 }else{
					 if(UnitAllocationActivity.player1){
						 UnitAllocationActivity.checkOthersSelected(UnitAllocationActivity.tankList);
					 }else{
						 UnitAllocationActivity.checkOthersSelected(UnitAllocationActivity.tankList2);
					 }	 
					 isSelected = true;
					 outline.setVisible(true);
					 if(UnitAllocationActivity.gameStart){
						 UnitAllocationActivity.unregisterItems(UnitAllocationActivity.tankList);
						 UnitAllocationActivity.unregisterItems(UnitAllocationActivity.tankList2);
					 }
			 // }	  
				  
			  }else{
=======
		public Tank(float centerX, int i, ITextureRegion mFaceTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager) {
			super(centerX,i,mFaceTextureRegion,vertexBufferObjectManager);
			// TODO Auto-generated constructor stub
		}
		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			float posY = pSceneTouchEvent.getY() - this.getHeight() / 2;
	        float posX = pSceneTouchEvent.getX() - this.getWidth() / 2;
			  
			  if(pSceneTouchEvent.isActionDown()){
				  this.originX = posX;
			      this.originY = posY;
			      validMove = true;
				  
				  //Selects the Tank and highlights it
				  if(isSelected){
					  this.removed = true;
					  scene.detachChild(outline);
					  this.detachSelf();
					  
				  }else{
					  UnitAllocationActivity.checkOthersSelected();
					  isSelected = true;
					  outline.setVisible(true);
				  }
>>>>>>> 916d227f72400b8fac790d461ea46cd8d48da265
			  }
			  else if (pSceneTouchEvent.isActionMove()) {
				  if (validMove) {
					  for (int i = 0; i < tankList.size(); i++) {
						  if (this != tankList.get(i)) {
							  if (this.collidesWith(tankList.get(i))) {
								  this.setPosition(this.originX, this.originY);
								  this.outline.setPosition(this.originX - 2, this.originY - 2);
								  validMove = false;
								  return true;
							  }
						  }
					  }
					  
					  if  (posY < ( (CAMERA_HEIGHT / 2) + (mBarricadeTextureRegion.getHeight() / 2))) {
					      posY = (CAMERA_HEIGHT / 2) + (mBarricadeTextureRegion.getHeight() / 2);
					  }

				      this.setPosition(posX, posY);
				      
				      outline.setPosition(posX - 2, posY - 2);
				  }
				  else {
					  return true;
				  }
			  }
			  else if (pSceneTouchEvent.isActionUp()) {
				  if (validMove) {
					  this.originX = posX;
				      this.originY = posY;
				  }
			  }
			  
	          return true;
	        }
	};	

<<<<<<< HEAD
//scene.registerUpdateHandler(new IUpdateHandler() {
//@Override
//public void reset() { }
//
//@Override
//public void onUpdate(final float pSecondsElapsed) {
//for(int i = 0; i < tankList.size();i++){  
//	
//	for(int j = 0; j < tankList.size(); j++){
//     if (i!=j){	
//		if(tankList.get(j).collidesWith(tankList.get(i))) {
//  		tankList.get(j).setX(tankList.get(j).x);
//          tankList.get(j).setY(tankList.get(j).y);
//          
//      }else{
//      	tankList.get(j).x = tankList.get(j).getX();
//          tankList.get(j).y = tankList.get(j).getY();
//          tankList.get(j).setX(tankList.get(j).x);
//          tankList.get(j).setY(tankList.get(j).y);
//      }
//     }
//  }
//}
//}
//});
//this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
//for(int i = 0; i < UnitAllocationActivity.tankList.size();i++){  
//	 if(this.x != UnitAllocationActivity.tankList.get(i).x && this.y != UnitAllocationActivity.tankList.get(i).y ){
//	  if( this.collidesWith(UnitAllocationActivity.tankList.get(i)) ){
//		 this.setX(this.x);
//     this.setY(this.y);
//     
//	 }else{
//		this.x = this.getX();
//     this.y = this.getY();
//     this.setX(this.x);
//     this.setY(this.y);
//	 }
// }
//}
=======
}
>>>>>>> 916d227f72400b8fac790d461ea46cd8d48da265
