package com.jarhax.poweradapters.adapters;

import buildcraft.api.mj.*;
import com.jarhax.poweradapters.InternalBattery;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class MjAdapter extends IPowerAdapter implements IMjReceiver, IMjPassiveProvider {
	
	public MjAdapter(InternalBattery battery) {
		
		super(battery);
	}

	@Override
	public long getExchangeRate() {

		// 1Mj = 100 internal power
		return 100;
	}
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == MjAPI.CAP_PASSIVE_PROVIDER || capability == MjAPI.CAP_CONNECTOR) {
        
            return true;
        }
        return false;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == MjAPI.CAP_PASSIVE_PROVIDER || capability == MjAPI.CAP_CONNECTOR) {
        
            return (T) this;
        }
        return null;
    }
    
    @Override
	public boolean canConnect(IMjConnector other) {

		return true;
	}

	@Override
	public long getPowerRequested() {
		
		return Math.min(this.getLocalInput(), this.getLocalCapacity() - this.getLocalStored());
	}

	@Override
	public long receivePower(long microJoules, boolean simulate) {
		
		return this.addPower(microJoules, simulate);
	}
    
    @Override
    public long extractPower(long min, long max, boolean simulate) {
        return this.takePower(max, simulate);
    }
}