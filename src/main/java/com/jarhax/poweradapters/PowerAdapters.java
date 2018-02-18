package com.jarhax.poweradapters;

import net.darkhax.bookshelf.registry.RegistryHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = "poweradapters", name = "Power Adapters", version = "@VERSION@", dependencies = "", certificateFingerprint = "@FINGERPRINT@")
public class PowerAdapters {

	private static RegistryHelper helper = new RegistryHelper("poweradapters").enableAutoRegistration().setTab(CreativeTabs.MISC);
	
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event) {
		
		helper.registerBlock(new BlockBasicMJ(), "mj");
		GameRegistry.registerTileEntity(TileEntityMJ.class, "mj");
	}
}
