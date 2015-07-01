package com.lappard.android.screens;

import android.content.Context;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lappard.android.LeopardRun;
import com.lappard.android.R;
import com.lappard.android.graphic.AssetManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


public class MenuScreen implements IScreen {
    protected SpriteBatch batch;
    protected Stage stage;
    private Sprite background;
    private BitmapFont _font;
    private Context _context;


    public MenuScreen(Context context) {
        batch = new SpriteBatch();
        _context = context;
    }

    @Override
    public void show() {

        /**
         * create _font from testfont.ttf
         */
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/testfont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(24 * Gdx.graphics.getDensity());
        _font = generator.generateFont(parameter);



        stage = new Stage(new ExtendViewport(1280,720));
        Gdx.input.setInputProcessor(stage);


        ImageTextButton.ImageTextButtonStyle iBStyle = new ImageTextButton.ImageTextButtonStyle();
        iBStyle.font = _font;
        iBStyle.fontColor = Color.BLACK;

        iBStyle.up = new SpriteDrawable(new Sprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_BLOCK)));
//        iBStyle.down = new SpriteDrawable(new Sprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_BLOCK)));
        ImageTextButton singlePlayerButton = new ImageTextButton(_context.getString(R.string.single_player_button),iBStyle);
        singlePlayerButton.getLabel().setAlignment(Align.center);
        singlePlayerButton.getLabelCell().width(400).height(200);

        ImageTextButton multiPlayerButton = new ImageTextButton(_context.getString(R.string.multi_player_button),iBStyle);
        multiPlayerButton.getLabel().setAlignment(Align.center);
        multiPlayerButton.getLabelCell().width(400).height(200);

        singlePlayerButton.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //move out this stage
                final GameScreen gs = new GameScreen();
                stage.addAction(sequence(run(new Runnable() {

                    @Override
                    public void run() {
                        ((LeopardRun) Gdx.app.getApplicationListener()).addScreen(gs);
                    }
                }), moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

                    @Override
                    public void run() {
                        gs.setActive();
                    }
                })));
                return true;
            }
        });

        //bg
        background = new Sprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_BACKGROUND));
        Image bgactor = new Image(background);
        bgactor.setFillParent(true);
        stage.addActor(bgactor);


        // create table
        Table header = new Table();
        header.setFillParent(true);

        // text content
        Label.LabelStyle headerStyle = new Label.LabelStyle();
        headerStyle.font = _font;
        headerStyle.fontColor = Color.BLACK;
        Label headerText = new Label(_context.getString(R.string.menu_heading),headerStyle);
        header.center();

        header.add(headerText).center();
        header.row();
        header.add(singlePlayerButton);
        header.row().padTop(20);
        header.add(multiPlayerButton);

        /**
         * add table to the stage
         */
        stage.addActor(header);

        /**
         * draw table outlines
         */
        stage.setDebugAll(true);



        // coming in from top animation
//        stage.addAction(sequence(moveTo(0, stage.getWidth()), moveTo(0, 0, 1f)));

    }



    @Override
    public void render(float delta) {

        batch.begin();
        stage.act(delta);
        stage.draw();
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void setActive() {

    }
}
