package net.aiohub.bedwars.shop;


import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.World;

public class CustomVillager extends EntityVillager{
	

	


	    public CustomVillager(final World world) {
	        super(world);	  
	        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity)this.bukkitEntity).getHandle();
	        net.minecraft.server.v1_8_R3.NBTTagCompound tag = nmsEntity.getNBTTag();
	        if (tag == null) {
	          tag = new net.minecraft.server.v1_8_R3.NBTTagCompound();
	        }
	        nmsEntity.c(tag);
	        tag.setInt("NoAI", 1);
	        nmsEntity.f(tag);
	    }
	    
	    @Override
	    public boolean damageEntity(DamageSource damagesource, float f) {
	    	
	    	return false;
	    }
	    


}
