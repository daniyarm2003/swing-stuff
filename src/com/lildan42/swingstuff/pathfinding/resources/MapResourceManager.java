package com.lildan42.swingstuff.pathfinding.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class MapResourceManager<ResourceKey, Resource> implements ResourceManager<ResourceKey, Resource> {

    private final Map<ResourceKey, Resource> resources = new HashMap<>();

    @Override
    public void loadResource(ResourceKey key, Resource resource) {
        if(key == null || resource == null) {
            throw new IllegalArgumentException("key or resource cannot be null");
        }

        this.resources.put(key, resource);
    }

    @Override
    public boolean isResourceLoaded(ResourceKey key) {
        return this.resources.containsKey(key);
    }

    @Override
    public Resource getLoadedResource(ResourceKey key) {
        if(!this.isResourceLoaded(key)) {
            throw new NoSuchElementException("Resource with key %s is not loaded".formatted(key.toString()));
        }

        return this.resources.get(key);
    }
}
