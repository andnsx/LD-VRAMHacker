package com.fizzicsgames.beneath.game;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.fizzicsgames.beneath.Res;

public class GameHelp extends TableWindow {

	
	private TextButton buttonOK;

	public GameHelp() {
		super("How to play?");
		
		setSize(800f, 430f);
		
		// Add content
		Label l = new Label("Welcome to advanced VRAM browser. You can move pieces of images as you like. You need to get the passphrase using this information. Some additional one or Googling can help too! You can try as much as you want to but be aware, because passphases are case-sensetive.",
			Res.skin);
		l.setWrap(true);
		l.setWidth(getWidth() - 20f);
		l.setPosition(10f, getHeight() - l.getPrefHeight() / 2f - 60f);
		addActor(l);
		
		// Button
		buttonOK = new TextButton("Got it!", Res.skin);
		buttonOK.setPosition(getWidth() / 2f - buttonOK.getWidth() / 2f, 5f);
		addActor(buttonOK);
	}

	public TextButton getButton() {
		return buttonOK;
	}
}
