package com.jarhax.poweradapters.adapters;

import com.jarhax.poweradapters.InternalBattery;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public abstract class IPowerAdapter {

	private InternalBattery battery;
	
	public IPowerAdapter(InternalBattery battery) {
		
		this.battery = battery;
	}
	
	abstract long getExchangeRate();
	
	public long getLocalCapacity() {
		
		return battery.getCapacity() / this.getExchangeRate();
	}
	
	public long getLocalStored() {
		
		return battery.getStored() / this.getExchangeRate();
	}
	
	public long getLocalInput() {
		
		return battery.getInput() / this.getExchangeRate();
	}
	
	public long getLocalOutput() {
		
		return battery.getOutput() / this.getExchangeRate();
	}
	
	public long takePower(long requested, boolean simulated) {
		
		final long flooredRequested = Math.min(this.getLocalOutput(), requested);
		final long availablePower = Math.min(flooredRequested, this.getLocalStored());
		return Math.min(availablePower, flooredRequested);
	}
	
	public long addPower(long power, boolean simulated) {
		
		final long acceptedPower = Math.min(this.getLocalInput(), power);
		
		if (!simulated) {
			
			this.battery.addPower(acceptedPower);
		}
		
		return power - acceptedPower;
	}
	
	public InternalBattery getInternalBattery() {
		
		return this.battery;
	}
	
	public abstract boolean hasCapability(Capability<?> capability, EnumFacing facing);
    
    public abstract <T> T getCapability(Capability<T> capability, EnumFacing facing);
}
