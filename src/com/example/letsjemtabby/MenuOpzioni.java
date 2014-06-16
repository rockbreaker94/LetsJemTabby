package com.example.letsjemtabby;

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
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.opengl.GLES20;

import com.example.letsjemtabby.utils.Costanti;

public class MenuOpzioni extends SimpleBaseGameActivity implements IOnMenuItemClickListener {
	private static final int MENU_SOUND = 0;
	private static final int MENU_LANGUAGE = 1;
	private static final int MENU_VOLUME = 2;
	private Camera mCamera;
	private Scene mScena;
	private Text mTitolo;
	private Font mFont;
	private Font mFontTitolo;
	private Font mFontOpzioni;
	private Text mOpzioniSound;
	private MenuScene mMenuScene;
	@Override
	public EngineOptions onCreateEngineOptions() {
		if(Costanti.CAMERA_WIDTH>getWindowManager().getDefaultDisplay().getWidth())
			Costanti.CAMERA_WIDTH=getWindowManager().getDefaultDisplay().getWidth();
		if(Costanti.CAMERA_HEIGHT>getWindowManager().getDefaultDisplay().getHeight())
			Costanti.CAMERA_HEIGHT=getWindowManager().getDefaultDisplay().getHeight();
		
		this.mCamera = new Camera(0, 0, Costanti.CAMERA_WIDTH, Costanti.CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight()), this.mCamera);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		return false;
	}

	@Override
	protected void onCreateResources() {
		FontFactory.setAssetBasePath("font/");

		final ITexture fontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		final ITexture fontTextureOpzioni = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mFontTitolo = FontFactory.createFromAsset(this.getFontManager(), fontTexture, this.getAssets(), "CHUBBY.TTF", 52, true, android.graphics.Color.BLACK);
		this.mFontOpzioni = FontFactory.createFromAsset(this.getFontManager(), fontTextureOpzioni, this.getAssets(),"CHUBBY.TTF",38,true,android.graphics.Color.BLACK);
		this.mFontOpzioni.load();
		this.mFontTitolo.load();
		
	}

	@Override
	protected Scene onCreateScene() {
		this.mScena = new Scene();
		this.mMenuScene = createMenuScene();
		this.mScena.setChildScene(this.mMenuScene, false, true, true);
		this.mScena.setBackground(new Background(new Color(255,191,0)));
		this.mTitolo = new Text(Costanti.X_TITOLO,Costanti.Y_TITOLO, mFontTitolo, "Opzioni", getVertexBufferObjectManager());
		mScena.attachChild(mTitolo);
		return this.mScena;
	}
	protected MenuScene createMenuScene() {
		final MenuScene menuScene = new MenuScene(this.mCamera);

		final IMenuItem playMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_SOUND, this.mFontOpzioni, "Sound:", this.getVertexBufferObjectManager()), new Color(1,0,0), new Color(0,0,0));
		playMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(playMenuItem);

		final IMenuItem optionsMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_LANGUAGE, this.mFontOpzioni, "Language:", this.getVertexBufferObjectManager()), new Color(1,0,0), new Color(0,0,0));
		optionsMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(optionsMenuItem);
		
		final IMenuItem creditsMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_VOLUME, this.mFontOpzioni, "Volume:", this.getVertexBufferObjectManager()), new Color(1,0,0), new Color(0,0,0));
		creditsMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(creditsMenuItem);
		

		menuScene.buildAnimations();

		menuScene.setBackgroundEnabled(false);

		menuScene.setOnMenuItemClickListener(this);
		return menuScene;
	}

}
