
package com.cen3031.blast;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.util.Iterator;
import org.andengine.engine.handler.IUpdateHandler;
import android.util.Log;

public class UnitAllocationActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener,OnClickListener {

	// ===========================================================
	// Constants
	// ===========================================================
	private static int CAMERA_WIDTH;
	private static int CAMERA_HEIGHT;
	 private BitmapTextureAtlas mFontTexture;
     private Font mFont;
	// ===========================================================
	// Fields
	// ===========================================================
	
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private BitmapTextureAtlas mBitmapTextureAtlas2;
	private TextureRegion mTankTextureRegion;
	private TextureRegion SoldierTextureRegion;
	private TextureRegion SoldierBut1TextureRegion;
	private TextureRegion SoldierBut2TextureRegion;
	public TextureRegion aimTextureRegion;
	static TextureRegion mBarricadeTextureRegion;
	private TextureRegion mCircleTextureRegion;
	private static TextureRegion mExplosionTextureRegion;
	private TextureRegion mPlayer1TextureRegion;
	private TextureRegion mPlayer2TextureRegion;
	private TextureRegion mMineTextureRegion;
	private TextureRegion mTankButton1TextureRegion;
	private TextureRegion mTankButton2TextureRegion;
	private TextureRegion mMineButton1TextureRegion;
	private TextureRegion mMineButton2TextureRegion;
	private TextureRegion mButton1TextureRegion;
	private TextureRegion mButton2TextureRegion;
	private TextureRegion mBulletTextureRegion;
	private Sprite barricade;
	private SpriteBackground mGrassBackground;
	private Sound snd_explosion;
	private Sound snd_game_win;
	private Sound snd_game_loss;
	private Sound snd_miss;
	private Sound snd_fire;
	Camera camera;
	
	Tank tank;
	Soldier soldier;
	Sprite mine;
	float touchX; 
	float touchY;
	static Scene scene;
	HUD hud;
	static LinkedList<Tank> tankList = new LinkedList<Tank>();
	static LinkedList<Tank> tankList2 = new LinkedList<Tank>();
	static LinkedList<Integer> tankXList = new LinkedList<Integer>();
	static LinkedList<Integer> tankYList = new LinkedList<Integer>();
	static LinkedList<Integer> tankXList2 = new LinkedList<Integer>();
	static LinkedList<Integer> tankYList2 = new LinkedList<Integer>();
	static LinkedList<Soldier> soldList = new LinkedList<Soldier>();
	static LinkedList<Soldier> soldList2 = new LinkedList<Soldier>();
	static LinkedList<Integer> soldXList = new LinkedList<Integer>();
	static LinkedList<Integer> soldYList = new LinkedList<Integer>();
	static LinkedList<Integer> soldXList2 = new LinkedList<Integer>();
	static LinkedList<Integer> soldYList2 = new LinkedList<Integer>();
	static LinkedList<Sprite> circleList = new LinkedList<Sprite>();
	static LinkedList<Sprite> mineList = new LinkedList<Sprite>();
	static LinkedList<Sprite> mineList2 = new LinkedList<Sprite>();
	static LinkedList<Integer> mineXList = new LinkedList<Integer>();
	static LinkedList<Integer> mineYList = new LinkedList<Integer>();
	static LinkedList<Integer> mineXList2 = new LinkedList<Integer>();
	static LinkedList<Integer> mineYList2 = new LinkedList<Integer>();
	private static final int MAX_TANKS = 5;
	private static final int MAX_MINES= 2;
	static boolean player1 ;
	boolean turn1mes;
	boolean turn2mes;
	boolean fire;
	boolean canFire;
	boolean move;
	boolean viewMap;
	boolean gameStart;
	boolean tankSel;
	boolean mineSel;
	boolean soldierSel;
	ButtonSprite tankButton;
	ButtonSprite mineButton;
	ButtonSprite soldierButton;
	Text tankText;
	Text mineText;
	Text soldText;
	Text user1Text;
	Text user2Text;
	ButtonSprite fireButton;
	ButtonSprite mapButton;
	ButtonSprite moveButton;
	Sprite bullet;
	Sprite bullet_s;
	int bulletSpeed = 500;
	Sprite circle;
	boolean animOver;
	GameState gameState;
	boolean isOnline = false;
	Rectangle balanceLabel;
	Rectangle unitAllocLabel;
	Text moneyText;
	int MONEY = 4;
	int balance1 = MONEY;
	int balance2 = MONEY;
	String user1 = null;
	String user2 = null;
	String pIDturn = null;
	String phoneID1 = null;
	String phoneID2 = null;
	int gameID;
	int turn = 0;
	int starter = 0;
	
	public TextureRegion ITextureRegion;
	public BitmapTextureAtlas BitmapTextureAtlas3;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
//		DisplayMetrics metrics = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		CAMERA_WIDTH = 480;
		CAMERA_HEIGHT = 800;

		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	     
		EngineOptions en = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		en.getAudioOptions().setNeedsSound(true);
		return en;	
		}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),128, 128, TextureOptions.BILINEAR);
		this.mBitmapTextureAtlas2 = new BitmapTextureAtlas(this.getTextureManager(),1024, 1024, TextureOptions.BILINEAR);
		this.mTankTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "tank.png", 0, 0);
		this.SoldierTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "sprite_soldier.png",30,0);
		this.SoldierBut1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "sprite_soldier_but1.png",60,0);
		this.SoldierBut2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "sprite_soldier_but2.png",90,0);
		this.aimTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "soldier_aim.png",0,30);
		this.mBarricadeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "barricade.png", 0, 0);
		this.mExplosionTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "explosion.png", 0, 50);
		this.mCircleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "small_circle.png", 20, 200);
		this.mPlayer1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "player1.png", 10, 25);
		this.mPlayer2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "player2.png", 10, 100);
		this.mMineTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "mine.png", 150, 150);
		this.mTankButton1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "tankButton1.png", 200, 200);
		this.mTankButton2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "tankButton2.png", 250, 250);
		this.mMineButton1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "mineButton1.png", 300, 300);
		this.mMineButton2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "mineButton2.png", 375, 375);
		this.mButton1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "button1.png", 450, 450);
		this.mButton2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "button2.png", 550, 550);
		this.mBulletTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "bullet.png", 650, 650);
		//this.mGrassBackground = new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, this.getTextureManager(), AssetBitmapTextureAtlasSource.create(this.getAssets(), "gfx/background_grass.png"), this.getVertexBufferObjectManager());
		
		this.BitmapTextureAtlas3 = new BitmapTextureAtlas (this.getTextureManager (), CAMERA_WIDTH, CAMERA_HEIGHT);
		Random r = new Random();
		int i1 = r.nextInt(3) + 1;
		
		if (starter==0) {
			if (i1 == 1 ) {
				this.ITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset (this.BitmapTextureAtlas3, this, "field.png", 0, 0);
			}
			if (i1 == 2 ) {
				this.ITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset (this.BitmapTextureAtlas3, this, "snowbackground.png", 0, 0);
			}
			if (i1 == 3 ) {
				this.ITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset (this.BitmapTextureAtlas3, this, "dessert.png", 0, 0);
			}
			
			starter = 1;
			
		}
		//this.mGrassBackground = new SpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, this.getTextureManager(), AssetBitmapTextureAtlasSource.create(this.getAssets(), "gfx/field.png"), this.getVertexBufferObjectManager());	
		this.getEngine().getTextureManager().loadTexture(mBitmapTextureAtlas);
		this.getEngine().getTextureManager().loadTexture(mBitmapTextureAtlas2);
		this.getEngine().getTextureManager().loadTexture(BitmapTextureAtlas3);
		this.mFontTexture = new BitmapTextureAtlas(this.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        this.mFont = new Font(this.getFontManager(), this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, Color.WHITE);

        this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
        this.getFontManager().loadFont(this.mFont);
        
        //SoundFactory.setAssetBasePath("sounds/");
        try
        {
        	snd_explosion = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(), "sounds/explosion.wav");
        	snd_game_win = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(), "sounds/GameOverWin.wav");
        	snd_game_loss = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(), "sounds/GameOverLoss.wav");
        	snd_miss = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(), "sounds/Miss.wav");
        	snd_fire = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(), "sounds/MissileFire.wav");
        } catch (IOException e)
        {
        	e.printStackTrace();
        }
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
		scene = new Scene();
		Sprite back = new Sprite (0, 0, CAMERA_WIDTH,CAMERA_HEIGHT,this.ITextureRegion, this.getVertexBufferObjectManager ());
		scene.setBackground(new SpriteBackground (back));
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.setOnSceneTouchListener(this);
		//ONLINE GAME SETUPS
      	Bundle extras = getIntent().getExtras();
      		if (extras != null) {
      			String activity = extras.getString("activity");
      			//Player starts new game
      			if(activity.equals("NewGame")){
	      			phoneID1 = extras.getString("phoneID");
	      			user1 = extras.getString("user");
	      			player1 = true;
      			}else if(activity.equals("StartedGame")){
      				GameState gs = (GameState) getIntent().getSerializableExtra("GameState");
      				System.out.println(gs.user1name+","+gs.user1ID+","+gs.user2name+","+gs.user2ID);
      				setGameState(gs);
      				player1 = false;
      				camera.setRotation(180f);
      			}else if(activity.equals("FullGame")){
      				GameState gs = (GameState) getIntent().getSerializableExtra("GameState");
      				System.out.println(gs.user1name+","+gs.user1ID+","+gs.user2name+","+gs.user2ID);
      				setGameState(gs);
      				gameStart = true;
      				if(pIDturn.equals(phoneID1)){
      					camera.setRotation(0);
      				}else{
      					camera.setRotation(180f);
      				}
      			}
      				isOnline = true;
      			
      		}
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		
		
		final float centerX = (CAMERA_WIDTH - this.mBarricadeTextureRegion.getWidth()) / 2;
        final float centerY = (CAMERA_HEIGHT - this.mBarricadeTextureRegion.getHeight()) / 2;
        final float textCenterX =(CAMERA_WIDTH - this.mPlayer1TextureRegion.getWidth()) / 2;
        
        this.barricade = new Sprite(centerX, centerY, this.mBarricadeTextureRegion, this.mEngine.getVertexBufferObjectManager());
        scene.attachChild(this.barricade);
       
    	//ONLINE PLAYER1 NAME
    	if(user1 != null){
    		user1Text =  new Text(textCenterX-10, CAMERA_HEIGHT-40, this.mFont, user1, new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());	
    	}else{
    	//PASS AND PLAY Player1 NAME
    		user1Text =  new Text(textCenterX-10, CAMERA_HEIGHT-40, this.mFont, "PLAYER1", new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
    	}
    	//ONLINE PLAYER2 NAME
    	if(user2 != null){
    		user2Text =  new Text(textCenterX, 10, this.mFont, user2, new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
    	}else{
    	//PASS AND PLAY Player2 NAME
    		user2Text =  new Text(textCenterX, 10, this.mFont, "PLAYER2", new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
    	}
    	System.out.println("NULL test0-1");
    	//SET USER1 and 2 text
    	user1Text.setScale(2);
        scene.attachChild(user1Text);
        user2Text.setScale(2);
        user2Text.setRotation(180);
        scene.attachChild(user2Text);
        
        System.out.println("NULL test1");
        //Set turn
        if(isOnline){
        	if(pIDturn != null){
	        	if(pIDturn.equals(phoneID1)){
	        		player1 = true;
	        		gameStart = true;
	        	}else if(pIDturn.equals(phoneID2)){
	        		player1 = false;
	        		gameStart = true;
	        	}
	        	
	        	
        	}else{
        		gameStart = false;
        	}
        System.out.println(gameStart);
        }else{
        	player1 = true;
        	gameStart = false;
        }
    	gameDialog(5);
		
    	hud = new HUD();
    	System.out.println("NULL test3");
    	//TANK and MINE buttons
        tankButton = new ButtonSprite(0, barricade.getY()-75, this.mTankButton1TextureRegion,this.mTankButton2TextureRegion, this.getVertexBufferObjectManager(),this);
        tankButton.setSize(75,75);
        tankText =  new Text(5, 0, this.mFont, Integer.toString(1), new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());

        
        mineButton = new ButtonSprite(75, barricade.getY()-75, this.mMineButton1TextureRegion,this.mMineButton2TextureRegion, this.getVertexBufferObjectManager(),this);
        mineButton.setSize(75,75);
        mineText =  new Text(5, 0, this.mFont, Integer.toString(1), new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
        
        balanceLabel = new Rectangle(225,barricade.getY()-75,75,75,this.getVertexBufferObjectManager());
        balanceLabel.setColor(0,0,0);
        Text balanceText =  new Text(-20, -10, this.mFont, "Balance", new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
        balanceText.setScale(.5f);
        moneyText = new Text(30, 30, this.mFont,Integer.toString(MONEY), new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		balanceLabel.attachChild(moneyText);
        
        unitAllocLabel = new Rectangle(0,tankButton.getY()-45,225,45,this.getVertexBufferObjectManager());
        unitAllocLabel.setColor(0,0,0);
        Text unitAllocText =  new Text(0, 0, this.mFont, "Unit Allocation", new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
        unitAllocText.setScale(.9f);
        
        soldierButton = new ButtonSprite(150, barricade.getY()-75, this.SoldierBut1TextureRegion,this.SoldierBut2TextureRegion, this.getVertexBufferObjectManager(),this);
        soldierButton.setSize(75,75);
        soldText = new Text(5, 0, this.mFont, Integer.toString(1), new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
        
        tankButton.attachChild(tankText);
        soldierButton.attachChild(soldText);
        mineButton.attachChild(mineText);
        balanceLabel.attachChild(balanceText);
        unitAllocLabel.attachChild(unitAllocText);
        
        hud.attachChild(balanceLabel);
        hud.attachChild(unitAllocLabel);
        hud.attachChild(tankButton);
        hud.attachChild(mineButton);
        hud.attachChild(soldierButton);
        hud.registerTouchArea(tankButton);
        hud.registerTouchArea(mineButton);
        hud.registerTouchArea(soldierButton);
        hud.setTouchAreaBindingOnActionDownEnabled(true);
        this.camera.setHUD(hud);
        
        
        //FIRE AND MOVE BUTTONS
        fireButton =  new ButtonSprite(0, CAMERA_HEIGHT-75, this.mButton1TextureRegion,this.mButton2TextureRegion, this.getVertexBufferObjectManager(),this);
        fireButton.setSize(CAMERA_WIDTH/3-5,75);
       // mapButton =  new ButtonSprite(CAMERA_WIDTH/3, CAMERA_HEIGHT-75, this.mButton1TextureRegion,this.mButton2TextureRegion, this.getVertexBufferObjectManager(),this);
       // mapButton.setSize(CAMERA_WIDTH/3-5,75);
        moveButton =  new ButtonSprite(CAMERA_WIDTH/3*2, CAMERA_HEIGHT-75, this.mButton1TextureRegion,this.mButton2TextureRegion, this.getVertexBufferObjectManager(),this);
        moveButton.setSize(CAMERA_WIDTH/3,75);
        
        
        Text fireButtonText = new Text(fireButton.getWidth()/4,fireButton.getHeight()/4, this.mFont, "FIRE", new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
       // Text mapButtonText = new Text(mapButton.getWidth()/4, mapButton.getHeight()/4, this.mFont, "View Map", new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
        Text moveButtonText = new Text(moveButton.getWidth()/4, moveButton.getHeight()/4, this.mFont, "MOVE", new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
        
        fireButton.attachChild(fireButtonText);
        //mapButton.attachChild(mapButtonText);
        moveButton.attachChild(moveButtonText);
        
        System.out.println("NULL test4");
        if(isOnline && gameStart){
        	  hud.detachChild(tankButton);
			  hud.detachChild(mineButton);
			  hud.detachChild(balanceLabel);
			  hud.detachChild(unitAllocLabel);
			  hud.clearTouchAreas();
			  updateHUD();
        }
        scene.registerUpdateHandler(detect);
        return scene;
		}
	 
	@Override
	public void onClick(final ButtonSprite pButtonSprite, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(pButtonSprite == tankButton){
					if(!gameStart){

						tankSel = true;
						mineSel = false;
						soldierSel = false;
					}
				}
				else if(pButtonSprite == mineButton){
					if(!gameStart){
						tankSel = false;
						mineSel = true;
						soldierSel = false;
					 
					}
				}
				else if (pButtonSprite == soldierButton){
					if(!gameStart){
						tankSel = false;
						mineSel = false;
						soldierSel = true;
					}
				}
				else if(pButtonSprite == fireButton){ 
					Log.d("debug","fire button pressed");
       				  fire = true;
       				  move = false;
       				  viewMap = false;
       				  if(player1){
       					  registerItems(tankList);
       					  registerItems_sold(soldList);
       
       				  }else{
       					  registerItems(tankList2);
       					  registerItems_sold(soldList2);
       				  }
       				  hud.setVisible(false);
				}
				else if(pButtonSprite == mapButton){ 
					  fire = false;
				      move = false;
				      viewMap = true;
				      unregisterItems(tankList);
				      unregisterItems(tankList2);
				      unregisterItems_sold(soldList);
				      unregisterItems_sold(soldList2);
     				  hud.setVisible(false);
				}
				else if(pButtonSprite == moveButton){ 
					fire = false;
				      move = true;
				      viewMap = false;
				      if(player1){
  					  registerItems(tankList);
  					  registerItems_sold(soldList);
				      }else{
  					  registerItems(tankList2);
  					  registerItems_sold(soldList2);
				      }
				      hud.setVisible(false);
				}
			}
		});
	}
	//Toast Messages in onTouch events
	 public void gameToast(final String msg) {
		    this.runOnUiThread(new Runnable() {
		        @Override
		        public void run() {
		           Toast.makeText(UnitAllocationActivity.this, msg,Toast.LENGTH_SHORT).show();
		        }
		    });
		}
	 
	 @Override
	 public void onBackPressed() {
		 scene.registerUpdateHandler(detect);
		 scene.unregisterUpdateHandler(detect);
		 finish();
	 }
	 public void onCancel(DialogInterface dialog) {
	        // if from activity
		 	scene.registerUpdateHandler(detect);
			scene.unregisterUpdateHandler(detect);
	        finish();
	        // if activity is a field
	        //activity.finish();
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
		        		  alert.setCancelable(false);
		        		  alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
		        			  public void onClick(DialogInterface dialog, int whichButton) {  		        			
		        				  gameToast("Player2 Turn to place Units");
		        				  unregisterItems(tankList);
		        				  registerItems(tankList2);
		        				  unregisterItems_sold(soldList);
		        				  registerItems_sold(soldList2);
		        				  camera.setRotation(180f);
		        			      updateMoneyText(MONEY);
		        			    //SUMBIT PLAYER1 SIDE	
		        			      if(balance1 == 0 && isOnline){
		        						setTankXYList(tankList,tankList2);
		        						setMineXYList(mineList,mineList2);
		        						setSoldierXYList(soldList,soldList2);
		        						sendData(tankXList,tankYList,null,null,mineXList,mineYList,null,null,-1,-1);
		        					}
		        				  return;                  
		        			  }  
		        		  });  
		        		 
		        		  alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		        			  public void onClick(DialogInterface dialog, int which) {
		     	        	 	player1 = true;
		     	        	 	registerItems(tankList);
		     	        	 	registerItems_sold(soldList);
		     	        	 	return;   
		     	         }
		     	     });
		        		  
		        		  
		     	             alert.show(); 
		     	             
		     	             break;
		        	  case 2: 
		        		  AlertDialog.Builder alert2 = new AlertDialog.Builder(UnitAllocationActivity.this);                 
		        		  alert2.setTitle("Player2 Submit Side");  
		        		  alert2.setMessage("Are you finished setting your side?");        
		        		  alert2.setCancelable(false);
		        		  alert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
		        			  public void onClick(DialogInterface dialog, int whichButton) {  
		        				  gameStart = true;
		        				  player1 = true;
		        				  turn1mes = false;
		        				  gameToast("Game Started!");
		        				  gameToast("Player1 turn");
		        				  registerItems(tankList);
		        				  registerItems_sold(soldList);
		        				  unregisterItems(tankList2);
		        				  unregisterItems_sold(soldList2);
		        				  camera.setRotation(0f);
		        				  hud.detachChild(tankButton);
		        				  hud.detachChild(mineButton);
		        				  hud.detachChild(soldierButton);
		        				  hud.detachChild(balanceLabel);
		        				  hud.detachChild(unitAllocLabel);
		        				  hud.clearTouchAreas();
		        				  updateHUD();
		        				  if(balance2 == 0 && isOnline){
		        						setTankXYList(tankList,tankList2);
		        						setMineXYList(mineList,mineList2);
		        						setSoldierXYList(soldList,soldList2);
		        						pIDturn = phoneID1;
		        						sendData3(tankXList,tankYList,tankXList2,tankYList2,mineXList,mineYList,mineXList2,mineYList2,-1,-1);
		        					}
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
		        					  registerItems_sold(soldList);
		        				  }else{
		        					  registerItems(tankList2);
		        					  registerItems_sold(soldList2);
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
	        				      unregisterItems_sold(soldList);
	        				      unregisterItems_sold(soldList2);
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
		        					  registerItems_sold(soldList);
		        				  }else{
		        					  registerItems(tankList2);
		        					  registerItems_sold(soldList2);
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
		        			  snd_game_win.play();
		        		  }else{
		        			  alert4.setTitle("Player2 WINS!!");
		        			  snd_game_win.play();
		        		  }
		        		  alert4.setCancelable(false);
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
	        		 
	        		  alert5.setMessage("-Each Player Places any Combination of Tanks,Mines " +
	        		  			"and Soldier with the allotted Balance \n" +
	        		  			"-Each turn a Player Selects Fire or Move \n" +
	        				  	"-Tap Tanks to Fire/ Drag to Move \n" +
	        		  			"-Tapping Area on Screen to Submit \n" +
	        				  	"-Striking Enemy Mines Will Result in Damage \n" +
	        		  			"-Last Player with Tanks will Win");
	        		  alert5.setCancelable(false);
	        		  alert5.setPositiveButton("START GAME", new DialogInterface.OnClickListener() {  
	        			  public void onClick(DialogInterface dialog, int whichButton) {  
	        				 gameToast("Tap screen to place Units");  
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
			checkRemove_sold(soldList);
			//Checks if any other tanks are selected 
			//If they are deselect them
			unregisterItems(tankList2);
			unregisterItems_sold(soldList2);
			checkOthersSelected(tankList);
			checkOthersSelected_sold(soldList);
			
			if(0<balance1 && tankSel){
				if (pSceneTouchEvent.isActionDown()) {
					
					tank = new Tank(touchX ,touchY,50,50, this.mTankTextureRegion, this.getVertexBufferObjectManager());

					float posY = touchY - tank.getHeight() / 2;
		   
					if(posY > (CAMERA_HEIGHT / 2) + 5 && placeTank(tank,tankList) ){
						tankList.add(tank);
						scene.registerTouchArea(tank); // register touch area , so this allows you to drag it
						scene.attachChild(tank); //add it to the scene
						tankSel = false;
						//updateTankText(tankList); //Update tank count
						balance1--;
						updateMoneyText(balance1);
						
					}
					
				return false;
				} 
			}
			else if((balance1 > 0) && (soldierSel == true)){
				Log.d("DEBUG: ", "Soldier Selected!");
				if (pSceneTouchEvent.isActionDown()) {
					soldier = new Soldier(touchX ,touchY,30,30, this.SoldierTextureRegion, this.getVertexBufferObjectManager());
					float posY = touchY - soldier.getHeight() / 2;
					if(posY > (CAMERA_HEIGHT / 2) + 5 && placeSoldier(soldier,soldList) ){
						Log.d("DEBUG: ", "Soldier Placed!");
						soldList.add(soldier);
						scene.registerTouchArea(soldier); // register touch area , so this allows you to drag it
						scene.attachChild(soldier); //add it to the scene
						soldierSel = false;
						balance1--;
						updateMoneyText(balance1);
					}
				        //???  
				    return false;
		       	}
			}
			else if(0 < balance1 && mineSel){
				//Check if player has only mines on side
				if(mineList.size() != MONEY-1){
					if(pSceneTouchEvent.isActionDown()){
					mine = new Sprite(touchX ,touchY,50,50, this.mMineTextureRegion, this.getVertexBufferObjectManager());
						float posY = touchY - mine.getHeight() / 2;
						if(posY > (CAMERA_HEIGHT / 2) + 5 && canPlace(mine,mineList,tankList)){
							mineList.add(mine);
							scene.registerTouchArea(mine); // register touch area , so this allows you to drag it
							scene.attachChild(mine); //add it to the scene
							mineSel = false;
							balance1--;
							updateMoneyText(balance1);
						}
						return false;
					}
				}else{
					//dont allow having only mines
					gameToast("Please Select Atleast One Firing Unit");
				}
			}else if(balance1 == 0){
				player1 = false;
				gameDialog(1);
			
			}
		}
		//PLAYER2 SET UP SIDE
		else if(!player1 && !gameStart){
			// CREATE POP UP DIALOG TO SUBMIT
			//Player 2 turn
			if(0<balance2 && tankSel){
				//Checks each touch if the tank has been removed from the screen
				//Removes it from Linked List
				checkRemove(tankList2);
				checkRemove_sold(soldList2);
				//Checks if any other tanks are selected 
				//If they are deselect them
				unregisterItems(tankList);
				unregisterItems_sold(soldList);
				checkOthersSelected(tankList2);
				checkOthersSelected_sold(soldList2);
				if (pSceneTouchEvent.isActionDown()) {
					touchX = pSceneTouchEvent.getX();
					touchY = pSceneTouchEvent.getY();
	      
					tank = new Tank(touchX ,touchY,50,50, this.mTankTextureRegion, this.getVertexBufferObjectManager());

					float posY = touchY + tank.getHeight() / 2;
		   
					if(posY < (CAMERA_HEIGHT - this.mBarricadeTextureRegion.getHeight()) / 2 - 20 && placeTank(tank,tankList2) ){
						tankList2.add(tank);
						tank.setRotation(180);
						scene.registerTouchArea(tank); // register touch area , so this allows you to drag it
						scene.attachChild(tank); //add it to the scene
						tankSel = false;
						balance2--;
						updateMoneyText(balance2);
					}
					
				return false;
				} 
			}
			else if((balance2 > 0) && (soldierSel == true)){
				Log.d("DEBUG: ", "Soldier Selected!");
				if (pSceneTouchEvent.isActionDown()) {
					soldier = new Soldier(touchX ,touchY,30,30, this.SoldierTextureRegion, this.getVertexBufferObjectManager());
					float posY = touchY - soldier.getHeight() / 2;
					if(posY < (CAMERA_HEIGHT / 2) + 5 && placeSoldier(soldier,soldList) ){
						Log.d("DEBUG: ", "Soldier Placed!");
						soldList2.add(soldier);
						soldier.setRotation(180);
						scene.registerTouchArea(soldier); // register touch area , so this allows you to drag it
						scene.attachChild(soldier); //add it to the scene
						soldierSel = false;
						balance2--;
						updateMoneyText(balance2);
					}
				        //???  
				    return false;
		       	}
			}
			else if(0 < balance2 && mineSel){
				//PLACE MINES
				//Check if player has only mines on side
				if(mineList2.size() != MONEY-1){
					if(pSceneTouchEvent.isActionDown()){
					mine = new Sprite(touchX ,touchY,50,50, this.mMineTextureRegion, this.getVertexBufferObjectManager());
						
						float posY = touchY - mine.getHeight() / 2;
						if(posY < (CAMERA_HEIGHT - this.mBarricadeTextureRegion.getHeight()) / 2 - 20 && canPlace(mine,mineList2,tankList2)){
							mineList2.add(mine);
							scene.registerTouchArea(mine); // register touch area , so this allows you to drag it
							scene.attachChild(mine); //add it to the scene
							mineSel = false;
							balance2--;
							updateMoneyText(balance2);
						}
						return false;
					}
				}else{
					//dont allow having only mines
					gameToast("Please Select Atleast One Firing Unit");
				}
			}else if(balance2 == 0){
				gameStart = true;
				gameDialog(2);
			}
			
		}else{
			//GAME STARTED Player 1 turn
			if(player1){
				if(turn1mes ){
					
					gameToast("Player1 turn");
					unregisterItems(tankList2);
					unregisterItems_sold(soldList2);
//					checkOthersSelected(tankList);
//					checkOthersSelected_sold(soldList);
					turn1mes = false;
					turn2mes = true;
					hud.setVisible(true);
				}
					//else{
//					camera.setRotation(0f);
//				}
				//User selects fire on dialog
				if(fire){
					int index = 0;
					canFire = false;
					boolean soldierFire = false;
					for(int i = 0;i < tankList.size();i++){
						if(tankList.get(i).isSelected){
							index = i;
							tankList.get(i).outline.setVisible(false);
							canFire = true;
						}
					}
					if(canFire == false){
			            for(int i = 0;i < soldList.size();i++){
			              if(soldList.get(i).isSelected){
			                index = i;
			                soldList.get(i).outline.setVisible(false);
			                canFire = true;
			                soldierFire  = true;
			              }
			            }
			         }
					if(canFire && soldierFire == false){
						//Makes sure a tank is selected to fire
						
						if (pSceneTouchEvent.isActionDown()) {
							touchX = pSceneTouchEvent.getX();
							touchY = pSceneTouchEvent.getY();
							float posY = touchY - tank.getHeight() / 2;
							 
							if(posY > (CAMERA_HEIGHT / 2)){
								tankFire(tankList,tankList2,mineList2,touchX,touchY,index,mEngine);
							}else{
								registerItems(tankList);
								registerItems_sold(soldList);
							}
						}
					}
					else if(canFire && soldierFire == true){
		            if (pSceneTouchEvent.isActionDown()) {
		              touchX = pSceneTouchEvent.getX();
		              touchY = pSceneTouchEvent.getY();
		              float posY = touchY - soldier.getHeight() / 2;
		               
		              //if(posY > (CAMERA_HEIGHT / 2)){
		                soldierFire(index);
		              //}else{
		                //registerItems(tankList);
		                //registerItems_sold(soldList);
		              //}
		            }
		          }
				}
				//IF USER PICKS MOVE
				else if(move){
					player1 = false;
					turn2mes = true;
					turn1mes = false;
					move = false;
					unregisterItems(tankList2);
					unregisterItems_sold(soldList2);
					if(isOnline){
						setTankXYList(tankList,tankList2);
						setMineXYList(mineList,mineList2);
						setSoldierXYList(soldList,soldList2);
						sendData2(tankXList,tankYList,tankXList2,tankYList2,mineXList,mineYList,mineXList2,mineYList2,-1,-1);	
					}
				//Player1 selects View Map
				}else if(viewMap){
					viewMap = false;
					hud.setVisible(true);
				}
			}else{
				if(turn2mes){
					
					unregisterItems(tankList);
					unregisterItems_sold(soldList);
					gameToast("Player2 turn");
					turn2mes = false;
					turn1mes = true;
//					checkOthersSelected(tankList2);
//					checkOthersSelected_sold(soldList2);
					hud.setVisible(true);
				}
//				else{
//					camera.setRotation(180f);
//				}
					//IF USER SELECTS FIRE
					if(fire){
						int index = 0;
						canFire = false;
						boolean soldierFire = false;
						//Makes sure a tank is selected to fire
						for(int i = 0;i < tankList2.size();i++){
							if(tankList2.get(i).isSelected){
								index = i;
								tankList2.get(i).outline.setVisible(false);
								canFire = true;
							}
						}
						if(canFire == false){
				            for(int i = 0;i < soldList2.size();i++){
				              if(soldList2.get(i).isSelected){
				                index = i;
				                soldList2.get(i).outline.setVisible(false);
				                canFire = true;
				                soldierFire  = true;
				              }
				            }
				         }
						if(canFire && soldierFire == false){
						
							if (pSceneTouchEvent.isActionDown()) {
								touchX = pSceneTouchEvent.getX();
								touchY = pSceneTouchEvent.getY();
								float posY = touchY - tank.getHeight() / 2;  
								if(posY <= (CAMERA_HEIGHT / 2)){
									tankFire(tankList2,tankList,mineList,touchX,touchY,index,mEngine);
									
								}else{
									registerItems(tankList2);
									registerItems_sold(soldList2);
								}
							}
						}
						else if(canFire && soldierFire == true){
				            if (pSceneTouchEvent.isActionDown()) {
				              touchX = pSceneTouchEvent.getX();
				              touchY = pSceneTouchEvent.getY();
				              float posY = touchY - soldier.getHeight() / 2;
				               
				             // if(posY > (CAMERA_HEIGHT / 2)){
				                soldierFire(index);
				              //}else{
				                //registerItems(tankList2);
				                //registerItems_sold(soldList2);
				             // }
				            }
				          }
					}
					//IF USER SELECTS MOVE
					else if(move){
						player1 = true;
						turn1mes = true;
						turn2mes = false;
						move = false;
						unregisterItems(tankList);
						unregisterItems_sold(soldList);
						if(isOnline){
							setTankXYList(tankList,tankList2);
							setMineXYList(mineList,mineList2);
							setSoldierXYList(soldList,soldList2);
							sendData2(tankXList,tankYList,tankXList2,tankYList2,mineXList,mineYList,mineXList2,mineYList2,-1,-1);	
						}
					//Player1 selects View Map
					}else if(viewMap){
						viewMap = false;
						hud.setVisible(true);
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
	
	  float x,y;
	  boolean s_hit = false;
	  IUpdateHandler detect = new IUpdateHandler() {
	    @Override
	    public void reset() {
	    }  
	
	    @Override
	    public void onUpdate(float pSecondsElapsed) {
	    	if(player1){
	    		camera.setRotation(0);
	    	}
	    	else{
	    		camera.setRotation(180);
	    }
	      Iterator<Tank> it;
	      Iterator<Soldier> its;
	      Tank target_tank;
	      Soldier target_soldier;
	      if(!player1){
	        it = tankList2.iterator();
	        // iterating over the targets
	        while (it.hasNext()) {
	          target_tank = it.next();
	          if(bullet_s != null && target_tank != null){
	            if(target_tank.collidesWith(bullet_s)){
	            	snd_explosion.play();
	              x = target_tank.originX;
	              y = target_tank.originY;
	              s_hit = true;
	              scene.detachChild(target_tank);
	               try {
	                 Log.d("DEBUG", "Tring to remove tank from tanklist");
	                 it.remove();
                     } 
                     catch (Exception e) {
                       e.printStackTrace();
                     }
	            }
	          }
	        }
	        
	        its = soldList2.iterator();
	        while (its.hasNext()) {
	          target_soldier = its.next();
	          if(bullet_s != null && target_soldier != null){
	            if(target_soldier.collidesWith(bullet_s)){
	            	snd_explosion.play();
	              x = target_soldier.originX;
	              y = target_soldier.originY;
	              s_hit = true;
	              scene.detachChild(target_soldier);
	               try {
	                 Log.d("DEBUG", "Tring to remove soldier from tanklist");
	                 its.remove();
	                 } 
	                 catch (Exception e) {
	                   e.printStackTrace();
	                 }
	            } 
	          }
	        }
	      }
	      else{
	    	  it = tankList.iterator();
		        // iterating over the targets
		        while (it.hasNext()) {
		          target_tank = it.next();
		          if(bullet_s != null && target_tank != null){
		            if(target_tank.collidesWith(bullet_s)){
		            	snd_explosion.play();
		              x = target_tank.originX;
		              y = target_tank.originY;
		              s_hit = true;
		              scene.detachChild(target_tank);
		               try {
		                 Log.d("DEBUG", "Tring to remove tank from tanklist");
		                 it.remove();
	                     } 
	                     catch (Exception e) {
	                       e.printStackTrace();
	                     }
		            }
		          }
		        }
		        
		        its = soldList.iterator();
		        while (its.hasNext()) {
		          target_soldier = its.next();
		          if(bullet_s != null && target_soldier != null){
		            if(target_soldier.collidesWith(bullet_s)){
		            	snd_explosion.play();
		              x = target_soldier.originX;
		              y = target_soldier.originY;
		              s_hit = true;
		              scene.detachChild(target_soldier);
		               try {
		                 Log.d("DEBUG", "Tring to remove soldier from tanklist");
		                 its.remove();
		                 } 
		                 catch (Exception e) {
		                   e.printStackTrace();
		                 }
		            } 
		          }
		        }
	      }
	      if(s_hit == true){
				fire = false;
				if(player1win() && isOnline == false){
					turn2mes = false;
					gameDialog(4);
				}else{
					turn2mes = true;
				}
				if(player2win() && isOnline == false){
					turn1mes = false;
					gameDialog(4);
				}else{
					turn1mes = true;
				}
			}
	    }
	  };
	

	// ===========================================================
	// Methods
	// ===========================================================
	
	public void attach_explo(float x, float y){
	  Sprite explo = new Sprite(x,y, mExplosionTextureRegion, getVertexBufferObjectManager());
	  scene.attachChild(explo);
	}
	  
	public static void checkOthersSelected(LinkedList<Tank> list){
		 for(int i = 0; i < list.size(); i++){
			    if(list.get(i).isSelected){
			    	list.get(i).isSelected = false;
			    	list.get(i).outline.setVisible(false);
			    }
		   }
	}
	
	public static void checkOthersSelected_sold(LinkedList<Soldier> list){
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
	
	public static void checkRemove_sold(LinkedList<Soldier> list){
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
	
	public static void unregisterItems_sold(LinkedList<Soldier> list){
	    for(int i = 0; i < list.size(); i++){
	        scene.unregisterTouchArea(list.get(i));
	      
	    }
	  }
	
	public static void registerItems(LinkedList<Tank> list){
		for(int i = 0; i < list.size(); i++){
				scene.registerTouchArea(list.get(i));
			
		}
	}
	
	public static void registerItems_sold(LinkedList<Soldier> list){
	    for(int i = 0; i < list.size(); i++){
	        scene.registerTouchArea(list.get(i));
	      
	    }
	  }
	
	public boolean player1win(){
		if(tankList2.isEmpty() && soldList2.isEmpty()){
			scene.registerUpdateHandler(detect);
			scene.unregisterUpdateHandler(detect);
			return true;
		}
		return false;
	}
	
	public boolean player2win(){
		if(tankList.isEmpty() && soldList.isEmpty()){
			scene.registerUpdateHandler(detect);
			scene.unregisterUpdateHandler(detect);
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
			if(sprite.collidesWith(spriteList.get(i))){
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
	
	boolean placeSoldier(Soldier sold, LinkedList<Soldier> list){
	    for(int i = 0; i < list.size(); i++){
	      if(sold.collidesWith(list.get(i))){
	    	  return false;
	      }
	    }
	    return true;
	 }
	
	void updateTankText(LinkedList<Tank> list){
		tankButton.detachChild(tankText);
		tankText = new Text(0, 0, this.mFont, Integer.toString(MAX_TANKS-list.size()), new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		tankButton.attachChild(tankText);
	}
	
	void updateMineText(LinkedList <Sprite> list){
		mineButton.detachChild(mineText);
		mineText = new Text(0, 0, this.mFont, Integer.toString(MAX_MINES-list.size()), new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		mineButton.attachChild(mineText);
	}
	
	void updateMoneyText(int balance){
		balanceLabel.detachChild(moneyText);
		moneyText = new Text(30, 30, this.mFont, Integer.toString(balance), new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		balanceLabel.attachChild(moneyText);
	}
	
	void updateHUD(){
		hud.attachChild(fireButton);
		hud.registerTouchArea(fireButton);
		//hud.attachChild(mapButton);
		//hud.registerTouchArea(mapButton);
		hud.attachChild(moveButton);
		hud.registerTouchArea(moveButton);
	}
	
	void soldierFire(final int index){
	    float slope,b;
	    float newX = 0;
	    float newY = 0;
	    Soldier selected;
	    snd_fire.play();
	    if(player1){
	      selected = soldList.get(index);
	    }
	    else{
	      selected = soldList2.get(index);
	    }
	    selected.stopAimer();
	    bullet_s  = new Sprite(selected.getX()+selected.getWidth(),selected.getY()-selected.getHeight(), 10, 10, mExplosionTextureRegion,this.getVertexBufferObjectManager());
	    if(selected.getRotation() == 0){
	      slope = (selected.getAimPosY() - (selected.originY - selected.getHeight()/2)) / (selected.getAimPosX() - (selected.originX + selected.getWidth()/2));
	    }
	    else{
	      slope = (selected.getAimPosY_2() - (selected.originY - selected.getHeight()/2)) / (selected.getAimPosX() - (selected.originX + selected.getWidth()/2));
	    }
	    b = (selected.originY - selected.getHeight()/2) - slope*(selected.originX + selected.getWidth()/2);
	    MoveModifier moveBullet = null;
	    if(selected.getAimPosX() > bullet_s.getX()){
	      newX = selected.getX() + (CAMERA_WIDTH-(selected.getX()));
	      newY =newX*slope+b;
	    }
	    else if(selected.getAimPosX() < bullet_s.getX()){
	      newX = selected.getX() - (CAMERA_WIDTH - (CAMERA_WIDTH - selected.getX()));
	      newY = (newX)*slope+b;
	    }
	    else if(selected.getAimPosX() == bullet_s.getX()){
	      newX = selected.getX();
	      newY = CAMERA_HEIGHT+10;
	    }
	    else{
	      newX = selected.getX();
	      newY = selected.getY();
	    }
	    if(((newY < -800.0f) || (newY > 1350.0f)) && (selected.getRotation() == 0)){
	      newX = selected.getX();
	      newY = 0.0f;
	    }
	    else if(((newY < -800.0f) || (newY > 1350.0f)) && (selected.getRotation() != 0)){
	      newX = selected.getX();
	      newY = CAMERA_HEIGHT;
	    }
	    if(player1){
	          player1 = false;
	        }else{
	          player1 = true;
	    }
	    moveBullet = new MoveModifier(1f,selected.getX(),newX,selected.getY(),newY){
	      @Override
	      protected void onModifierFinished(IEntity pItem){
	        super.onModifierFinished(pItem);
	        if(s_hit){
	          attach_explo(x,y);
	          s_hit = false;
	        }
	        //pItem.clearEntityModifiers();
	        if(!player1){
	          soldList.get(index).isSelected = false;
	          unregisterItems_sold(soldList);
	          //player1 = false;
	          fire = false;
	          if(player1win() && isOnline == false){
	            turn2mes = false;
	            gameDialog(4);
	          }else{
	            turn2mes = true;
	          }
	        }else{
	          
	          soldList2.get(index).isSelected = false;
	          unregisterItems_sold(soldList2);
	          //player1 = true;
	      fire = false;
	          if(player2win() && !isOnline){
	            turn1mes = false;
	            gameDialog(4);
	          }else{
	            turn1mes = true;
	          }
	          
	        }
	        if(!player1){
	          camera.setRotation(180f);
	          Log.d("DEBUG","rotated:180");
	        }else{
	          camera.setRotation(0f);
	          Log.d("DEBUG","rotated:0");
	        }
	        scene.detachChild(bullet_s);
	        //boolean pturn = !player1;
	        if(player1){
	          pIDturn = phoneID1;
	        }else{
	          pIDturn = phoneID2;
	        }
	        if(isOnline){
	          int win = 0;
	          setTankXYList(tankList,tankList2);
	          setMineXYList(mineList,mineList2);
	          setSoldierXYList(soldList,soldList2);
	          if(player1win() || player2win()){
	        	  	
					pIDturn = "null";
					if(player1win()){
						win = 1;
					}else{
						win = 2;
					}
				}
				sendData2(tankXList,tankYList,tankXList2,tankYList2,mineXList,mineYList,mineXList2,mineYList2,win,-1);	
				}
	      }
	    };
	    Log.d("originX",Float.toString(selected.getX()));
	    Log.d("originY",Float.toString(selected.getY()));
	    Log.d("final x",Float.toString(newX));
	    Log.d("final y",Float.toString(newY));
	    
	    bullet_s.registerEntityModifier(moveBullet);
	        scene.attachChild(bullet_s);
	}

	void tankFire(final LinkedList<Tank> myTanks, final LinkedList<Tank> oppTanks,final LinkedList<Sprite> oppMines,final float touchX, final float touchY,final int index, final Engine mEngine){
		circle = new Sprite(touchX,CAMERA_HEIGHT-touchY, this.mCircleTextureRegion, this.mEngine.getVertexBufferObjectManager());
		scene.attachChild(circle);
		//Shoots bullet
		snd_fire.play();
		
		final Tank selTank = myTanks.get(index);
		final float targetX = touchX;
		final float targetY = CAMERA_HEIGHT-touchY;
		float tx = selTank.originX;
		float ty = selTank.originY;
		bullet  = new Sprite(tx,ty, 10, 10, mBulletTextureRegion,this.mEngine.getVertexBufferObjectManager() );
        
        float gY =  targetY -  bullet.getY(); // some calu about how far the bullet can go, in this case up to the enemy
        float gX =  targetX - bullet.getX();
        if(player1){
        	player1 = false;
        }else{
        	player1 = true;
		}
        MoveByModifier moveBullet = new MoveByModifier(0.5f, gX,  gY){
        		
        	int tankInt = 0;
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
        		//Collision detection
				boolean somethingDied = false;
    			snd_fire.stop();
    			
        		for(int i = 0; i < oppTanks.size();i++){ 
					if( circle.collidesWith(oppTanks.get(i)) ){
						scene.detachChild(oppTanks.get(i));
						scene.detachChild(circle);
						oppTanks.remove(i);
						Sprite explosion = new Sprite(touchX,CAMERA_HEIGHT-touchY, UnitAllocationActivity.mExplosionTextureRegion, mEngine.getVertexBufferObjectManager());
						scene.attachChild(explosion);
						
						snd_explosion.play();
						somethingDied = true;
					}
				}
        		if(!player1){
	        		for(int i = 0; i < soldList2.size();i++){ 
	        			
						if( circle.collidesWith(soldList2.get(i)) ){
							System.out.println("SOLDIER HIT");
							scene.detachChild(soldList2.get(i));
							scene.detachChild(circle);
							soldList2.remove(i);
							Sprite explosion = new Sprite(touchX,CAMERA_HEIGHT-touchY, UnitAllocationActivity.mExplosionTextureRegion, mEngine.getVertexBufferObjectManager());
							scene.attachChild(explosion);
							
							snd_explosion.play();
							somethingDied = true;
						}
					}
        		}
        		else{
        			for(int i = 0; i < soldList.size();i++){ 
						if( circle.collidesWith(soldList.get(i)) ){
							System.out.println("SOLDIER HIT");
							scene.detachChild(soldList.get(i));
							scene.detachChild(circle);
							soldList.remove(i);
							Sprite explosion = new Sprite(touchX,CAMERA_HEIGHT-touchY, UnitAllocationActivity.mExplosionTextureRegion, mEngine.getVertexBufferObjectManager());
							scene.attachChild(explosion);
							
							snd_explosion.play();
							somethingDied = true;
						}
					}
        		}
        		
				//if hits mine
				//find selected tank and destroy it
				for(int i = 0; i < oppMines.size();i++){  
					if( circle.collidesWith(oppMines.get(i)) ){
						scene.detachChild(myTanks.get(index));
						scene.detachChild(oppMines.get(i));
						scene.detachChild(circle);
						Sprite explosion = new Sprite(myTanks.get(index).originX,myTanks.get(index).originY, UnitAllocationActivity.mExplosionTextureRegion, mEngine.getVertexBufferObjectManager());
						scene.attachChild(explosion);
						Sprite explosion2 = new Sprite(touchX,CAMERA_HEIGHT-touchY, UnitAllocationActivity.mExplosionTextureRegion, mEngine.getVertexBufferObjectManager());
						scene.attachChild(explosion2);
						myTanks.remove(myTanks.get(index));
						oppMines.remove(i);
						
						snd_explosion.play();
						somethingDied = true;
						tankInt = i;
						break;
					}
				}
				
				if (!somethingDied)
				{
					snd_miss.play(); // This sound plays when the player misses
				}
				//Switch turns and check winner
				if(!player1){
					if(tankInt != index){
						tankList.get(index).isSelected = false;
					}
					unregisterItems(tankList);
					//player1 = false;
					fire = false;
					if(player1win() && isOnline == false){
						turn2mes = false;
						gameDialog(4);
					}else{
						turn2mes = true;
					}
				}else{
					if(tankInt != index){
						tankList2.get(index).isSelected = false;
					}
					unregisterItems(tankList2);
					//player1 = true;
					fire = false;
					if(player2win()){
						turn1mes = false;
						gameDialog(4);
					}else{
						turn1mes = true;
					}
					
				}
				if(!player1){
					camera.setRotation(180f);
				}else{
					camera.setRotation(0f);
				}
				scene.detachChild(bullet);
				
				//boolean pturn = !player1;
				if(player1){
					pIDturn = phoneID1;
				}else{
					pIDturn = phoneID2;
				}
				int win = -1;
				if(isOnline){
					setTankXYList(tankList,tankList2);
					setMineXYList(mineList,mineList2);
					setSoldierXYList(soldList,soldList2);
					if(player1win() || player2win()){
						pIDturn = "null";
						if(player1win()){
							win = 1;
						}else{
							win = 2;
						}
					}
					sendData2(tankXList,tankYList,tankXList2,tankYList2,mineXList,mineYList,mineXList2,mineYList2,win,-1);				}
			}
        };
        bullet.registerEntityModifier(moveBullet);
        scene.attachChild(bullet);
       
        
     }       
	void sendData(LinkedList <Integer> p1TanksX,LinkedList <Integer> p1TanksY,LinkedList <Integer> p2TanksX,LinkedList <Integer> p2TanksY,
				LinkedList <Integer> p1MinesX,LinkedList <Integer> p1MinesY,LinkedList <Integer> p2MinesX,LinkedList <Integer> p2MinesY,
				float targetX,float targetY){
		
		gameState = new GameState(p1TanksX,p1TanksY,p2TanksX,p2TanksY,p1MinesX,p1MinesY,p2MinesX,p2MinesY, soldXList,soldYList,soldXList2,soldYList2,
				targetX, targetY,phoneID1,phoneID2,user1,user2,pIDturn);		
		System.out.println(gameState.user1name + "," + gameState.user1ID);
		System.out.println(gameState.user2name + "," + gameState.user2ID);
		Thread client = new Thread(new ClientThread(this,gameState,1));
	    client.start();
	    try{
	    	client.join();
	    }
	    catch(Exception e){
	    	
	    }
	    
	    Intent intent = new Intent(getBaseContext(), ActiveGameMenuActivity.class);
    	startActivity(intent);
		
	}
	
	void sendData2(LinkedList <Integer> p1TanksX,LinkedList <Integer> p1TanksY,LinkedList <Integer> p2TanksX,LinkedList <Integer> p2TanksY,
			LinkedList <Integer> p1MinesX,LinkedList <Integer> p1MinesY,LinkedList <Integer> p2MinesX,LinkedList <Integer> p2MinesY,
			float targetX,float targetY){
	
	gameState = new GameState(p1TanksX,p1TanksY,p2TanksX,p2TanksY,p1MinesX,p1MinesY,p2MinesX,p2MinesY, soldXList,soldYList,soldXList2,soldYList2,
								targetX, targetY,phoneID1,phoneID2,user1,user2,pIDturn);
	gameState.gameID=this.gameID;
	System.out.println(gameState.user1name + "," + gameState.user1ID);
	System.out.println(gameState.user2name + "," + gameState.user2ID);
	Thread client = new Thread(new ClientThread(this,gameState,2));
    client.start();
    try{
    	client.join();
    }
    catch(Exception e){
    	
    }
    
    Intent intent = new Intent(getBaseContext(), ActiveGameMenuActivity.class);
	startActivity(intent);
	
}
	
	void sendData3(LinkedList <Integer> p1TanksX,LinkedList <Integer> p1TanksY,LinkedList <Integer> p2TanksX,LinkedList <Integer> p2TanksY,
			LinkedList <Integer> p1MinesX,LinkedList <Integer> p1MinesY,LinkedList <Integer> p2MinesX,LinkedList <Integer> p2MinesY,
			float targetX,float targetY){
	pIDturn = phoneID1;
	gameState = new GameState(p1TanksX,p1TanksY,p2TanksX,p2TanksY,p1MinesX,p1MinesY,p2MinesX,p2MinesY, soldXList,soldYList,soldXList2,soldYList2,
			targetX, targetY,phoneID1,phoneID2,user1,user2,pIDturn);	
	System.out.println(gameState.user1name + "," + gameState.user1ID);
	System.out.println(gameState.user2name + "," + gameState.user2ID);
	Thread client = new Thread(new ClientThread(this,gameState,5));
    client.start();
    try{
    	client.join();
    }
    catch(Exception e){
    	
    }
    	Intent intent = new Intent(getBaseContext(), ActiveGameMenuActivity.class);
    	startActivity(intent);
	}
	//Convert TanKList to XandY lists
	public void setTankXYList(LinkedList<Tank> list,LinkedList<Tank> list2){
		tankXList.clear();
		tankYList.clear();
		tankXList2.clear();
		tankYList2.clear();
		if(list != null && list2 != null){
			for(int i = 0; i < list.size(); i++){
				tankXList.add((int) list.get(i).getX());
				tankYList.add((int) list.get(i).getY());
			}
			
			for(int i = 0; i < list2.size(); i++){
				tankXList2.add((int) list2.get(i).getX());
				tankYList2.add((int) list2.get(i).getY());
			}
		}
	}
	
	public void setMineXYList(LinkedList<Sprite> list,LinkedList<Sprite> list2){
		mineXList.clear();
		mineYList.clear();
		mineXList2.clear();
		mineYList2.clear();
		if(list != null && list2 != null){
			for(int i = 0; i < list.size(); i++){
				mineXList.add((int) list.get(i).getX());
				mineYList.add((int) list.get(i).getY());
			}
			
			for(int i = 0; i < list2.size(); i++){
				mineXList2.add((int) list2.get(i).getX());
				mineYList2.add((int) list2.get(i).getY());
			}
		}
	}
	
	public void setSoldierXYList(LinkedList<Soldier> list,LinkedList<Soldier> list2){
		soldXList.clear();
		soldYList.clear();
		soldXList2.clear();
		soldYList2.clear();
		if(list != null && list2 != null){
			for(int i = 0; i < list.size(); i++){
				soldXList.add((int) list.get(i).getX());
				soldYList.add((int) list.get(i).getY());
			}
			
			for(int i = 0; i < list2.size(); i++){
				soldXList2.add((int) list2.get(i).getX());
				soldYList2.add((int) list2.get(i).getY());
			}
		}
	}
	
	public void setNewTankList(LinkedList<Integer> listX,LinkedList<Integer> listY,LinkedList<Integer> listX2,LinkedList<Integer> listY2){
		
		if(listX != null && listY != null){
			tankList.clear();
			for(int i = 0; i < listX.size(); i++){
				tank = new Tank(listX.get(i) ,listY.get(i),50,50, this.mTankTextureRegion, this.getVertexBufferObjectManager());
				tankList.add(tank);
				
				scene.attachChild(tank);
			}
			
		}
		
		if(listX2 != null && listY2 != null){
			tankList2.clear();
			for(int i = 0; i < listX2.size(); i++){
				tank = new Tank(listX2.get(i) ,listY2.get(i),50,50, this.mTankTextureRegion, this.getVertexBufferObjectManager());
				tankList2.add(tank);
				tank.setRotation(180);
				scene.attachChild(tank);
			}
		}
	}
	
	public void setNewMineList(LinkedList<Integer> listX,LinkedList<Integer> listY,LinkedList<Integer> listX2,LinkedList<Integer> listY2){
		
		if(listX != null && listY != null){
			mineList.clear();
			for(int i = 0; i < listX.size(); i++){
				mine = new Sprite(listX.get(i),listY.get(i),50,50, this.mMineTextureRegion, this.getVertexBufferObjectManager());
				mineList.add(mine);
				scene.attachChild(mine);
			}
		}
		
		if(listX2 != null && listY2 != null){
			mineList2.clear();
			for(int i = 0; i < listX2.size(); i++){
				mine = new Sprite(listX2.get(i),listY2.get(i),50,50, this.mMineTextureRegion, this.getVertexBufferObjectManager());
				mineList2.add(mine);
				scene.attachChild(mine);

			}
		}
		
	}
	
public void setNewSoldierList(LinkedList<Integer> listX,LinkedList<Integer> listY,LinkedList<Integer> listX2,LinkedList<Integer> listY2){
		
		if(listX != null && listY != null){
			soldList.clear();
			for(int i = 0; i < listX.size(); i++){
				soldier = new Soldier(touchX ,touchY,30,30, this.SoldierTextureRegion, this.getVertexBufferObjectManager());
				soldList.add(soldier);
				scene.attachChild(soldier);
			}
		}
		
		if(listX2 != null && listY2 != null){
			soldList2.clear();
			for(int i = 0; i < listX2.size(); i++){
				soldier = new Soldier(touchX ,touchY,30,30, this.SoldierTextureRegion, this.getVertexBufferObjectManager());
				soldList2.add(soldier);
				scene.attachChild(soldier);

			}
		}
		
	}
	//creates game state
	public void setGameState(GameState gs){
		setNewTankList(gs.p1TanksX,gs.p1TanksY,gs.p2TanksX,gs.p2TanksY);
		setNewMineList(gs.p2MinesX,gs.p2MinesY,gs.p2MinesX,gs.p2MinesY);
		setNewSoldierList(gs.p2MinesX,gs.p2MinesY,gs.p2MinesX,gs.p2MinesY);
		user1 = gs.user1name;
		phoneID1 = gs.user1ID;
		user2 = gs.user2name;
		phoneID2 = gs.user2ID;
		pIDturn = gs.pIDturn;
		this.gameID = gs.gameID;
	}
/********************************************************************************************************************/	
	
	
	
/*****************************************************
* Tank Class when selected is highlighted by
* a rectangle object
******************************************************/
	class Tank extends Sprite implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		float originX, originY;
		boolean removed,isSelected, validMove;
		Rectangle outline;
		public Tank(float pX, float pY, float pWidth, float pHeight,
				ITextureRegion mTankTextureRegion, VertexBufferObjectManager  vbom) {
			super(pX, pY, pWidth, pHeight, mTankTextureRegion, vbom);
			originX = pX;
			originY = pY;
			removed = false;
			validMove = false;
			outline = new Rectangle (pX-2,pY-2,pWidth+4,pHeight+4, vbom);
			outline.setVisible(false);
			outline.setColor(1,1,0);
			UnitAllocationActivity.scene.attachChild(outline);
		}

		public Tank(float centerX, int i, ITextureRegion mTankTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager) {
			super(centerX,i,mTankTextureRegion,vertexBufferObjectManager);
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
/*****************************************************
* Soldier Class when selected is highlighted by
* a rectangle object
******************************************************/
class Soldier extends Sprite implements Serializable {
	/**
 * 
 */
private static final long serialVersionUID = 1L;
	float originX, originY;
	Sprite aimer;
	Sprite tester;
	Runnable aimRun;
	Thread aimThread;
	int runOnce;
	int aimAngle;
	boolean aimContinue = false;
	boolean removed,isSelected, validMove;
	Rectangle outline;
	public Soldier(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion mTankTextureRegion, VertexBufferObjectManager  vbom) {
		super(pX, pY, pWidth, pHeight, mTankTextureRegion, vbom);
		originX = pX;
		originY = pY;
		removed = false;
		validMove = false;
		outline = new Rectangle (pX-2,pY-2,pWidth+4,pHeight+4, vbom);
		outline.setVisible(false);
		outline.setColor(1,1,0);
		UnitAllocationActivity.scene.attachChild(outline);
		runOnce = 0;
	}

	public Soldier(float centerX, int i, ITextureRegion mTankTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager) {
		super(centerX,i,mTankTextureRegion,vertexBufferObjectManager);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		float posY = pSceneTouchEvent.getY() - this.getHeight() / 2;
		float posX = pSceneTouchEvent.getX() - this.getWidth() / 2;
	if(fire){
		if(UnitAllocationActivity.player1){
			 checkOthersSelected_sold(soldList);
		 }else{
			 checkOthersSelected_sold(soldList2);
		 }	 
		 isSelected = true;
		 outline.setVisible(true);
		 if(gameStart){
			 unregisterItems_sold(soldList);
			 unregisterItems_sold(soldList2);
			 soldierAim();
		 }  
	}else{
		if(pSceneTouchEvent.isActionDown()){
			 this.originX = posX;
			 this.originY = posY;
             validMove = true;
			 if(UnitAllocationActivity.player1){
					 checkOthersSelected_sold(soldList);
				 }else{
					 checkOthersSelected_sold(soldList2);
				 }
			 	isSelected = true;
				 outline.setVisible(true);
				 return true;
		}
		else if(pSceneTouchEvent.isActionMove()){
			 if(player1){
				 checkOthersSelected_sold(soldList);
			 }else{
				 checkOthersSelected_sold(soldList2);
			 }
			 isSelected = true;
			if (validMove) {
				if(player1){
					for (int i = 0; i < soldList.size(); i++) {
						if (this != soldList.get(i)) {
							if (this.collidesWith(soldList.get(i))) {
								this.setPosition(this.originX, this.originY);
								this.outline.setPosition(this.originX - 2, this.originY - 2);
								validMove = false;
								return true;
							}
						}
					}
					for (int i = 0; i < tankList.size(); i++) {
						if (this.collidesWith(tankList.get(i))) {
							this.setPosition(this.originX, this.originY);
							this.outline.setPosition(this.originX - 2, this.originY - 2);
							validMove = false;
							return true;
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
					for (int i = 0; i < soldList2.size(); i++) {
						if (this != soldList2.get(i)) {
							if (this.collidesWith(soldList2.get(i))) {
								this.setPosition(this.originX, this.originY);
								this.outline.setPosition(this.originX - 2, this.originY - 2);
								validMove = false;
								return true;
							}
						}
					}
					for (int i = 0; i < tankList2.size(); i++) {
						if (this.collidesWith(tankList2.get(i))) {
							this.setPosition(this.originX, this.originY);
							this.outline.setPosition(this.originX - 2, this.originY - 2);
							validMove = false;
							return true;
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
			 checkOthersSelected_sold(soldList);
		 }else{
			 checkOthersSelected_sold(soldList2);
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
	void soldierAim(){
		if(runOnce == 0){
			aimer = new Sprite(originX+(this.mWidth/2),originY-(this.mHeight/2)+10,120,30, aimTextureRegion, this.getVertexBufferObjectManager());
			//tester = new Sprite(0f,200f,20,20,mExplosionTextureRegion, this.getVertexBufferObjectManager());
			if(this.getRotation() == 180){
				aimer.setRotation(180);
			}
			scene.attachChild(aimer);
			//scene.attachChild(tester);
			aimer.setRotationCenter(0,aimer.getHeight()/2);
			aimContinue = true;
			runOnce++;
			startAimer();
		}
	}
	
	void stopAimer(){
		scene.detachChild(aimer);
		aimContinue = false;
		runOnce = 0;
	}
	
	float getAimPosX(){
		float x = (float) (aimer.getX() + aimer.getWidth()*Math.cos(Math.toRadians(aimAngle)));
		return x;
	}
	
	float getAimPosY(){
		float y = (float) (aimer.getY() - aimer.getWidth()*Math.sin(Math.toRadians(aimAngle)));
		return y;
	}
	
	float getAimPosX_2(){
		float x = (float) (aimer.getX() + aimer.getWidth()*Math.cos(Math.toRadians(aimAngle)));
		return x;
	}
	
	float getAimPosY_2(){
		float y = (float) (aimer.getY() + aimer.getWidth()*Math.sin(Math.toRadians(aimAngle)));
		return y;
	}
	
	void startAimer(){
		aimRun = new Runnable() {
	        @Override
	        public void run() {
	           while(aimContinue){
	        	   if(getRotation() == 0){
		        	   for(int i = 0; i < 180; i++){
		        		   if(!aimContinue){
		        			   break;
		        		   }
		        		   aimer.setRotation(-i);
		        		   aimAngle = i;
//		        		   tester.setX(getAimPosX());
//		        		   tester.setY(getAimPosY());
		        		   try {
		        			   Thread.sleep(3);
		        		   }
		        		   catch (InterruptedException e) {
		        			   e.printStackTrace();
		        		   }
		        	   }
		        	   for(int i = 180; i > 0; i--){
		        		   if(!aimContinue){
		        			   break;
		        		   }
		        		   aimer.setRotation(-i);
		        		   aimAngle = i;
//		        		   tester.setX(getAimPosX());
//		        		   tester.setY(getAimPosY());
		        		   try {
		        			   Thread.sleep(3);
		        		   } 
		        		   catch (InterruptedException e) {
		        			   e.printStackTrace();
		        		   }
		        	   }
	        	   }
	        	   else{
	        		   for(int i = 0; i < 180; i++){
		        		   if(!aimContinue){
		        			   break;
		        		   }
		        		   aimer.setRotation(i);
		        		   aimAngle = i;
//		        		   tester.setX(getAimPosX());
//		        		   tester.setY(getAimPosY_2());
		        		   try {
		        			   Thread.sleep(3);
		        		   }
		        		   catch (InterruptedException e) {
		        			   e.printStackTrace();
		        		   }
		        	   }
		        	   for(int i = 180; i > 0; i--){
		        		   if(!aimContinue){
		        			   break;
		        		   }
		        		   aimer.setRotation(i);
		        		   aimAngle = i;
//		        		   tester.setX(getAimPosX());
//		        		   tester.setY(getAimPosY_2());
		        		   try {
		        			   Thread.sleep(3);
		        		   } 
		        		   catch (InterruptedException e) {
		        			   e.printStackTrace();
		        		   }
		        	   }
	        	   }
	           }
	        }
	    };
	    aimThread = new Thread(aimRun);
	    aimThread.start();
	}
};
}
