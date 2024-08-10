package com.lildan42.swingstuff.pathfinding.simobjects.managers;

import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;

import java.util.ArrayList;
import java.util.List;

public class DefaultSimulationObjectManager implements SimulationObjectManager {

    private static final double NEARBY_OBJECT_MAX_DIST = 1000.0;

    private final List<SimulationObject> objects = new ArrayList<>();
    private final List<SimulationObject> removalQueue = new ArrayList<>();

    @Override
    public List<SimulationObject> getAllObjects() {
        return this.objects;
    }

    @Override
    public List<SimulationObject> getAllNearbyObjects(SimulationObject obj) {
        List<SimulationObject> nearbyObjects = new ArrayList<>();

        for(SimulationObject simObject : this.objects) {
            if(simObject != obj && obj.getDistanceFromSqr(simObject) <= NEARBY_OBJECT_MAX_DIST * NEARBY_OBJECT_MAX_DIST) {
                nearbyObjects.add(simObject);
            }
        }

        return nearbyObjects;
    }

    @Override
    public void addObject(SimulationObject obj) {
        this.objects.add(obj);
    }

    @Override
    public void queueObjectRemoval(SimulationObject obj) {
        if(!obj.isRemoved()) {
            this.removalQueue.add(obj);
            obj.markRemoved();
        }
    }

    @Override
    public void removeQueuedObjects() {
        this.objects.removeAll(this.removalQueue);
        this.removalQueue.clear();
    }
}
