package com.lappard.android.util;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.lappard.android.actors.PhysicsActor;

public class ContactHandler implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        PhysicsActor actorA = (PhysicsActor)contact.getFixtureA().getUserData();
        PhysicsActor actorB = (PhysicsActor)contact.getFixtureB().getUserData();
        actorA.onContact(actorB);
        actorB.onContact(actorA);
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
