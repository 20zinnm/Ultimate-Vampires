package uVampires;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UVListener implements Listener {

	@EventHandler
	public void onPotionSplashEvent(PotionSplashEvent e ) {
		for (Entity entity : e.getAffectedEntities()) {
			if (entity instanceof Player) {
				Player p = (Player) entity;
				if (!UVampires.vampires.containsKey(p)) return;
				if (UVampires.vampires.get(p)) {
					if (e.getPotion().getEffects().equals(PotionEffectType.HEAL)) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 0, 3));
					}
					if (e.getPotion().getEffects().equals(PotionEffectType.HARM)) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 0, 3));
					}
					if (e.getPotion().getEffects().equals(PotionEffectType.REGENERATION)) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 2, 5));
					}
					if (e.getPotion().getEffects().equals(PotionEffectType.POISON)) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2, 5));
					}
				}
			}
		}
	}
	
}
