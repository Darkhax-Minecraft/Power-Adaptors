package com.jarhax.poweradapters.adapters.caps;

import com.jarhax.poweradapters.adapters.IPowerAdapter;

import net.darkhax.tesla.api.implementation.BaseTeslaContainer;
import net.minecraft.nbt.NBTTagCompound;

public class BaseContainerTesla extends BaseTeslaContainer {

    private IPowerAdapter adapter;

    public BaseContainerTesla () {
        
    }

    public BaseContainerTesla (IPowerAdapter adapter) {

        this.adapter = adapter;
    }

    public BaseContainerTesla (long capacity, long input, long output, IPowerAdapter adapter) {

        super(capacity, input, output);
        this.adapter = adapter;
    }

    public BaseContainerTesla (long power, long capacity, long input, long output, IPowerAdapter adapter) {

        super(power, capacity, input, output);
        this.adapter = adapter;
    }

    public BaseContainerTesla (NBTTagCompound dataTag, IPowerAdapter adapter) {

        super(dataTag);
        this.adapter = adapter;
    }

    @Override
    public long getStoredPower () {

        return this.adapter.getLocalStored();
    }

    @Override
    public long givePower (long Tesla, boolean simulated) {

        return this.adapter.addPower(Tesla, simulated);
    }

    @Override
    public long takePower (long Tesla, boolean simulated) {

        return this.adapter.takePower(Tesla, simulated);
    }

    @Override
    public long getCapacity () {

        return this.adapter.getLocalCapacity();
    }

    @Override
    public long getInputRate () {

        return this.adapter.getLocalInput();
    }

    @Override
    public long getOutputRate () {

        return this.adapter.getLocalOutput();
    }

}
