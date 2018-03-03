package com.jarhax.poweradapters.adapters;

import com.jarhax.poweradapters.InternalBattery;
import com.jarhax.poweradapters.adapters.caps.BaseContainerTesla;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TeslaAdapter extends IPowerAdapter {
    
    private BaseContainerTesla container;
    
    public TeslaAdapter(InternalBattery battery) {
        
        super(battery);
        container = new BaseContainerTesla(this);
    }
    
    @Override
    public long getExchangeRate() {
        
        // Tesla = 25 internal power
        return 25;
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_HOLDER) {
            return true;
        }
        return false;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_HOLDER) {
            return (T) container;
        }
        return null;
    }
    
}