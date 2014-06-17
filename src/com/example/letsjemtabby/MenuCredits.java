package com.example.letsjemtabby;

import java.io.IOException;

import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
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

import android.content.Intent;

import com.example.letsjemtabby.utils.Costanti;

public class MenuCredits extends SimpleBaseGameActivity{

	private Camera mCamera;
	private Scene mScena;
	private Font mFontTitolo;
	private Text mTitolo;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private BitmapTextureAtlas mBitmapTextureAtlas2;
	private TextureRegion mVolumeTexturePresente;
	private TextureRegion mVolumeTextureAssente;
	private Sprite mVolumeSprite;
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
	protected void onCreateResources() {
		FontFactory.setAssetBasePath("font/");

		final ITexture fontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mFontTitolo = FontFactory.createFromAsset(this.getFontManager(), fontTexture, this.getAssets(), "CHUBBY.TTF", 52, true, android.graphics.Color.BLACK);
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
			if(Costanti.mMusic==null)
			{
				Costanti.mMusic =  MusicFactory.createMusicFromAsset(getMusicManager(), this, "wagner_the_ride_of_the_valkyries.ogg");
				Costanti.mMusic.setLooping(true);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected Scene onCreateScene() {
		this.mScena = new Scene();
		this.mScena.setBackground(new Background(new Color(255,191,0)));
		Intent i = getIntent();
		i.getBundleExtra("");
		this.mTitolo = new Text(Costanti.X_TITOLO,Costanti.Y_TITOLO,this.mFontTitolo,"Crediti",getVertexBufferObjectManager());
		this.mScena.attachChild(mTitolo);
		addVolume();
		return this.mScena;
	}

	private void addVolume() {
		this.mVolumeSprite = new Sprite(Costanti.CAMERA_WIDTH-128,Costanti.CAMERA_HEIGHT-128,this.mVolumeTexturePresente,this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.isActionDown())
				{
					if(Costanti.mMusic.getVolume()!=0)
					{
						Costanti.mMusic.setVolume(0,0);
						this.setTextureRegion(MenuCredits.this.mVolumeTextureAssente);
					}
					else if(Costanti.mMusic.getVolume()==0)
					{
						Costanti.mMusic.setVolume(1,1);
						this.setTextureRegion(MenuCredits.this.mVolumeTexturePresente);
					}
				}
				return true;
			}
		};
		this.mScena.attachChild(this.mVolumeSprite);
		this.mScena.registerTouchArea(this.mVolumeSprite);
		
	}

}
