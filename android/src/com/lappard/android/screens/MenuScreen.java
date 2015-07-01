package com.lappard.android.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lappard.android.LeopardRun;
import com.lappard.android.graphic.AssetManager;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


public class MenuScreen implements IScreen {
    protected SpriteBatch batch;
    protected Stage stage;
    private Sprite background;
    private BitmapFont font;



    public MenuScreen() {
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

        /**
         * create font from testfont.ttf
         */
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/testfont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(24 * Gdx.graphics.getDensity());
        font = generator.generateFont(parameter);

        /**
         * dont use the games viewport here --> PIXEL_PER_METER <--
         */
//        stage = new Stage(new ExtendViewport(1280f / PIXEL_PER_METER, 720f / PIXEL_PER_METER));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        stage.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //move out this stage
                final GameScreen gs = new GameScreen();
                stage.addAction(sequence(run(new Runnable() {

                    @Override
                    public void run() {
                        ((LeopardRun) Gdx.app.getApplicationListener()).addScreen(gs);
                    }
                }),moveTo(0, -stage.getHeight(), .5f),run(new Runnable() {

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
        this.background.setSize(1024, 768);
        Image bgactor = new Image(background);
        bgactor.setFillParent(true);
        stage.addActor(bgactor);


        // create table
        Table header = new Table();
        header.setFillParent(true);

        // text content
        Label.LabelStyle headerStyle = new Label.LabelStyle();
        headerStyle.font = font;
        headerStyle.fontColor = Color.BLACK;
        Label headerText = new Label("tab to play",headerStyle);

        headerText.setWidth(10);
        headerText.setHeight(10);
        headerText.setScale(0.3f,0.3f);
        headerText.pack();
        header.center();

        header.add(headerText).center();
        header.row();

        /**
         * add table to the stage
         */
        stage.addActor(header);

        /**
         * draw table outlines
         */
//        stage.setDebugAll(true);



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
