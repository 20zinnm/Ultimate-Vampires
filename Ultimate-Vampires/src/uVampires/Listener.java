package uVampires;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class Listener {

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			if (e.getEntity() instanceof Player) {
				if (UVampires.vampires.containsKey(e.getDamager())) {
					Boolean bool = UVampires.vampires.get(e.getDamager());
					if (bool) {
						UVampires uv = new UVampires();
						Double damage = uv.getConfig().getDouble("vampires.dealDamage");
						e.setDamage(damage);
						String infectedS = e.getEntity().toString();
						Player infected = Bukkit.getServer().getPlayer(infectedS);
						if (!UVampires.vampires.get(infected)) {
							UVampires.vampires.put(infected, true);
							infected.sendMessage(ChatColor.DARK_AQUA + "You are now a vampire!");
						}
					}
				}
			}	
		}
	}
	
	@EventHandler
	public void PotionSplashEvent(PotionSplashEvent e) {
		for (LivingEntity i : e.getAffectedEntities()) {
			if (i instanceof Player) {
				if (UVampires.vampires.containsKey(i)) {
					Boolean bool = UVampires.vampires.get(i);
					if (bool) {
						if (e.getPotion().equals(PotionType.INSTANT_HEAL)) {
							i.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 2, 10));
						}
						if (e.getPotion().equals(PotionType.INSTANT_DAMAGE)) {
							i.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2, 10));
						}
					}
				}
			}
		}
	}
}
