package com.fizzicsgames.beneath.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class OneKeyListener extends InputListener {

	private int key;
	
	public OneKeyListener(int key) {
		this.key = key;
	}
	
	@Override
	public boolean keyDown(InputEvent event, int keycode) {
		if (keycode == key) {
			fire();
			return true;
		}
		
		return false;
	}
	
	public void fire() {
	}
}
