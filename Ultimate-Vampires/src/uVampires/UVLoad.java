package uVampires;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class UVLoad {
	@SuppressWarnings("unchecked")
	public static HashMap<Player, Boolean> load(String path)
	{
		try
		{
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
			Object result = ois.readObject();
			return (HashMap<Player, Boolean>)result;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				if (p.isOp()) {
					ItemStack is = new ItemStack(Material.DIAMOND, 1);
					p.setItemInHand(is);
					ItemMeta im = p.getItemInHand().getItemMeta();
					im.setDisplayName(ChatColor.RED + "Sorry about the error! --UltimateVampires");
				}
			}
		}
		return null;
	}
}
