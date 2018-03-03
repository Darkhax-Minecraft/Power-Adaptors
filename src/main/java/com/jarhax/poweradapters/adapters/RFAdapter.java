package com.jarhax.poweradapters.adapters;

import buildcraft.api.mj.*;
import cofh.redstoneflux.api.IEnergyConnection;
import com.jarhax.poweradapters.InternalBattery;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class RFAdapter extends IPowerAdapter{
	
	public RFAdapter(InternalBattery battery) {
		
		super(battery);
	}

	@Override
	public long getExchangeRate() {

		// Tesla = 25 internal power
		return 25;
	}
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return false;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return null;
    }
    
}