package com.fizzicsgames.beneath;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		
		//CompileResources.main(args);
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Game";
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		
		
		
		if (height >= 720) {
			cfg.width = 1280;
			cfg.height = 720;
		} else {
			cfg.width = 1024;
			cfg.height = 640;
		}
		
		cfg.useGL30 = false;
		//cfg.samples = 2;
		
		
		
		new LwjglApplication(new BeneathGame(), cfg);
	}
}
