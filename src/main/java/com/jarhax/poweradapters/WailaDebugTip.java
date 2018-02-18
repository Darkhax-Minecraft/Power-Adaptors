package com.jarhax.poweradapters;

import java.util.List;

import javax.annotation.Nonnull;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

@WailaPlugin
public class WailaDebugTip implements IWailaPlugin, IWailaDataProvider {

	@Override
    @Nonnull
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
    	
		currenttip.add(accessor.getBlock().getClass().getName());
        return currenttip;
    }
    
	@Override
	public void register(IWailaRegistrar registrar) {
		
		registrar.registerBodyProvider(this, Block.class);
	}
}