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
import android.content.Intent;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 15:13:46 - 15.06.2010
 */
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
	static TextureRegion mBarricadeTextureRegion;
	private TextureRegion mCircleTextureRegion;
	private TextureRegion mExplosionTextureRegion;
	private TextureRegion mPlayer1TextureRegion;
	private TextureRegion mPlayer2TextureRegion;
	private TextureRegion mMineTextureRegion;
	private Sprite barricade;
	private RepeatingSpriteBackground mGrassBackground;
	Camera camera;
	
	Tank tank;
	Sprite mine;
	float touchX; 
	float touchY;
	static Scene scene;
	static LinkedList<Tank> tankList = new LinkedList<Tank>();
	static LinkedList<Tank> tankList2 = new LinkedList<Tank>();
	static LinkedList<Sprite> circleList = new LinkedList<Sprite>();
	static LinkedList<Sprite> mineList = new LinkedList<Sprite>();
	static LinkedList<Sprite> mineList2 = new LinkedList<Sprite>();
	private static final int MAX_TANKS = 5;
	private static final int MAX_MINES= 2;
	static boolean player1 ;
	boolean turn1mes;
	boolean turn2mes;
	boolean fire;
	boolean canFire;
	boolean move;
	boolean viewMap;
	static boolean gameStart;
	


	@Override
	public EngineOptions onCreateEngineOptions() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		CAMERA_WIDTH = metrics.widthPixels;
		CAMERA_HEIGHT = metrics.heightPixels;

		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),32, 32, TextureOptions.BILINEAR);
		this.mBitmapTextureAtlas2 = new BitmapTextureAtlas(this.getTextureManager(),1024, 1024, TextureOptions.BILINEAR);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "tank.png", 0, 0);
		this.mBarricadeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "barricade.png", 0, 0);
		this.mExplosionTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "explosion.png", 0, 50);
		this.mCircleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "small_circle.png", 20, 200);
		this.mPlayer1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "player1.png", 10, 25);
		this.mPlayer2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "player2.png", 10, 100);
		this.mMineTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "mine.png", 150, 150);
		this.mGrassBackground = new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, this.getTextureManager(), AssetBitmapTextureAtlasSource.create(this.getAssets(), "gfx/background_grass.png"), this.getVertexBufferObjectManager());
		this.getEngine().getTextureManager().loadTexture(mBitmapTextureAtlas);
		this.getEngine().getTextureManager().loadTexture(mBitmapTextureAtlas2);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		tankList.clear();
		tankList2.clear();
		mineList.clear();
		mineList2.clear();
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
        final float textCenterX =(CAMERA_WIDTH - this.mPlayer1TextureRegion.getWidth()) / 2;
        
        this.barricade = new Sprite(centerX, centerY, this.mBarricadeTextureRegion, this.mEngine.getVertexBufferObjectManager());
        Sprite player1text= new Sprite(textCenterX , CAMERA_HEIGHT-30, this.mPlayer1TextureRegion, this.mEngine.getVertexBufferObjectManager());
        Sprite player2text = new Sprite(textCenterX , 10, this.mPlayer2TextureRegion, this.mEngine.getVertexBufferObjectManager());
        
        scene.attachChild(this.barricade);
        player1text.setScale(4);
        scene.attachChild(player1text);
        player2text.setScale(4);
        player2text.setRotation(180);
        scene.attachChild(player2text);
        player1 = true;
    	gameStart = false;
    	
    	gameDialog(5);
		
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
		        				  gameToast("Player2 Turn to place Tanks");
		        				  registerItems(tankList2);
		        				  camera.setRotation(180f);
		        				  return;                  
		        			  }  
		        		  });  
		        		 
		        		  alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		        			  public void onClick(DialogInterface dialog, int which) {
		     	        	 	player1 = true;
		     	        	 	registerItems(tankList);
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
		        				  registerItems(tankList);
		        				  camera.setRotation(0f);
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
		        		  alert3.setMessage("Would You Like to Fire or Move Tanks");                
		        		  alert3.setPositiveButton("FIRE", new DialogInterface.OnClickListener() {  
		        			  public void onClick(DialogInterface dialog, int whichButton) {  
		        				  fire = true;
		        				  move = false;
		        				  viewMap = false;
		        				  if(player1){
		        					  registerItems(tankList);
		        				  }else{
		        					  registerItems(tankList2);
		        				  }
		        				  return;
		        			  }  
		        		  });  
		        		  alert3.setNeutralButton("View Map", new DialogInterface.OnClickListener() {
		        			  public void onClick(DialogInterface dialog, int which) {
		        				  fire = false;
	        				      move = false;
	        				      viewMap = true;
	        				      unregisterItems(tankList);
	        				      unregisterItems(tankList2);
		        				  return;   
		        	     }
		        		  });
		        		  alert3.setNegativeButton("MOVE", new DialogInterface.OnClickListener() {
		        			  public void onClick(DialogInterface dialog, int which) {
		        				  fire = false;
	        				      move = true;
	        				      viewMap = false;
	        				      if(player1){
		        					  registerItems(tankList);
		        				  }else{
		        					  registerItems(tankList2);
		        				  }
		     	        	 	return;   
		     	         }
		     	     });
		        		  alert3.show(); 
		        		  break;
		        	
		        	  case 4:
		        		  AlertDialog.Builder alert4 = new AlertDialog.Builder(UnitAllocationActivity.this);                 
		        		  if(player1win()){
		        			  alert4.setTitle("Player1 WINS!!");
		        		  }else{
		        			  alert4.setTitle("Player2 WINS!!");
		        		  }
		        		  alert4.setMessage("Would You Like to Play Again?");                
		        		  alert4.setPositiveButton("YES", new DialogInterface.OnClickListener() {  
		        			  public void onClick(DialogInterface dialog, int whichButton) {  
		        				  Intent intent = getIntent();
		        				  finish();
		        			      startActivity(intent);    
		        			  }  
		        		  });  

		        		  alert4.setNegativeButton("NO", new DialogInterface.OnClickListener() {
		        			  public void onClick(DialogInterface dialog, int which) {
		        				  finish();
		     	        	 	return;   
		     	         }
		     	     });
		        		  alert4.show(); 
		        		  break;
		     	   
		        case 5:
	        		  AlertDialog.Builder alert5 = new AlertDialog.Builder(UnitAllocationActivity.this);                 
	        		  alert5.setTitle("Helpful Tips");
	        		 
	        		  alert5.setMessage("-Each Player Places 5 Tanks then 2 Mines \n" +
	        		  			"-Each turn a Player Selects Fire or Move \n" +
	        				  	"-Tap Tanks to Fire/ Drag to Move \n" +
	        		  			"-Tapping Area on Screen to Submit \n" +
	        				  	"-Striking Enemy Mines Will Result in Damage \n" +
	        		  			"-Last Player with Tanks will Win");                
	        		  alert5.setPositiveButton("START GAME", new DialogInterface.OnClickListener() {  
	        			  public void onClick(DialogInterface dialog, int whichButton) {  
	        				 gameToast("Tap screen to place Tanks");  
	        			  }  
	        		  });  
	        		  alert5.show(); 
	        		  break;
	     	   }
		        	
		        }
		    });
		}

	@Override
	public boolean onSceneTouchEvent(final Scene pScene, TouchEvent pSceneTouchEvent) {
		touchX = pSceneTouchEvent.getX();
		touchY = pSceneTouchEvent.getY();
		//Max tanks = 5
		//PLAYER1 SET UP SIDE
		if(player1 && !gameStart){
			//Checks each touch if the tank has been removed from the screen
			//Removes it from Linked List
			checkRemove(tankList);
			//Checks if any other tanks are selected 
			//If they are deselect them
			unregisterItems(tankList2);
			checkOthersSelected(tankList);
			if(tankList.size()<MAX_TANKS){
				if (pSceneTouchEvent.isActionDown()) {
					
	      
					tank = new Tank(touchX ,touchY,50,50, this.mFaceTextureRegion, this.getVertexBufferObjectManager());

					float posY = touchY - tank.getHeight() / 2;
		   
					if(posY > (CAMERA_HEIGHT / 2) + 5 && placeTank(tank,tankList) ){
						tankList.add(tank);
						scene.registerTouchArea(tank); // register touch area , so this allows you to drag it
						scene.attachChild(tank); //add it to the scene
						if(tankList.size()==MAX_TANKS){
							gameToast("Place Mines");
						}
					}
				
				return false;
				} 
			}else if(mineList.size() < MAX_MINES){
				
				if(pSceneTouchEvent.isActionDown()){
				mine = new Sprite(touchX ,touchY,50,50, this.mMineTextureRegion, this.getVertexBufferObjectManager());
					float posY = touchY - mine.getHeight() / 2;
					if(posY > (CAMERA_HEIGHT / 2) + 5 && canPlace(mine,mineList,tankList)){
						mineList.add(mine);
						scene.registerTouchArea(mine); // register touch area , so this allows you to drag it
						scene.attachChild(mine); //add it to the scene
					}
					return false;
				}
			}else{
				player1 = false;
				gameDialog(1);
				
			}
		}
		//PLAYER2 SET UP SIDE
		else if(!player1 && !gameStart){
			// CREATE POP UP DIALOG TO SUBMIT
			//Player 2 turn
			if(tankList2.size()<MAX_TANKS){
				//Checks each touch if the tank has been removed from the screen
				//Removes it from Linked List
				checkRemove(tankList2);
				//Checks if any other tanks are selected 
				//If they are deselect them
				unregisterItems(tankList);
				checkOthersSelected(tankList2);
				if (pSceneTouchEvent.isActionDown()) {
					touchX = pSceneTouchEvent.getX();
					touchY = pSceneTouchEvent.getY();
	      
					tank = new Tank(touchX ,touchY,50,50, this.mFaceTextureRegion, this.getVertexBufferObjectManager());

					float posY = touchY + tank.getHeight() / 2;
		   
					if(posY < (CAMERA_HEIGHT - this.mBarricadeTextureRegion.getHeight()) / 2 - 20 && placeTank(tank,tankList2) ){
						tankList2.add(tank);
						tank.setRotation(180);
						scene.registerTouchArea(tank); // register touch area , so this allows you to drag it
						scene.attachChild(tank); //add it to the scene
						if(tankList2.size()==MAX_TANKS){
							gameToast("Place Mines");
						}
					}
					
				return false;
				} 
			}else if(mineList2.size() < MAX_MINES){
				//PLACE MINES
				if(pSceneTouchEvent.isActionDown()){
				mine = new Sprite(touchX ,touchY,50,50, this.mMineTextureRegion, this.getVertexBufferObjectManager());
					
					float posY = touchY - mine.getHeight() / 2;
					if(posY < (CAMERA_HEIGHT - this.mBarricadeTextureRegion.getHeight()) / 2 - 20 && canPlace(mine,mineList2,tankList2)){
						mineList2.add(mine);
						scene.registerTouchArea(mine); // register touch area , so this allows you to drag it
						scene.attachChild(mine); //add it to the scene
					}
					return false;
				}
			}else{
				gameStart = true;
				gameDialog(2);
			}
			
		}else{
			//GAME STARTED Player 1 turn
			if(player1){
				if(turn1mes){
					camera.setRotation(0f);
					gameToast("Player1 turn");
					registerItems(tankList);
					unregisterItems(tankList2);
					turn1mes = false;
					turn2mes = true;
					gameDialog(3);
				}else{
					camera.setRotation(0f);
				}
				//User selects fire on dialog
				if(fire){
					int index = 0;
					canFire = false;
					for(int i = 0;i < tankList.size();i++){
						if(tankList.get(i).isSelected){
							index = i;
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
						   
						if(posY > (CAMERA_HEIGHT / 2)){
							Sprite circle = new Sprite(touchX, touchY, this.mCircleTextureRegion, this.mEngine.getVertexBufferObjectManager());
							scene.attachChild(circle);
							Sprite circle2 = new Sprite(touchX,CAMERA_HEIGHT-touchY, this.mCircleTextureRegion, this.mEngine.getVertexBufferObjectManager());
							scene.attachChild(circle2);
							//If hits other TANK
							for(int i = 0; i < UnitAllocationActivity.tankList2.size();i++){  
								if( circle2.collidesWith(tankList2.get(i)) ){
									scene.detachChild(tankList2.get(i));
									scene.detachChild(circle2);
									tankList2.remove(i);
									Sprite explosion = new Sprite(touchX,CAMERA_HEIGHT-touchY, this.mExplosionTextureRegion, this.mEngine.getVertexBufferObjectManager());
									scene.attachChild(explosion);
								}
							}
							//if hits mine
							//find selected tank and destroy it
							for(int i = 0; i < UnitAllocationActivity.mineList2.size();i++){  
								if( circle2.collidesWith(mineList2.get(i)) ){
									scene.detachChild(tankList.get(index));
									scene.detachChild(mineList2.get(i));
									scene.detachChild(circle2);
									Sprite explosion = new Sprite(tankList.get(index).originX,tankList.get(index).originY, this.mExplosionTextureRegion, this.mEngine.getVertexBufferObjectManager());
									scene.attachChild(explosion);
									Sprite explosion1 = new Sprite(touchX,CAMERA_HEIGHT-touchY, this.mExplosionTextureRegion, this.mEngine.getVertexBufferObjectManager());
									scene.attachChild(explosion1);
									tankList.remove(tankList.get(index));
									mineList2.remove(i);
								}
							}
							if(player1win()){
								turn2mes = false;
								gameDialog(4);
							}else{
								turn2mes = true;
							}
							tankList.get(index).isSelected = false;
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
					player1 = false;
					turn2mes = true;
					move = false;
				
				//Player1 selects View Map
				}else if(viewMap){
					viewMap = false;
					gameDialog(3);
				}
			}else{
				if(turn2mes){
					camera.setRotation(180f);
					unregisterItems(tankList);
					registerItems(tankList2);
					gameToast("Player2 turn");
					turn2mes = false;
					gameDialog(3);
					
				}
					
					//IF USER SELECTS FIRE
					if(fire){
						int index = 0;
						canFire = false;
						//Makes sure a tank is selected to fire
						for(int i = 0;i < tankList2.size();i++){
							if(tankList2.get(i).isSelected){
								index = i;
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
								//if hits mine
								//find selected tank and destroy it
								for(int i = 0; i < mineList.size();i++){  
									if( circle2.collidesWith(mineList.get(i)) ){
										scene.detachChild(tankList2.get(index));
										scene.detachChild(mineList.get(i));
										scene.detachChild(circle2);
										Sprite explosion = new Sprite(tankList2.get(index).originX,tankList2.get(index).originY, this.mExplosionTextureRegion, this.mEngine.getVertexBufferObjectManager());
										scene.attachChild(explosion);
										Sprite explosion1 = new Sprite(touchX,CAMERA_HEIGHT-touchY, this.mExplosionTextureRegion, this.mEngine.getVertexBufferObjectManager());
										scene.attachChild(explosion1);
										tankList.remove(tankList2.get(index));
										mineList.remove(i);
									}
								}
								if(player2win()){
									turn1mes = false;
									gameDialog(4);
								}else{
									turn1mes = true;
								}
								tankList2.get(index).isSelected = false;
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
						player1 = true;
						turn1mes = true;
						move = false;
					
					//Player1 selects View Map
					}else if(viewMap){
						viewMap = false;
						gameDialog(3);
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
	
	public boolean player2win(){
		if(tankList.isEmpty()){
			return true;
		}
		return false;
	}

	boolean canPlace(Sprite sprite,LinkedList<Sprite> spriteList,LinkedList<Tank> list){
		for(int i = 0; i < list.size(); i++){
			if(sprite.collidesWith(list.get(i))){
				return false;
			}
		}
		for(int i = 0; i < spriteList.size(); i++){
			if(sprite.collidesWith(list.get(i))){
				return false;
			}
		}
		return true;
	}
	
	
	boolean placeTank(Tank tank,LinkedList<Tank> list){
		for(int i = 0; i < list.size(); i++){
			if(tank.collidesWith(list.get(i))){
				return false;
			}
		}
		return true;
	}
	
	
/*****************************************************
* Tank Class when selected is highlighted by
* a rectangle object
******************************************************/
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

		public Tank(float centerX, int i, ITextureRegion mFaceTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager) {
			super(centerX,i,mFaceTextureRegion,vertexBufferObjectManager);
			// TODO Auto-generated constructor stub
		}
		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			float posY = pSceneTouchEvent.getY() - this.getHeight() / 2;
			float posX = pSceneTouchEvent.getX() - this.getWidth() / 2;
		if(fire){
			if(UnitAllocationActivity.player1){
				 checkOthersSelected(tankList);
			 }else{
				 checkOthersSelected(tankList2);
			 }	 
			 isSelected = true;
			 outline.setVisible(true);
			 if(gameStart){
				 unregisterItems(tankList);
				 unregisterItems(tankList2);
			 }  
		}else{
			if(pSceneTouchEvent.isActionDown()){
				 this.originX = posX;
				 this.originY = posY;
	             validMove = true;
				 if(UnitAllocationActivity.player1){
						 checkOthersSelected(tankList);
					 }else{
						 checkOthersSelected(tankList2);
					 }
				 	isSelected = true;
					 outline.setVisible(true);
					 return true;
			}
			else if(pSceneTouchEvent.isActionMove()){
				 if(player1){
					 checkOthersSelected(tankList);
				 }else{
					 checkOthersSelected(tankList2);
				 }
				 isSelected = true;
				if (validMove) {
					if(player1){
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
						for (int i = 0; i < mineList.size(); i++) {
								if (this.collidesWith(mineList.get(i))) {
									this.setPosition(this.originX, this.originY);
									this.outline.setPosition(this.originX - 2, this.originY - 2);
									validMove = false;
									return true;
								}
						}
						if  (posY < ( (CAMERA_HEIGHT / 2) + (mBarricadeTextureRegion.getHeight() / 2))) {
							posY = (CAMERA_HEIGHT / 2) + (mBarricadeTextureRegion.getHeight() / 2);
						}

					}else{
						for (int i = 0; i < tankList2.size(); i++) {
							if (this != tankList2.get(i)) {
								if (this.collidesWith(tankList2.get(i))) {
									this.setPosition(this.originX, this.originY);
									this.outline.setPosition(this.originX - 2, this.originY - 2);
									validMove = false;
									return true;
								}
							}
						}
						for (int i = 0; i < mineList2.size(); i++) {
							if (this.collidesWith(mineList2.get(i))) {
								this.setPosition(this.originX, this.originY);
								this.outline.setPosition(this.originX - 2, this.originY - 2);
								validMove = false;
								return true;
							}
						}
						if  (posY > ( (CAMERA_HEIGHT / 2) + (mBarricadeTextureRegion.getHeight() / 2))) {
							posY = (barricade.getY()-barricade.getHeight()*2);
						}
					}
					
					
				    this.setPosition(posX, posY);
				    outline.setPosition(posX - 2, posY - 2);
				}else{
					return true;
				}
          
        } 
		else if (pSceneTouchEvent.isActionUp()) {
			 if(UnitAllocationActivity.player1){
				 checkOthersSelected(tankList);
			 }else{
				 checkOthersSelected(tankList2);
			 }
			 isSelected = true;
			if (validMove) {
				this.originX = posX;
			    this.originY = posY;

			    
			}
		}
	 }
	  return true;
	}
};
}
