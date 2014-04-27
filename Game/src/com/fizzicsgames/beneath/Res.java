package com.fizzicsgames.beneath;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.fizzicsgames.beneath.game.SoundSystem;

public class Res {

	public static enum AtlasID {
		Main("main.tex", "Main");
		private String fileName, folderName;
		private TextureAtlas atlas;
		
		private AtlasID(String fileName, String folderName) {
			this.fileName = fileName;
			this.folderName = folderName;
		}
		
		public String getFileName() {
			return fileName;
		}
		
		public String getFolderName() {
			return folderName;
		}
		
		public void loadAtlas() {
			atlas = new TextureAtlas("gfx/" + fileName);
		}
		
		public TextureAtlas getAtlas() {
			return atlas;
		}
	}
	
	public static Skin skin;
	public static BitmapFont font, fontCourier;
	public static AssetManager assets;
	
	public static void init() {
		assets = new AssetManager();
		assets.setLoader(TiledMap.class, new TmxMapLoader());
		
		// Load missions
		assets.finishLoading();
		
		// Load sounds
		SoundSystem.init();
		
		for (AtlasID a : AtlasID.values()) {
			a.loadAtlas();
		}
		
		// Font
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		fontCourier = new BitmapFont(Gdx.files.internal("courier.fnt"));
		
		// Skin
		skin = new Skin();
		
		// Main window
		TextureAtlas main = AtlasID.Main.getAtlas();
		skin.add("npWindow", new NinePatch(main.findRegion("npWindow"), 5, 32, 42, 5), NinePatch.class);
		skin.add("npConsole", new NinePatch(main.findRegion("npConsole"), 4, 4, 4, 4), NinePatch.class);
		skin.add("npButton", new NinePatch(main.findRegion("npButton"), 4, 4, 4, 4), NinePatch.class);
		skin.add("npTextField", new NinePatch(main.findRegion("npTextField"), 4, 4, 4, 4), NinePatch.class);
		skin.add("npInnerWindow", new NinePatch(main.findRegion("npInnerWindow"), 5, 5, 5, 5), NinePatch.class);
		skin.add("cursor", main.findRegion("cursor"), TextureRegion.class);
		
		skin.add("grid", new Texture(Gdx.files.internal("grid.png")), Texture.class);

		// Label style
		LabelStyle ls = new LabelStyle();
		ls.font = font;
		ls.fontColor = new Color(1f, 1f, 1f, 0.8f);
		skin.add("windowHeader", ls, LabelStyle.class);
		skin.add("default", ls, LabelStyle.class);
		
		// Console style
		ls = new LabelStyle();
		ls.font = fontCourier;
		skin.add("console", ls, LabelStyle.class);
		
		// Text field style
		TextFieldStyle tfs = new TextFieldStyle();
		tfs.background = skin.getDrawable("npTextField");
		tfs.cursor = skin.getDrawable("cursor");
		tfs.font = font;
		tfs.fontColor = Color.WHITE;
		skin.add("default", tfs, TextFieldStyle.class);
		
		// Button style
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = skin.getDrawable("npButton");
		tbs.down = tbs.up;
		tbs.over = tbs.up;
		tbs.pressedOffsetX = 2f;
		tbs.pressedOffsetY = 2f;
		tbs.font = font;
		tbs.fontColor = Color.WHITE;
		skin.add("default", tbs);
	}
}
