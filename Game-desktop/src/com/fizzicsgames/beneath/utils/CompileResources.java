package com.fizzicsgames.beneath.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapHelper;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.tiledmappacker.TiledMapPacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.fizzicsgames.beneath.Res.AtlasID;


public class CompileResources {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		for(AtlasID atlas : AtlasID.values()) {
			String atlasFolder = "../Game/Atlases/" + atlas.getFolderName() +"/";
			String outputFolder = "../Game/CompiledData/gfx/";
			
			File outputFolderFile = new File(outputFolder);
			outputFolderFile.mkdirs();
			
			// Delete pack file if it exists 
			File packFile = new File(outputFolder, atlas.getFileName());
			if (packFile.exists())
				packFile.delete();
			
			TexturePacker.process(atlasFolder, outputFolder, atlas.getFileName());
		}
		
		// Copy compiled data to everywhere
		File dataLocation = new File("../Game/Data/");
		File sourceLocation = new File("../Game/CompiledData/");
		try {
			// Data
			copyDirectory(dataLocation, sourceLocation);
			
			// Desktop
			copyDirectory(sourceLocation, new File("./"));
			// Android
			copyDirectory(sourceLocation, new File("../Game-android/assets/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Done!");
	}

	private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            } 
        } finally {
            is.close();
            os.close();
        }
    }
	
	public static void copyDirectory(File sourceLocation, File targetLocation)
			throws IOException {
		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();

			for (int i = 0; i < children.length; i++) {

				copyDirectory(new File(sourceLocation, children[i]), new File(
						targetLocation, children[i]));
			}
		} else {

			// Mac's shit
			if (sourceLocation.getName().equals(".DS_Store")) {
				sourceLocation.delete();
				return;
			}
			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.flush();
			out.close();
		}
	}
}
