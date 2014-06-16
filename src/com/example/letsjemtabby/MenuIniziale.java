package com.example.letsjemtabby;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import com.example.letsjemtabby.utils.Costanti;

import android.content.Intent;
import android.opengl.GLES20;


public class MenuIniziale extends SimpleBaseGameActivity implements IOnMenuItemClickListener {

	protected static final int MENU_QUIT = 3;
	private static final int MENU_PLAY = 0;
	private static final int MENU_OPTIONS = 1;
	private static final int MENU_CREDITS = 2;

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;

	protected Scene mMainScene;

	private BitmapTextureAtlas mBitmapTextureAtlas;

	private Font mFont;
	private Music mMusic;
	protected MenuScene mMenuScene;
	private Font mFontTitolo;
	private Text mTitolo;
	private TextureRegion mVolumeTextureAssente;
	private Sprite mVolumeSprite;
	private TextureRegion mVolumeTexturePresente;
	private BitmapTextureAtlas mBitmapTextureAtlas2;
	@Override
	public EngineOptions onCreateEngineOptions() {
		if(Costanti.CAMERA_WIDTH>getWindowManager().getDefaultDisplay().getWidth())
			Costanti.CAMERA_WIDTH=getWindowManager().getDefaultDisplay().getWidth();
		if(Costanti.CAMERA_HEIGHT>getWindowManager().getDefaultDisplay().getHeight())
			Costanti.CAMERA_HEIGHT=getWindowManager().getDefaultDisplay().getHeight();
		this.mCamera = new Camera(0, 0, Costanti.CAMERA_WIDTH, Costanti.CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight()), this.mCamera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		return engineOptions;
	}

	@Override
	public void onCreateResources() {
		FontFactory.setAssetBasePath("font/");
		final ITexture fontTitolo = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		final ITexture fontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mFontTitolo = FontFactory.createFromAsset(this.getFontManager(), fontTitolo, this.getAssets(), "PermanentMarker.ttf", 72, true, android.graphics.Color.WHITE);
		this.mFont = FontFactory.createFromAsset(this.getFontManager(), fontTexture, this.getAssets(), "CHUBBY.TTF", 48, true, android.graphics.Color.WHITE);
		this.mFont.load();
		this.mFontTitolo.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		this.mBitmapTextureAtlas2 = new BitmapTextureAtlas(this.getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		this.mVolumeTexturePresente = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "volume-presente.png", 0, 0);
		this.mVolumeTextureAssente = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "volume-tolto.png", 0, 0);
		this.mBitmapTextureAtlas2.load();
		this.mBitmapTextureAtlas.load();
		
		MusicFactory.setAssetBasePath("mfx/");
		try {
			this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "wagner_the_ride_of_the_valkyries.ogg");
			this.mMusic.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}
	}	

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		if(!this.mMusic.isPlaying())
			this.mMusic.play();
		
		this.mMenuScene = this.createMenuScene();
		this.mMainScene = new Scene();
		this.mMainScene.setBackground(new Background(new Color(255,191,0)));
		this.mTitolo = new Text(30,40,mFontTitolo,"Let's Jem Tabby!",this.getVertexBufferObjectManager());
		this.mMainScene.attachChild(this.mTitolo);
		this.mMainScene.setChildScene(this.mMenuScene, false, true, false);
		addVolume();
		return this.mMainScene;
	}

	private void addVolume() {
		this.mVolumeSprite = new Sprite(Costanti.CAMERA_WIDTH-128,Costanti.CAMERA_HEIGHT-128,this.mVolumeTexturePresente,this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.isActionDown())
				{
					if(MenuIniziale.this.mMusic.getVolume()!=0)
					{
						MenuIniziale.this.mMusic.setVolume(0,0);
						this.setTextureRegion(MenuIniziale.this.mVolumeTextureAssente);
					}
					else if(MenuIniziale.this.mMusic.getVolume()==0)
					{
						MenuIniziale.this.mMusic.setVolume(1,1);
						this.setTextureRegion(MenuIniziale.this.mVolumeTexturePresente);
					}
				}
				return true;
			}
		};
		this.mMainScene.attachChild(this.mVolumeSprite);
		this.mMainScene.registerTouchArea(this.mVolumeSprite);
	}

	@Override
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
			case MENU_PLAY:
				Intent i = new Intent(MenuIniziale.this,MenuInterno.class);
				startActivity(i);
				return true;
			case MENU_OPTIONS:
				Intent iOptions = new Intent(MenuIniziale.this,MenuOpzioni.class);
				startActivity(iOptions);
				return true;
			case MENU_CREDITS:
				Intent iCredits = new Intent(MenuIniziale.this,MenuCredits.class);
				startActivity(iCredits);
				return true;
			case MENU_QUIT:
				/* End Activity. */
				this.finish();
				return true;
			default:
				return false;
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	protected MenuScene createMenuScene() {
		final MenuScene menuScene = new MenuScene(this.mCamera);

		final IMenuItem playMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_PLAY, this.mFont, "Play", this.getVertexBufferObjectManager()), new Color(1,0,0), new Color(0,0,0));
		playMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(playMenuItem);

		final IMenuItem optionsMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_OPTIONS, this.mFont, "Options", this.getVertexBufferObjectManager()), new Color(1,0,0), new Color(0,0,0));
		optionsMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(optionsMenuItem);
		
		final IMenuItem creditsMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_CREDITS, this.mFont, "Credits", this.getVertexBufferObjectManager()), new Color(1,0,0), new Color(0,0,0));
		creditsMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(creditsMenuItem);
		
		final IMenuItem quitMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_QUIT, this.mFont, "Quit", this.getVertexBufferObjectManager()), new Color(1,0,0), new Color(0,0,0));
		quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(quitMenuItem);

		menuScene.buildAnimations();

		menuScene.setBackgroundEnabled(false);

		menuScene.setOnMenuItemClickListener(this);
		return menuScene;
	}

}
