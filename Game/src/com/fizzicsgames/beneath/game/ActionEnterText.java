package com.fizzicsgames.beneath.game;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class ActionEnterText extends TemporalAction  {

	private CharSequence text;
	private GameConsole console;
	private int previous;
	
	public ActionEnterText(CharSequence text, GameConsole c) {
		this.text = text;
		this.console = c;
		previous = 0;
	}
	
	@Override
	protected void update(float percent) {
		int current = (int)(percent * text.length());
		if (current > previous) {
			previous = current;
			SoundSystem.playKey();
		}
		console.setCommand(text.subSequence(0, current));
	}


	public static ActionEnterText get(CharSequence text, GameConsole c, float duration) {
		ActionEnterText ret = new ActionEnterText(text, c);
		ret.setDuration(duration);
		
		return ret;
	}
}
