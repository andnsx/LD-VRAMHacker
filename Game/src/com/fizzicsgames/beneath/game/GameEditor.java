package com.fizzicsgames.beneath.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.fizzicsgames.beneath.Res;

public class GameEditor extends TableWindow {

	public final static float BEGIN_X = 5f + 4f, BEGIN_Y = 195f + 4f;
	
	public final static int MAX_WIDTH = 30, MAX_HEIGHT = 13;
	private Table gridBack;
	private Image grid;
	
	private int mission;

	private TextField fieldAnswer;

	private TextButton buttonAnswer;
	
	public GameEditor(int m) {
		super("Graphix Hacker");
		mission = m;
		setSize(980f, 700f);
		

		
		// Grid back
		gridBack = new Table(Res.skin);
		gridBack.setBackground("npInnerWindow");
		gridBack.setSize(MAX_WIDTH * 32f + 8f,MAX_HEIGHT * 32f + 8f);
		gridBack.setPosition(BEGIN_X - 4f, BEGIN_Y - 4f);
		addActor(gridBack);
		
		
		// Grid
		grid = new Image(Res.skin.getTiledDrawable("grid"));
		grid.setSize(MAX_WIDTH * 32f,MAX_HEIGHT * 32f);
		grid.setPosition(BEGIN_X, BEGIN_Y);
		addActor(grid);
		
		// Editor grid
		
		// Labels
		Label l = new Label("Fetched VRAM:", Res.skin);
		l.setPosition(5f, BEGIN_Y + grid.getHeight() + 5f);
		addActor(l);
		
		// Task
		l = new Label("Task:", Res.skin);
		l.setPosition(5f, BEGIN_Y - 45f);
		addActor(l);
		
		Table taskBack = new Table(Res.skin);
		taskBack.setBackground("npInnerWindow");
		taskBack.setSize(gridBack.getWidth()  * 0.66f, 150f);
		taskBack.setPosition(5f, 5f);
		addActor(taskBack);
		
		
		// Text
		l = new Label(Missions.brief[m-1], Res.skin.get("console", LabelStyle.class));
		l.setWrap(true);
		l.setWidth(taskBack.getWidth() - 10f);
		l.setPosition(10f, taskBack.getY() + taskBack.getHeight() - l.getPrefHeight() / 2f - 15f);
		addActor(l);
		
		// Enter field
		l = new Label("Try to hack:", Res.skin);
		l.setPosition(taskBack.getX() + taskBack.getWidth() + 5f, BEGIN_Y - 45f);
		addActor(l);
		
		Table enterBack = new Table(Res.skin);
		enterBack.setBackground("npInnerWindow");
		enterBack.setSize(325f, 150f);
		enterBack.setPosition(taskBack.getX() + taskBack.getWidth() + 5f, 5f);
		addActor(enterBack);
		
		fieldAnswer = new TextField("", Res.skin);
		fieldAnswer.setWidth(315f);
		fieldAnswer.setPosition(5f, 90f);
		fieldAnswer.setMaxLength(20);
		enterBack.addActor(fieldAnswer);
		
		buttonAnswer = new TextButton("Hack", Res.skin);
		buttonAnswer.setWidth(315f);
		buttonAnswer.setPosition(5f, 5f);
		enterBack.addActor(buttonAnswer);
	}

	public TextButton getButtonAnswer() {
		return buttonAnswer;
	}
	
	public TextField getFieldAnswer() {
		return fieldAnswer;
	}

}
