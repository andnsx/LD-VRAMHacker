package com.fizzicsgames.beneath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BgScreen {
	
	protected Stage stage;
	protected InputMultiplexer input;
	
	public BgScreen() {
		float wh = (float)Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
		stage = new Stage(wh * 720f, 720f);
	}
	
	public void render() {
		stage.draw();
	}
	
	public void update() {
		stage.act();
	}
	
	public void onShow() {
		
	}
	
	public void onHide() {
		
	}
	
	public void onResize(int w, int h) {
		float wh = (float)w / h;
		stage.setViewport(wh * 720f, 720f, true);
	}
	
	public InputMultiplexer getMultiplexer() {
		return input;
	}
}
