package com.lildan42.swingstuff.pathfinding.simobjects.managers;

import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;

import java.util.List;

public interface SimulationObjectManager {
    Iterable<SimulationObject> getAllObjects();
    List<SimulationObject> getAllNearbyObjects(SimulationObject obj);

    void addObject(SimulationObject obj);

    void queueObjectRemoval(SimulationObject obj);
    void removeQueuedObjects();
}
