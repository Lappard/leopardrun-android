package com.lappard.android.graphic;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

public class AssetManager {

    private static AssetManager instance;

    public static final String TEXTURE_BLOCK = "textures/block.png";
    public static final String TEXTURE_FLOOR = "textures/ground.png";
    public static final String TEXTURE_CAT = "textures/cat.png";
    public static final String TEXTURE_GHOST = "textures/ghost.png";
    public static final String TEXTURE_FIREWALL = "textures/firewall.png";
    public static final String TEXTURE_BACKGROUND = "textures/background.jpg";
    public static final String TEXTURE_LOGO = "textures/logo.png";
    public static final String TEXTURE_BUTTON_BLUE = "textures/blue_button.png";
    public static final String TEXTURE_COIN = "textures/Coin.png";

    public static final String FONT_SHOJUMARU_24 = "fonts/shojumaru-regular.ttf";
    public static final String FONT_SHOJUMARU_14 = "fonts/shojumaru-regular.ttf_14";
    public static final String FONT_SHOJUMARU_12 = "fonts/shojumaru-regular.ttf_12";

    public static final String SOUND_DEATH = "sounds/dead.wav";
    public static final String SOUND_JUMP = "sounds/jump.wav";
    public static final String SOUND_THEME = "sounds/theme.wav";

    private HashMap<String, Texture> textures;
    private HashMap<String, BitmapFont> fonts;
    private HashMap<String, Sound> sounds;

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
        textures.put(TEXTURE_GHOST, new Texture(TEXTURE_GHOST));
        textures.put(TEXTURE_FIREWALL, new Texture(TEXTURE_FIREWALL));
        textures.put(TEXTURE_BACKGROUND, new Texture(TEXTURE_BACKGROUND));
        textures.put(TEXTURE_LOGO, new Texture(TEXTURE_LOGO));
        textures.put(TEXTURE_BUTTON_BLUE, new Texture(TEXTURE_BUTTON_BLUE));
        textures.put(TEXTURE_COIN, new Texture(TEXTURE_COIN));

        fonts = new HashMap<>();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_SHOJUMARU_24));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(24 * Gdx.graphics.getDensity());
        BitmapFont font = generator.generateFont(parameter);

        fonts.put(FONT_SHOJUMARU_24, font);

        parameter.size = (int)(14 * Gdx.graphics.getDensity());
        fonts.put(FONT_SHOJUMARU_14, generator.generateFont(parameter));

        parameter.size = (int)(12 * Gdx.graphics.getDensity());
        fonts.put(FONT_SHOJUMARU_12, generator.generateFont(parameter));

        sounds = new HashMap<>();
        sounds.put(SOUND_DEATH, Gdx.audio.newSound(Gdx.files.internal(SOUND_DEATH)));
        sounds.put(SOUND_JUMP, Gdx.audio.newSound(Gdx.files.internal(SOUND_JUMP)));
        sounds.put(SOUND_THEME, Gdx.audio.newSound(Gdx.files.internal(SOUND_THEME)));
    }

    public Texture getTexture(String name) {
        return textures.get(name);
    }
    public BitmapFont getFont(String name){
        return fonts.get(name);
    }
    public Sound getSound(String name) {
        return sounds.get(name);
    }
}
