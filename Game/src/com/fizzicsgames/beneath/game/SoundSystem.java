package com.fizzicsgames.beneath.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

public class SoundSystem {
	
	public static boolean soundOn = true;

	private static Array<Sound> keys;
	private static Sound failure, success;
	
	public static void init() {
		keys = new Array<Sound>();
		for (int i = 0; i < 5; ++i)
			keys.add(Gdx.audio.newSound(Gdx.files.internal("soundKey" + i + ".wav")));
		
		failure = Gdx.audio.newSound(Gdx.files.internal("soundFailure.wav"));
		success = Gdx.audio.newSound(Gdx.files.internal("soundSuccess.wav"));
	}
	
	private static void playSound(Sound s) {
		if (!soundOn) return;
		
		s.play();
	}
	
	public static void playFailure() {
		playSound(failure);
	}
	
	public static void playSuccess() {
		playSound(success);
	}
	
	public static void playKey() {
		playSound(keys.random());
	}
}
