package com.fizzicsgames.beneath.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.fizzicsgames.beneath.Res;

public class TableWindow extends Group {

	private Table table;
	
	public TableWindow(String head) {
		super();
		table = new Table(Res.skin);
		addActor(table);
		table.setFillParent(true);
		table.setBackground(Res.skin.getDrawable("npWindow"));
		table.left().top();
		
		table.add(head, "windowHeader").padTop(-50f).row();
		
	}
	
	public void setCenter() {
		setPosition(getStage().getWidth() / 2f - getWidth() / 2f, getStage().getHeight() / 2f - getHeight() / 2f);
	}
	
	public void startTransition() {
		setVisible(true);
		setScale(0f);
		setColor(1f, 1f, 1f, 0f);
		setOrigin(getWidth() / 2f, getHeight() / 2f);
		addAction(Actions.parallel(
			Actions.scaleTo(1f, 1f, 0.75f),
			Actions.alpha(1f, 0.75f)
		));
	}
	
	public void startTransitionOut() {
		addAction(Actions.sequence(Actions.parallel(
				Actions.scaleTo(0f, 0f, 0.75f),
				Actions.alpha(0f, 0.75f)),
				Actions.visible(false)
		));
	}
}
