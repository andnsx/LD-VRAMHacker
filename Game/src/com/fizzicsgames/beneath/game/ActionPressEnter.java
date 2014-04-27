package com.fizzicsgames.beneath.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Action;

public class ActionPressEnter extends Action {

	@Override
	public boolean act(float delta) {
		if (Gdx.input.isKeyPressed(Keys.ENTER))
			return true;
		
		return false;
	}

}
