package com.lappard.android.logic;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.lappard.android.entity.Floor;
import com.lappard.android.entity.Player;

/**
 * Created by Jonas on 16.05.2015.
 */
public class ContactSolver implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Object o1 = contact.getFixtureA().getBody().getUserData();
        Object o2 = contact.getFixtureB().getBody().getUserData();

        Log.d("GameLogic", "Begin contact: "+o1.getClass().getSimpleName()+", "+o2.getClass().getSimpleName());

        if(o1 instanceof Player && o2 instanceof Floor)
            playerWithFloor((Player)o1, (Floor)o2, true);

        if(o2 instanceof Player && o1 instanceof Floor)
            playerWithFloor((Player)o2, (Floor)o1, true);

    }

    @Override
    public void endContact(Contact contact) {

        Object o1 = contact.getFixtureA().getBody().getUserData();
        Object o2 = contact.getFixtureB().getBody().getUserData();

        Log.d("GameLogic", "End contact: "+o1.getClass().getSimpleName()+", "+o2.getClass().getSimpleName());

        if(o1 instanceof Player && o2 instanceof Floor)
            playerWithFloor((Player)o1, (Floor)o2, false);

        if(o2 instanceof Player && o1 instanceof Floor)
            playerWithFloor((Player)o2, (Floor)o1, false);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


    private void playerWithFloor(Player p, Floor f, boolean isContact){
        if(isContact){
            p.setOnGround(true);
        }else{
            p.setOnGround(false);
        }
    }
}
