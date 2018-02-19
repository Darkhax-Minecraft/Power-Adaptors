package com.jarhax.poweradapters.adapters;

import com.jarhax.poweradapters.InternalBattery;

import buildcraft.api.mj.IMjConnector;
import buildcraft.api.mj.IMjPassiveProvider;
import buildcraft.api.mj.IMjReceiver;

public class MjAdapter extends IPowerAdapter implements IMjReceiver {
	
	public MjAdapter(InternalBattery battery) {
		
		super(battery);
	}

	@Override
	public long getExchangeRate() {

		// 1Mj = 500 internal power
		return 500;
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
}