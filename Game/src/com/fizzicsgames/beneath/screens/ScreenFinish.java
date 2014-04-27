package com.fizzicsgames.beneath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.fizzicsgames.beneath.BeneathGame;
import com.fizzicsgames.beneath.Res;
import com.fizzicsgames.beneath.game.GameConsole;
import com.fizzicsgames.beneath.game.OneKeyListener;


public class ScreenFinish extends BgScreen {

	private GameConsole console;
	private Image overlay;
	
	public ScreenFinish() {
		super();
	}
	
	@Override
	public void onShow() {
		super.onShow();
		reset();
	}
	
	public void reset() {
		stage.clear();
		input = new InputMultiplexer();
		input.addProcessor(stage);
		Gdx.input.setInputProcessor(input);
		
		console = new GameConsole();
		stage.addActor(console);
		console.setCenter();
		
		// Overlay
		overlay = new Image(Res.AtlasID.Main.getAtlas().findRegion("blackOverlay"));
		overlay.setSize(stage.getWidth(), stage.getHeight());
		stage.addActor(overlay);
		
		overlay.addAction(Actions.sequence(
			Actions.alpha(0f, 1f),
			Actions.visible(false),
			Actions.delay(0.5f),
			Actions.run(new Runnable() {
				@Override
				public void run() {
					Array<Label> lines = console.postMultiline("This is great! You've done it! There are lots of things to be improved, lots of interesting features to implement. Maybe one day I'll make a good puzzle game out of this one, who knows.");
					console.post("");
					console.post("Thank you for playing, I hope you enjoyed it!");
					console.post("");
					console.post("ENTER to continue...");
					stage.addListener(new OneKeyListener(Keys.ENTER){
						@Override
						public void fire() {
							
							overlay.addAction(Actions.sequence(
								Actions.visible(true),
								Actions.alpha(1f, 1f),
								Actions.run(new Runnable() {
									@Override
									public void run() {
										BeneathGame.setScreen(ScreenMainMenu.class);
									}
								})
							));
						}
					});
				}
			})
		));
	}
	
	@Override
	public void onHide() {
		super.onHide();
	}
}
