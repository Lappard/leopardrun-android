package com.lappard.android.screens;

import android.content.Context;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
import com.lappard.android.screens.events.ScreenActivateEvent;
import com.lappard.android.screens.events.ScreenCreationEvent;
import com.lappard.android.screens.events.ScreenRemoveEvent;
import com.lappard.android.util.Event;

import java.lang.reflect.InvocationTargetException;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


public abstract class MenuScreen implements IScreen {
    protected Stage stage;
    protected Image background;
    protected BitmapFont font;
    protected Context context;
    protected Table layout;

    //for listeners, where "this" is another scope
    protected final MenuScreen instance;

    public MenuScreen(String backgroundTextureName) {
        instance = this;
        context = ((LeopardRun) Gdx.app.getApplicationListener()).getContext();

        stage = new Stage(new ExtendViewport(1280,720));
        stage.setDebugAll(LeopardRun.DEBUG_MODE);


        font = AssetManager.getInstance().getFont(AssetManager.FONT_OPENSANS_24);

        background = new Image(new Sprite(AssetManager.getInstance().getTexture(backgroundTextureName)));
        background.setFillParent(true);
        stage.addActor(background);

        layout = new Table();
        layout.setFillParent(true);
        layout.center();
        stage.addActor(layout);
    }

    protected Button createImageButton(float width, float height, String textureName, String label, final Action touchAction){
        ImageTextButton.ImageTextButtonStyle iBStyle = new ImageTextButton.ImageTextButtonStyle();
        iBStyle.font = font;
        iBStyle.fontColor = Color.BLACK;
        iBStyle.up = new SpriteDrawable(new Sprite(AssetManager.getInstance().getTexture(textureName)));

        ImageTextButton button = new ImageTextButton(label,iBStyle);
        button.getLabel().setAlignment(Align.center);
        button.getLabelCell().width(width).height(height);

        if(touchAction != null){
            button.addListener(new ClickListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    stage.addAction(touchAction);
                    return true;
                }
            });
        }


        return button;
    }

    protected Button createImageButton(float width, float height, String textureName, int stringRessource, Action touchAction){
        return createImageButton(width, height, textureName, context.getString(stringRessource), touchAction);
    }

    protected Label createLabel(String text){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.BLACK;
        return new Label(text ,style);
    }

    protected Label createLabel(int stringRessource){
        return createLabel(context.getString(stringRessource));
    }


    protected Action createScreenTransition(final ScreenCreator creator){

        return sequence(run(new Runnable() {
            @Override
            public void run() {

                IScreen destinationScreen = creator.createScreen();
                Event.getBus().post(new ScreenCreationEvent(destinationScreen));

            }
        }), moveTo(0, -stage.getHeight(), 1f), run(new Runnable() {

            @Override
            public void run() {
                Event.getBus().post(new ScreenRemoveEvent(instance));
            }
        }));
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        // coming in from top animation
        stage.addAction(sequence(moveTo(0, stage.getWidth()), moveTo(0, 0, 1f)));

    }



    @Override
    public void render(float delta) {

        stage.act(delta);
        stage.draw();

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
