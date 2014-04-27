package com.fizzicsgames.beneath.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.fizzicsgames.beneath.Res;

public class GameConsole extends TableWindow {

	private static final int WIDTH = 55;

	private Table consoleZone;
	
	private Array<Label> labels = new Array<Label>();
	private Label commandLabel;
	
	public GameConsole() {
		super("Best hacker console");
		setSize(800f, 500f);
		
		consoleZone = new Table(Res.skin);
		consoleZone.setBackground("npConsole");
		consoleZone.setSize(790f, 450f);
		addActor(consoleZone);
		consoleZone.setPosition(5f, 5f);
		
		commandLabel = new Label(">", Res.skin, "console");
		consoleZone.addActor(commandLabel);
		labels.add(commandLabel);
	}
	
	public void hide() {
		setOrigin(-getX(), -getY());
		addAction(Actions.sequence(
			Actions.parallel(
				Actions.scaleTo(0f, 0f, 0.75f),
				Actions.alpha(0f, 0.75f)
			),
			Actions.visible(false)
		));
	}
	
	public void show() {
		setOrigin(-getX(), -getY());
		addAction(Actions.sequence(
				Actions.visible(true),
				Actions.parallel(
					Actions.scaleTo(1f, 1f, 0.75f),
					Actions.alpha(1f, 0.75f)
				)
			));
	}
	
	public Label post(CharSequence text, Color c) {
		Label ret = addLine(text, c);
		alignLines();
		return ret;
	}
	
	public Label post(CharSequence text) {
		return post(text, Color.WHITE);
	}
	
	public void clearScreen() {
		for (int i = 1; i < labels.size; ++i) {
			Label l = labels.removeIndex(i);
			consoleZone.removeActor(l);
			--i;
		}
	}
	
	public Array<Label> postMultiline(CharSequence text) {
		Array<Label> ret = new Array<Label>();
		int lines = (int) (text.length() / WIDTH + ((text.length() % WIDTH) > 0 ? 1 : 0));
		for (int i = 0; i < lines; ++i) {
			int finish = (i+1) * WIDTH;
			if (finish > text.length())
				finish = text.length();
			ret.add(addLine(text.subSequence(i * WIDTH, finish), Color.WHITE));
		}
		
		alignLines();
		
		return ret;
	}
	

	private Label addLine(CharSequence text, Color c) {
		Label newLabel = new Label(text, Res.skin, "console");
		newLabel.setColor(c);
		labels.insert(1, newLabel);
		consoleZone.addActor(newLabel);
		return newLabel;
	}

	private void alignLines() {
		for (int i = 0; i < labels.size; ++i) {
			Label l = labels.get(i);
			l.setPosition(3f, i * 22f);
			
			if (i > 20) {
				consoleZone.removeActor(l);
				labels.removeIndex(i);
				--i;
			}
		}
	}
	

	public void setCommand(CharSequence text) {
		commandLabel.setText("> " + text);
	}
}
