package com.jarhax.poweradapters.adapters;

import buildcraft.api.mj.*;
import com.jarhax.poweradapters.InternalBattery;
import com.jarhax.poweradapters.adapters.caps.*;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class FEAdapter extends IPowerAdapter {
    private EnergyStorageAdapter container;
    
	public FEAdapter(InternalBattery battery) {
		
		super(battery);
		container = new EnergyStorageAdapter((int) battery.getCapacity(), this);
	}

	@Override
	public long getExchangeRate() {

		// Tesla = 25 internal power
		return 25;
	}

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY ) {
            return true;
        }
        return false;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY ) {
            return (T) container;
        }
        return null;
    }
}