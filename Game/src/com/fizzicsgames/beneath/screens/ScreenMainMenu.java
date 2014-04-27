package com.fizzicsgames.beneath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.fizzicsgames.beneath.BeneathGame;
import com.fizzicsgames.beneath.Res;
import com.fizzicsgames.beneath.game.ActionEnterText;
import com.fizzicsgames.beneath.game.GameConsole;

public class ScreenMainMenu extends BgScreen {

	private GameConsole console;
	private Image overlay;
	protected Array<Label> menu;
	protected int selectedIndex = 0;
	protected InputListener consoleListener;
	private InputListener backListener;
	
	public ScreenMainMenu() {
		float wh = (float)Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
		stage = new Stage(wh * 720f, 720f);
		
	}

	@Override
	public void onShow() {
		super.onShow();
		
		reset(false);
	}
	
	private void reset(final boolean fast) {
		
		if (!fast) {
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
		}
		
		final float multi = fast ? 0f : 1f;
		
		if (fast)
			overlay.setColor(1f, 1f, 1f, 0f);
		
		overlay.addAction(Actions.sequence(
			Actions.alpha(0f, 2f * multi),
			Actions.visible(false),
			Actions.delay(0.5f * multi),
			ActionEnterText.get("menu", console, 0.75f * multi),
			Actions.run(new Runnable() {
				@Override
				public void run() {
					console.post("> menu");
					console.setCommand("");
				}
			}),
			Actions.delay(0.2f * multi),
			Actions.run(new Runnable() {

				@Override
				public void run() {
					// Showing menu here
					console.clearScreen();
					Array<Label> vram = new Array<Label>(), hacker = new Array<Label>();
					vram.add(console.post(" _   _ ______   ___  ___  ___"));
					vram.add(console.post("| | | || ___ \\ / _ \\ |  \\/  |")); 
					vram.add(console.post("| | | || |_/ // /_\\ \\| .  . |"));
					vram.add(console.post("| | | ||    / |  _  || |\\/| |"));
					vram.add(console.post("\\ \\_/ /| |\\ \\ | | | || |  | |"));
					vram.add(console.post(" \\___/ \\_| \\_|\\_| |_/\\_|  |_/"));
					
					hacker.add(console.post(" _                   _               "));
					hacker.add(console.post("| |                 | |              ")); 
					hacker.add(console.post("| |__    __ _   ___ | | __ ___  _ __ "));
					hacker.add(console.post("| '_ \\  / _` | / __|| |/ // _ \\| '__|"));
					hacker.add(console.post("| | | || (_| || (__ |   <|  __/| |  "));
					hacker.add(console.post("|_| |_| \\__,_| \\___||_|\\_\\\\___||_| "));
					
					// Color
					for (Label l : vram)
						l.setColor(Color.RED);
					for (Label l : hacker)
						l.setColor(Color.BLUE);
					
					console.post("");
					menu = new Array<Label>();
					selectedIndex = 0;
					menu.add(console.post("> Play"));
					menu.add(console.post("> About"));
					menu.add(console.post("> Exit"));
					updateMenu();
					console.post("");
					console.post("Use arrows to navigate. Enter to cofirm.");
					
					// Add listener
					stage.addListener(consoleListener = new InputListener(){
						
						
						@Override
						public boolean keyDown(InputEvent event, int keycode) {
							switch (keycode) {
							case Keys.UP:
								--selectedIndex;
								if (selectedIndex < 0)
									selectedIndex = menu.size - 1;
								updateMenu();
								return true;
								
							case Keys.DOWN:
								++selectedIndex;
								if (selectedIndex > menu.size -1)
									selectedIndex = 0;
								updateMenu();
								return true;
								
							case Keys.ENTER:
								selectMenu();
								return true;
							}
							
							return false;
						}
					});
				}
			})
		));
	}
	
	private void selectMenu() {
		switch (selectedIndex) {
		case 0: // play
			stage.removeListener(consoleListener);
			console.clearScreen();
			overlay.addAction(Actions.sequence(
				ActionEnterText.get("graphix init", console, 1.3f),
				Actions.visible(true),
				Actions.alpha(1f, 1f),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						startGame();
					}
				})
			));
			break;
			
		case 1: // about
			console.clearScreen();
			stage.removeListener(consoleListener);
			console.post("VRAM hacker by Andrey Ponyavin");
			console.post("  for 29th Ludum Dare");
			console.post("");
			console.postMultiline("There are many surfaces in our world. One is graphical surface. Most of you had seen some graphical artifacts and some of you even had fun exploring video memory. Why not to use this information to steal some private information?");
			console.post("");
			console.post("Special thanks to:");
			console.post("   - Omsk IT community for hosting this event");
			console.post("   - BadLogic and contributors for awesome libGDX");
			console.post("");
			console.post("ESC to go back...");
			
			backListener = new InputListener(){
				@Override
				public boolean keyDown(InputEvent event, int keycode) {
					switch (keycode) {
					case Keys.ESCAPE:
						stage.removeListener(backListener);
						reset(true);
						return true;
					
					}
					
					return false;
				}
			};
			stage.addListener(backListener);
			break;
			
		case 2: // exit
			stage.removeListener(consoleListener);
			overlay.addAction(Actions.sequence(
				Actions.visible(true),
				Actions.alpha(1f, 1f),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						System.exit(0);
					}
				})
			));
			break;
		}
	}
	
	private void startGame() {
		BeneathGame.startLevel(1);
	}
	
	private void updateMenu() {
		for (Label l : menu)
			l.setColor(Color.WHITE);
		menu.get(selectedIndex).setColor(Color.YELLOW);
	}
	
	@Override
	public void onHide() {
		super.onHide();
	}
}
