package com.jarhax.poweradapters.adapters.caps;

import com.jarhax.poweradapters.adapters.IPowerAdapter;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorageAdapter extends EnergyStorage {
    
    private IPowerAdapter adapter;
    
    public EnergyStorageAdapter(int capacity, IPowerAdapter adapter) {
        super(capacity);
        this.adapter = adapter;
    }
    
    public EnergyStorageAdapter(int capacity, int maxTransfer, IPowerAdapter adapter) {
        super(capacity, maxTransfer);
        this.adapter = adapter;
    }
    
    public EnergyStorageAdapter(int capacity, int maxReceive, int maxExtract, IPowerAdapter adapter) {
        super(capacity, maxReceive, maxExtract);
        this.adapter = adapter;
    }
    
    public EnergyStorageAdapter(int capacity, int maxReceive, int maxExtract, int energy, IPowerAdapter adapter) {
        super(capacity, maxReceive, maxExtract, energy);
        this.adapter = adapter;
    }
    
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return (int) adapter.addPower(maxReceive, simulate);
    }
    
    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return (int) adapter.takePower(maxExtract, simulate);
    }
    
    @Override
    public int getEnergyStored() {
        return (int) adapter.getLocalStored();
    }
    
    @Override
    public int getMaxEnergyStored() {
        return (int) adapter.getLocalCapacity();
    }
    
    @Override
    public boolean canExtract() {
        return adapter.getLocalOutput()>0;
    }
    
    @Override
    public boolean canReceive() {
        return adapter.getLocalInput()>0;
    }
}
