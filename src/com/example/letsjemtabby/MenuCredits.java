package com.example.letsjemtabby;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import com.example.letsjemtabby.utils.Costanti;

public class MenuCredits extends SimpleBaseGameActivity{

	private Camera mCamera;
	private Scene mScena;
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
		this.mFontTitolo = FontFactory.createFromAsset(this.getFontManager(), fontTexture, this.getAssets(), "CHUBBY.TTF", 52, true, android.graphics.Color.BLACK);
		this.mFontTitolo.load(); 
		
	}

	@Override
	protected Scene onCreateScene() {
		this.mScena = new Scene();
		this.mScena.setBackground(new Background(new Color(255,191,0)));
		this.mTitolo = new Text(Costanti.X_TITOLO,Costanti.Y_TITOLO,this.mFontTitolo,"Crediti",getVertexBufferObjectManager());
		this.mScena.attachChild(mTitolo);
		return this.mScena;
	}

}
