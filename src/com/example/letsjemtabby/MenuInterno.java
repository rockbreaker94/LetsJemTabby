package com.example.letsjemtabby;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
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

import com.example.letsjemtabby.utils.Costanti;

import android.content.Intent;

public class MenuInterno extends SimpleBaseGameActivity{
	private static final float FONT_SIZE = 24;
	private Camera mCamera;
	float margineSx = (Costanti.CAMERA_WIDTH/100)*1;
	float distanzaQ = ((Costanti.CAMERA_WIDTH)/100)*2;
	float larghezzaQ = ((Costanti.CAMERA_WIDTH-((margineSx*2)+(distanzaQ*4)))/5);
	float margineSuperiore = (Costanti.CAMERA_HEIGHT/100)*17;
	protected Scene mMainScene;
	int mSchiacciato=0;

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private BitmapTextureAtlas mBitmapTextureAtlas2;
	private Font mFont;
	private TextureRegion mPad;
	private Scene mScena;
	private TextureRegion mPadCliccato;
	private Font mFontTitolo;
	private Text mTitolo;

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
		FontFactory.setAssetBasePath("font/");

		final ITexture fontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		final ITexture fontTextureTitolo = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

		this.mFont = FontFactory.createFromAsset(this.getFontManager(), fontTexture, this.getAssets(), "CHUBBY.TTF", 48, true, android.graphics.Color.BLACK);
		this.mFont.load();
		this.mFontTitolo = FontFactory.createFromAsset(this.getFontManager(), fontTextureTitolo, this.getAssets(), "CHUBBY.TTF", 52, true, android.graphics.Color.BLACK);
		this.mFontTitolo.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		if(larghezzaQ>=130)
		{
			this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 130, 130, TextureOptions.BILINEAR);
			this.mBitmapTextureAtlas2 =  new BitmapTextureAtlas(this.getTextureManager(), 130, 130, TextureOptions.BILINEAR);
		}
		else if(larghezzaQ<130)
		{
			this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 100, 100, TextureOptions.BILINEAR);
			this.mBitmapTextureAtlas2 = new BitmapTextureAtlas(this.getTextureManager(), 100, 100, TextureOptions.BILINEAR);
		}
		this.mPad = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "pad_orange.png", 0, 0);
		this.mPadCliccato = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "pad_orange_clicked.png", 0, 0);
		this.mBitmapTextureAtlas2.load();
		this.mBitmapTextureAtlas.load();
		
	}

	@Override
	protected Scene onCreateScene() {
		this.mScena = new Scene();
		this.mScena.setBackground(new Background(new Color(255,191,0)));
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mTitolo = new Text(Costanti.X_TITOLO,Costanti.Y_TITOLO,this.mFontTitolo,"Livelli",getVertexBufferObjectManager());
		for(int i=0;i<5;i++)
		{
			this.addLivello(margineSx,margineSuperiore,i+1);
			this.addNumLivelli((margineSx+12+larghezzaQ/2)-FONT_SIZE, (margineSuperiore+larghezzaQ/2)-FONT_SIZE,i+1);
			margineSx = margineSx + larghezzaQ + distanzaQ;
		}
		margineSuperiore = Costanti.CAMERA_HEIGHT - ((Costanti.CAMERA_HEIGHT/100)*30-margineSuperiore)- larghezzaQ;
		margineSx = (Costanti.CAMERA_WIDTH/100)*1;
		for(int i=5;i<10;i++)
		{
			this.addLivello(margineSx,margineSuperiore,i+1);
			this.addNumLivelli((margineSx+12+larghezzaQ/2)-FONT_SIZE, (margineSuperiore+larghezzaQ/2)-FONT_SIZE,i+1);
			margineSx = margineSx + larghezzaQ + distanzaQ;
		}
		this.mScena.attachChild(this.mTitolo);
		return mScena;
	}



	private void addNumLivelli(float f, float g, final int posizione) {
		final Text numero = new Text(f, g, mFont, ""+posizione ,getVertexBufferObjectManager());
		mScena.attachChild(numero);
	}


	private void addLivello(final float margineSx2, final float margineSuperiore2,final int posizione) {
		final Sprite livello = new Sprite(margineSx2,margineSuperiore2,this.mPad,this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.isActionDown())
				{
					this.setTextureRegion(MenuInterno.this.mPadCliccato);
				}
				mSchiacciato = posizione;
				Intent livello2 = new Intent(MenuInterno.this,Livello.class);
				livello2.putExtra("livello", mSchiacciato);
				MenuInterno.this.startActivity(livello2);
				return true;
			}
		};
		mScena.attachChild(livello);
		mScena.registerTouchArea(livello);
		
	}


}
