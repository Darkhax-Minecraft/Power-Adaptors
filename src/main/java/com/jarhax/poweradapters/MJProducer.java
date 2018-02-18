package com.jarhax.poweradapters;

import buildcraft.api.mj.IMjConnector;
import buildcraft.api.mj.IMjPassiveProvider;
import buildcraft.api.mj.MjAPI;

public class MJProducer implements IMjPassiveProvider {

	@Override
	public boolean canConnect(IMjConnector other) {

		return true;
	}

	@Override
	public long extractPower(long min, long max, boolean simulate) {

		return MjAPI.MJ * 10;
	}
}
