package uVampires;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UVampires extends JavaPlugin {
	
	public static HashMap<Player, Boolean> vampires = new HashMap<Player, Boolean>();
	
	public void onEnable() {
		saveDefaultConfig();
		getCommand("vampires").setExecutor(new UVCommandExecuter());
		String path = getDataFolder() + File.separator + "vampires.bin";
		File file = new File(path);
		if(file.exists()) {
			vampires = UVLoad.load(path);
			getLogger().info("uVampires v0.2 enabled!");
		} else {
			getLogger().info("Vampires storage file missing... creating a new one.");
			try {
				file.createNewFile();
				getLogger().info("All good!");
			} catch (IOException e) {
				getLogger().warning("It seems there was an error creating the storage file.");
				getLogger().warning("Please report the following errors to the BukkitDev page:");
				e.printStackTrace();
				getLogger().info("Intimidating spam :P.");
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (p.isOp()) {
						ItemStack is = new ItemStack(Material.DIAMOND, 1);
						p.setItemInHand(is);
						ItemMeta im = p.getItemInHand().getItemMeta();
						im.setDisplayName(ChatColor.RED + "Sorry about the error! --UltimateVampires");
					}
				}
			}
			this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new  Runnable(){
				public void run(){
				  for (Player p : getServer().getOnlinePlayers()) {
					  if (UVampires.vampires.containsKey(p)) {
							Boolean bool = UVampires.vampires.get(p);
							if (bool) {
					            Location l = p.getLocation();     
					            Location blockbellowplayer = new Location(l.getWorld(), l.getX(), l.getY() - 1, l.getZ());
					            if (blockbellowplayer != null) {
					            	int lightlevel = blockbellowplayer.getBlock().getLightLevel();
					            	while (lightlevel < 7) {
					            		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1, 2));
					            		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1, 2));
					            		p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1, 2));
					            	}
					            	while (lightlevel >= 7) {
					            		p.setFireTicks(1);
					            	}
					            }
							}
						}
				  	} 
				  }
				}, 20, 20);
			}
	}
	
	public void onDisable() {
		UVSave.save(vampires, (getDataFolder() + File.separator + "vampires.bin"));
		saveConfig();
		getLogger().info("UVampires v0.2 disabled!");
	}
}
