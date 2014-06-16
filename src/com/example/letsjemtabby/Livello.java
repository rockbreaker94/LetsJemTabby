package com.example.letsjemtabby;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import com.example.letsjemtabby.utils.Costanti;

import android.util.Log;

public class Livello extends SimpleBaseGameActivity{

	private Camera mCamera;
	private Sprite mButtonSprite;
	private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	private ITextureRegion mPad;
	private ITextureRegion mPadCliccato;
	private TextureRegion mPadDisabled;
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
	protected void onCreateResources() {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 130, 130);
		this.mPad = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "pad_orange.png");
		this.mBitmapTextureAtlas.load();
			
	}

	@Override
	protected Scene onCreateScene() {
		Scene scena = new Scene();
		scena.setBackground(new Background(new Color(255,191,0)));
		this.mButtonSprite = new ButtonSprite(100,100,this.mPad,this.getVertexBufferObjectManager(),new OnClickListener(){

			@Override
			public void onClick(ButtonSprite pButtonSprite,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				Log.e("HAI CLICCATO!!", "Hai cliccato il buttonSprite");
				
			}
				
		});
		scena.attachChild(mButtonSprite);
		scena.registerTouchArea(mButtonSprite);
		return scena;
	}

}
