package com.lappard.android.graphic;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

public class AssetManager {

    private static AssetManager instance;

    public static final String TEXTURE_BLOCK = "textures/blockTile.png";
    public static final String TEXTURE_FLOOR = "textures/ground.png";
    public static final String TEXTURE_CAT = "textures/cat.png";
    public static final String TEXTURE_FIREWALL = "textures/firewall.png";
    public static final String TEXTURE_BACKGROUND = "textures/background.jpg";

    public static final String FONT_OPENSANS_24 = "fonts/opensans.ttf";

    private HashMap<String, Texture> textures;
    private HashMap<String, BitmapFont> fonts;

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
        textures.put(TEXTURE_FIREWALL, new Texture(TEXTURE_FIREWALL));
        textures.put(TEXTURE_BACKGROUND, new Texture(TEXTURE_BACKGROUND));



        fonts = new HashMap<>();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_OPENSANS_24));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(24 * Gdx.graphics.getDensity());
        BitmapFont font = generator.generateFont(parameter);

        fonts.put(FONT_OPENSANS_24, font);

    }

    public Texture getTexture(String name) {
        return textures.get(name);
    }
    public BitmapFont getFont(String name){
        return fonts.get(name);
    }
}
