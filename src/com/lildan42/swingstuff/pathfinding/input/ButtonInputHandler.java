package com.lildan42.swingstuff.pathfinding.input;

import java.util.HashMap;
import java.util.Map;

public abstract class ButtonInputHandler<MappingIdentifier, ButtonCodeType> {
    private final Map<MappingIdentifier, ButtonInputMapping<ButtonCodeType>> buttonMappings = new HashMap<>();

    protected abstract boolean isButtonDown(ButtonCodeType code);

    public ButtonPressState getMappingPressState(MappingIdentifier mapping) {
        if(!this.buttonMappings.containsKey(mapping)) {
            throw new IllegalArgumentException("Button mapping %s not found".formatted(mapping.toString()));
        }

        return this.buttonMappings.get(mapping).getPressState();
    }

    public void setButtonMapping(MappingIdentifier mapping, ButtonCodeType code) {
        if(!this.buttonMappings.containsKey(mapping)) {
            this.buttonMappings.put(mapping, new ButtonInputMapping<>(code));
        }
        else {
            this.buttonMappings.get(mapping).setCode(code);
        }
    }

    public void updateMappings() {
        for(ButtonInputMapping<ButtonCodeType> mapping : this.buttonMappings.values()) {
            mapping.updatePressState(this.isButtonDown(mapping.getCode()));
        }
    }
}
