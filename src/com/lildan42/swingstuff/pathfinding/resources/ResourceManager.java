package com.lildan42.swingstuff.pathfinding.resources;

public interface ResourceManager<ResourceKey, Resource> {
    void loadResource(ResourceKey key, Resource resource);
    boolean isResourceLoaded(ResourceKey key);

    Resource getLoadedResource(ResourceKey key);
}
