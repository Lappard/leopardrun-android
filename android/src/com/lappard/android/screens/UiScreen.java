package com.lappard.android.screens;

import android.content.Context;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lappard.android.LeopardRun;
import com.lappard.android.R;
import com.lappard.android.graphic.AssetManager;
import com.lappard.android.logic.ScoreManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


public class UiScreen implements IScreen {
    protected SpriteBatch batch;
    protected Stage stage;
    private BitmapFont font;
    private Context _context;
    private Label _scoreLabel;

    public UiScreen() {
        batch = new SpriteBatch();
        _context = _context = ((LeopardRun) Gdx.app.getApplicationListener()).getContext();
    }

    @Override
    public void show() {


        font = AssetManager.getInstance().getFont(AssetManager.FONT_OPENSANS_24);
        stage = new Stage(new ExtendViewport(1280,720));

        // create table
        Table header = new Table();
        header.setFillParent(true);

        // text content
        Label.LabelStyle scoreStyle = new Label.LabelStyle();
        scoreStyle.font = font;
        scoreStyle.fontColor = Color.WHITE;
        _scoreLabel = new Label(_context.getString(R.string.score_pre_text),scoreStyle);
        header.left().bottom();
        header.add(_scoreLabel).left();

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
        _scoreLabel.setText(_context.getString(R.string.score_pre_text) + " " + (int) ScoreManager.getInstance().getScore());
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
