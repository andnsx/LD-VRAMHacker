package com.fizzicsgames.beneath.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Field extends Group implements Disposable {

	private final static String TAG = "Field";
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;
	private DragListener dragListener;

	private TiledMapTileLayer tiles;

	private Image draggableTile = null;
	private TiledMapTile storedTile = null;
	private int initialX, initialY;

	public Field(Stage s, TiledMap map) {
		this.map = map;
		renderer = new OrthogonalTiledMapRenderer(map, s.getSpriteBatch());
		renderer.setView((OrthographicCamera) s.getCamera());
		setPosition(GameEditor.BEGIN_X, GameEditor.BEGIN_Y);
		setSize(GameEditor.MAX_WIDTH * 32f, GameEditor.MAX_HEIGHT * 32f);
		
		
		// Get tiled layer
		for (MapLayer layer : map.getLayers()) {
			if (layer.getName().equals("Tiles")) {
				tiles = (TiledMapTileLayer)layer;
				break;
			}
		}
		
		if (tiles == null) throw new GdxRuntimeException("Tiles is null");
		
		// Mouse controls
		addListener(dragListener = new DragListener(){
			
			
			@Override
			public void dragStart(InputEvent event, float x, float y,
					int pointer) {
				//y = getHeight() - y; // Invert
				
				// Get tile
				initialX = (int)(x / 32f);
				initialY = (int)(y / 32f);
				
				Cell cell = tiles.getCell(initialX, initialY);
				if (cell != null) {
					TiledMapTile tile = cell.getTile();
					if (tile != null) {
						storedTile = tile;
						cell.setTile(null);
						
						draggableTile = new Image(tile.getTextureRegion());
						draggableTile.setPosition(x - draggableTile.getWidth() / 2f, y - draggableTile.getHeight() / 2f);
						addActor(draggableTile);
					}
				}
			}
			
			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {

				if (draggableTile != null)
					draggableTile.setPosition(x - draggableTile.getWidth() / 2f, y - draggableTile.getHeight() / 2f);
			}
			
			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer) {
				if (storedTile == null) return;
				
				
				if (draggableTile != null) {
					removeActor(draggableTile);
					draggableTile = null;
				}
				
				int cellX = (int)(x / 32f);
				int cellY = (int)(y / 32f);
				
				// Check if OK
				boolean suitable = cellX >= 0 && cellX <= tiles.getWidth() &&
						cellY >= 0 && cellY <= tiles.getHeight();
						
				Cell cell = null;
				if (suitable) {
					cell = tiles.getCell(cellX, cellY);
					
					if (cell == null) {
						cell = new Cell();
					} else {
						if (cell.getTile() != null)
							suitable = false; // can't just waste tile
					}
				}
				
				if (suitable) {
					cell.setTile(storedTile);
					tiles.setCell(cellX, cellY, cell);
				} else { // return to initial position
					cell = tiles.getCell(initialX, initialY);
					cell.setTile(storedTile);
				}
				
				storedTile = null;
			}
		});
		dragListener.setTapSquareSize(2f);
	}	@Override
	public Actor hit(float x, float y, boolean touchable) {
		Actor ret = super.hit(x, y, touchable);
		return ret;
	}

	@Override
	protected void drawChildren(Batch batch, float parentAlpha) {
		for (MapLayer layer : map.getLayers()) {
			if (layer.isVisible()) {
				if (layer instanceof TiledMapTileLayer) {
					renderer.renderTileLayer((TiledMapTileLayer) layer);
				} else {
					for (MapObject object : layer.getObjects()) {
						renderer.renderObject(object);
					}
				}
			}
		}

		super.drawChildren(batch, parentAlpha);
	}
	
	@Override
	public void dispose() {
		if (map != null) {
			map.dispose();
			map = null;
		}
	}
}
