package com.fizzicsgames.beneath;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.fizzicsgames.beneath.game.Missions;
import com.fizzicsgames.beneath.screens.BgScreen;
import com.fizzicsgames.beneath.screens.ScreenFinish;
import com.fizzicsgames.beneath.screens.ScreenGame;
import com.fizzicsgames.beneath.screens.ScreenMainMenu;

public class BeneathGame implements ApplicationListener {

	private static BgScreen currentScreen;
	private static Array<BgScreen> screens = new Array<BgScreen>();
	
	@Override
	public void create() {
		
		// Load atlases
		Res.init();
		
		screens.clear();
		screens.add(new ScreenGame());
		screens.add(new ScreenMainMenu());
		screens.add(new ScreenFinish());
		setScreen(ScreenMainMenu.class);
	}

	public static void startLevel(int i) {
		if (i <= Missions.count) {
			ScreenGame.mission = i;
			setScreen(ScreenGame.class);
		} else { // show final screen
			setScreen(ScreenFinish.class);
		}
		
	}
	
	public static void setScreen(Class c) {
		for (BgScreen s : screens) {
			if (s.getClass() == c) {
				setScreen(s);
				return;
			}
		}
		
		throw new GdxRuntimeException("Screen '" + c + "' wasn't found");
	}
	
	private static void setScreen(BgScreen s) {
		if (currentScreen != null)
			currentScreen.onHide();
		currentScreen = s;
		currentScreen.onShow();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearDepthf(1f);

		if (currentScreen != null) {
			currentScreen.update();
			currentScreen.render();
		}
	}

	@Override
	public void resize(int width, int height) {
		if (currentScreen != null)
			currentScreen.onResize(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	@Override
	public void dispose() {
		
	}
}
