package com.fizzicsgames.beneath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.fizzicsgames.beneath.BeneathGame;
import com.fizzicsgames.beneath.Res;
import com.fizzicsgames.beneath.game.ActionEnterText;
import com.fizzicsgames.beneath.game.ActionPressEnter;
import com.fizzicsgames.beneath.game.Field;
import com.fizzicsgames.beneath.game.GameConsole;
import com.fizzicsgames.beneath.game.GameEditor;
import com.fizzicsgames.beneath.game.GameHelp;
import com.fizzicsgames.beneath.game.Missions;
import com.fizzicsgames.beneath.game.OneKeyListener;
import com.fizzicsgames.beneath.game.SoundSystem;

public class ScreenGame extends BgScreen {

	public static int mission;
	
	// Windows
	private GameConsole console;
	private GameEditor editor;
	
	private String currentMissionFileName = "";
	private Field field;
	private Image overlay;
	private OneKeyListener listenerEscape;
	
	public ScreenGame() {
		super();
	}
	
	@Override
	public void onShow() {
		super.onShow();
		
		reset();
	}
	
	private void mission(final int m) {
		final float speed = 1f;
		
		stage.addAction(Actions.sequence(
			Actions.delay(1.0f * speed),
			Actions.run(new Runnable() {
				@Override
				public void run() {
					console.post("*****************************");
					console.post("* WELCOME TO GRAPHIX HACKER *");
					console.post("*(with no graphix ui though)*");
					console.post("");
					console.post("Please enter command: ");
					console.post("");
				}
			}),
			Actions.delay(0.5f * speed),
			ActionEnterText.get("graphix mission", console, 1.5f * speed),
			Actions.run(new Runnable() {
				@Override
				public void run() {
					console.setCommand("");
					console.post("> graphix mission");
				}
			}),
			Actions.delay(0.5f * speed),
			Actions.run(new Runnable() {
				@Override
				public void run() {
					console.post("");
					Array<Label> lines = console.postMultiline(Missions.brief[m-1]);
					for (Label l : lines)
						l.setColor(Color.YELLOW);
					console.post("");
					console.post("ENTER to continue...");
				}
			}),
			new ActionPressEnter(),
			Actions.run(new Runnable() {
				@Override
				public void run() {
					console.clearScreen();
				}
			}),
			ActionEnterText.get("graphix steal", console, 1.5f * speed),
			Actions.run(new Runnable() {
				@Override
				public void run() {
					console.setCommand("");
					console.post("> graphix steal");
				}
			}),
			Actions.delay(0.5f * speed),
			Actions.run(new Runnable() {
				@Override
				public void run() {
					console.post("");
					console.post("Stealing data, please wait...");
				}
			}),
			Actions.delay(2.0f * speed),
			Actions.run(new Runnable() {
				@Override
				public void run() {
					// Opening editor here
					createEditor(m);
				}
			})
		));
	}
	
	private void createField(final int mission) {
		currentMissionFileName = "mission" + mission + ".tmx";
		Res.assets.load(currentMissionFileName, TiledMap.class);
		Res.assets.finishLoading();
		TiledMap map = Res.assets.get(currentMissionFileName, TiledMap.class);
		
		field = new Field(stage, map);
		editor.addActor(field);
		
		// Add button listeners
		editor.getButtonAnswer().addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				String text = editor.getFieldAnswer().getText();
				if (text.length() > 0)
					hackSequence(mission, text);
			}
		});
		
		// Add exit listener
		stage.addListener(listenerEscape = new OneKeyListener(Keys.ESCAPE){
			@Override
			public void fire() {
				editor.setTouchable(Touchable.disabled);
				editor.removeListener(listenerEscape);
				ScreenGame.this.exit();
			}
		});
	}
	
	private void exit() {
		console.show();
		overlay.toFront();
		overlay.addAction(Actions.sequence(
			Actions.delay(1f),
			ActionEnterText.get("graphics exit", console, 1.5f),
			Actions.delay(0.2f),
			Actions.run(new Runnable() {
				@Override
				public void run() {
					console.post("> graphics exit");
					console.setCommand("");
				}
			}),
			Actions.visible(true),
			Actions.alpha(1f, 1f),
			Actions.run(new Runnable() {
				@Override
				public void run() {
					dispose();
					BeneathGame.setScreen(ScreenMainMenu.class);
				}
			})
		));
	}
	
	private void hackSequence(final int mission, final String text) {
		editor.setTouchable(Touchable.disabled);
		console.clearScreen();
		console.show();
		
		final boolean correct = text.equals(Missions.answer[mission-1]);
		final String enterText = "graphix hack " + text;
		console.addAction(Actions.sequence(
		    Actions.delay(0.5f),
			ActionEnterText.get(enterText, console, 2.0f),
			Actions.run(new Runnable() {
				@Override
				public void run() {
					console.setCommand("");
					console.post("> " + enterText);
				}
			}),
			Actions.delay(1.0f),
			Actions.run(new Runnable() {
				private OneKeyListener enterListener;

				@Override
				public void run() {
					if (correct) {
						// Success
						SoundSystem.playSuccess();
						console.post("");
						console.post("Success!", Color.GREEN);
						console.post("");
						Array<Label> lines = console.postMultiline(Missions.aftertext[mission-1]);
						for (Label l : lines)
							l.setColor(Color.YELLOW);
						console.post("");
						console.post("Press ENTER to continue");
						
						enterListener = new OneKeyListener(Keys.ENTER){
							public void fire() {
								stage.removeListener(enterListener);
								win();
							}
						};
						stage.addListener(enterListener);
					} else {
						// Try again
						SoundSystem.playFailure();
						console.post("");
						console.post("ERROR, wrong passphrase", Color.RED);
						console.addAction(Actions.sequence(
							Actions.delay(1.0f),
							Actions.run(new Runnable() {
								@Override
								public void run() {
									// Restore editor
									console.hide();
									editor.setTouchable(Touchable.enabled);
								}
							})
						));
					} 
				} // runnable end
			})
		));
	}
	
	private void win() {
		editor.setTouchable(Touchable.disabled);
		overlay.toFront();
		overlay.addAction(Actions.sequence(
			Actions.visible(true),
			Actions.alpha(1f, 1f),
			Actions.run(new Runnable() {
				@Override
				public void run() {
					dispose();
					BeneathGame.startLevel(mission+1);
				}
			})
		));

	}
	
	public void dispose() {
		if (field != null) {
			field.dispose();
			field = null;
		}
	}
	
	private void reset() {
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
		// TODO day texts
		
		// Day
		Group labelDayGroup = new Group();
		labelDayGroup.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f + 150f);
		labelDayGroup.setScale(1f);
		labelDayGroup.setColor(1f, 1f, 1f, 0f);
		Label labelDay = new Label("Day " + mission, Res.skin);
		labelDay.setPosition(-labelDay.getWidth() / 2f, -labelDay.getHeight() / 2f);
		labelDayGroup.addActor(labelDay);
		stage.addActor(labelDayGroup);
		labelDayGroup.addAction(Actions.sequence(
			Actions.alpha(1f, 0.5f),
			Actions.delay(2.3f),
			Actions.alpha(0f, 0.5f),
			Actions.removeActor()
		));
		
		// Title
		Group labelTitleGroup = new Group();
		labelTitleGroup.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f - 50f);
		labelTitleGroup.setScale(1.5f);
		labelTitleGroup.setColor(1f, 1f, 1f, 0f);
		Label labelTitle = new Label(Missions.dayNames[mission-1], Res.skin);
		labelTitle.setPosition(-labelTitle.getWidth() / 2f, -labelTitle.getHeight() / 2f);
		labelTitleGroup.addActor(labelTitle);
		stage.addActor(labelTitleGroup);
		labelTitleGroup.addAction(Actions.sequence(
			Actions.delay(0.3f),
			Actions.alpha(1f, 0.5f),
			Actions.delay(2f),
			Actions.alpha(0f, 0.5f),
			Actions.removeActor()
		));
		
		overlay.addAction(Actions.sequence(
			Actions.delay(3.2f),
			Actions.alpha(0f, 1f),
			Actions.visible(false),
			Actions.run(new Runnable() {
				
				@Override
				public void run() {
					mission(mission);
				}
			})
		));
	}
	
	@Override
	public void onHide() {
		super.onHide();
	}

	private void createEditor(final int m) {
		editor = new GameEditor(m);
		editor.startTransition();
		stage.addActor(editor);
		console.toFront();
		console.hide();
		editor.setCenter();
		createField(m);
		
		// Show help here
		if (mission == 1) {
			editor.setTouchable(Touchable.disabled);
			GameHelp help = new GameHelp();
			stage.addActor(help);
			help.setCenter();
			help.startTransition();
			help.getButton().addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					editor.setTouchable(Touchable.enabled);
					
					((GameHelp)event.getListenerActor().getParent()).startTransitionOut();
				}
			});
		}
	}
	

}
