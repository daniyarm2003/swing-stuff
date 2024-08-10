package com.lildan42.swingstuff.pathfinding.simobjects;

import com.lildan42.swingstuff.pathfinding.collision.Collider2D;
import com.lildan42.swingstuff.pathfinding.graphics.Renderable;
import com.lildan42.swingstuff.pathfinding.scenes.SimulationScene;
import com.lildan42.swingstuff.pathfinding.simobjects.utils.MovementProperties;
import com.lildan42.swingstuff.pathfinding.utils.PhysicsUtils;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public abstract class SimulationObject implements Renderable {
    protected final SimulationScene scene;

    private Vec2 pos = Vec2.ZERO;
    private Vec2 vel = Vec2.ZERO;
    private Vec2 acc = Vec2.ZERO;

    private double mass = 1.0;

    private boolean removed = false;
    private boolean tangible = false;

    public SimulationObject(SimulationScene scene) {
        this.scene = scene;
    }

    public Vec2 getPosition() {
        return this.pos;
    }

    public void setPosition(Vec2 pos) {
        this.pos = pos;
    }

    public SimulationScene getScene() {
        return this.scene;
    }

    public double getDistanceFromSqr(SimulationObject other) {
        return other.getPosition().sub(this.getPosition()).sqrMag();
    }

    public double getDistanceFrom(SimulationObject other) {
        return other.getPosition().sub(this.getPosition()).mag();
    }

    public double getMass() {
        return this.mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public Vec2 getVelocity() {
        return this.vel;
    }

    public void setVelocity(Vec2 vel) {
        this.vel = vel;
    }

    public Vec2 getAcceleration() {
        return this.acc;
    }

    public void setAcceleration(Vec2 acc) {
        this.acc = acc;
    }

    public void applyForce(Vec2 force) {
        this.setAcceleration(this.getAcceleration().add(force.scale(1.0 / this.getMass())));
    }

    public void applyGravity() {
        this.applyForce(Vec2.UNIT_Y.scale(this.getScene().getGravity() * this.getMass()));
    }

    public void jump(MovementProperties properties) {
        double jumpVelY = PhysicsUtils.getJumpVelocityYFromHeight(properties.getJumpHeight(), this.scene.getGravity());
        Vec2 vel = this.getVelocity();

        this.setVelocity(new Vec2(vel.getX(), jumpVelY));
    }

    public void applyPhysics(double dt) {
        this.setVelocity(this.getVelocity().add(this.getAcceleration().scale(dt)));
        this.setPosition(this.getPosition().add(this.getVelocity().scale(dt)));
        this.setAcceleration(Vec2.ZERO);
    }

    public boolean isRemoved() {
        return this.removed;
    }

    public void markRemoved() {
        this.removed = true;
    }

    public boolean isTangible() {
        return this.tangible;
    }

    public void setTangible(boolean tangible) {
        this.tangible = tangible;
    }

    protected abstract Collider2D getCollider();

    public Vec2 getMTV(Collider2D otherCollider) {
        if(otherCollider == null) {
            throw new IllegalArgumentException("collider cannot be null");
        }

        return this.getCollider() != null ? this.getCollider().getMTV(otherCollider) : null;
    }

    public boolean collides(Collider2D otherCollider) {
        if(otherCollider == null) {
            throw new IllegalArgumentException("collider cannot be null");
        }

        return this.getCollider() != null && this.getCollider().collides(otherCollider);
    }

    public Vec2 getMTV(SimulationObject other) {
        if(other == null) {
            throw new IllegalArgumentException("other simulation object cannot be null");
        }

        return other.getCollider() != null ? this.getMTV(other.getCollider()) : null;
    }

    public boolean collides(SimulationObject other) {
        if(other == null) {
            throw new IllegalArgumentException("other simulation object cannot be null");
        }

        return other.getCollider() != null && this.collides(other.getCollider());
    }

    public abstract void update(double dt);
}
