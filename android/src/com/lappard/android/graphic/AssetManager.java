package com.lappard.android.graphic;


import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class AssetManager {

    private static AssetManager instance;
    public static final String TEXTURE_BLOCK = "blockTile.png";
    public static final String TEXTURE_FLOOR = "ground.png";
    public static final String TEXTURE_CAT = "cat.png";

    private HashMap<String, Texture> textures;

    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    private AssetManager() {
        textures = new HashMap<>();
        textures.put(TEXTURE_BLOCK, new Texture(TEXTURE_BLOCK));
        textures.put(TEXTURE_FLOOR, new Texture(TEXTURE_FLOOR));
        textures.put(TEXTURE_CAT, new Texture(TEXTURE_CAT));
    }

    public Texture getTexture(String name) {
        return textures.get(name);
    }
}
