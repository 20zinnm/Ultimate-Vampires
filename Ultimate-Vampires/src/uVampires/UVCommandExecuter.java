package uVampires;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class UVCommandExecuter implements CommandExecutor {
	
	UVampires uv = new UVampires();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("vampires")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players can use this command!");
				return true;
			}
			if (args.length == 0) {
				if (args.length == 0) {
					sender.sendMessage("-=uVampires=-");
					sender.sendMessage("/vampires sacrifice: sacrifice a diamond to the gods to become normal!");
					if (sender.hasPermission("uvampires.add")) sender.sendMessage("/vampires add: Make someone a vampire!");
					if (sender.hasPermission("uvampires.remove")) sender.sendMessage("/vampires remove: Make someone normal again!");
					return true;
				}
			}
			if (args.length > 3) {
				sender.sendMessage("Too many arguments!");
				return false;
			}
			if (args[0].equalsIgnoreCase("add") && sender.hasPermission("uvampires.add")) {
				if (Bukkit.getServer().getPlayer(args[1]) != null) {
					Player p = Bukkit.getServer().getPlayer(args[1]);
					UVampires.vampires.put(p, true);
					p.sendMessage(ChatColor.DARK_AQUA + "You are now a vampire!");
					sender.sendMessage(ChatColor.DARK_AQUA + p.getName() + " is now a vampire!");
				} else return false;
			}
			if (args[0].equalsIgnoreCase("remove") && sender.hasPermission("uvampires.remove")) {
				if (Bukkit.getServer().getPlayer(args[0]) != null) {
					Player p = Bukkit.getServer().getPlayer(args[1]);
					UVampires.vampires.put(p, false);
					p.sendMessage(ChatColor.DARK_AQUA + "You are no longer a vampire!");
					sender.sendMessage(ChatColor.DARK_AQUA + p.getName() + " is no longer a vampire.");
				} else {
					if (!sender.hasPermission("uvampires.sacrifice")) sender.sendMessage("You don't have permission to use this command!");
				}
			}
			if (args[0].equalsIgnoreCase("sacrifice") && sender.hasPermission("uvampires.sacrifice")) {
				Player p = (Player) sender;
				if (p.getInventory().getItemInHand().equals(Material.DIAMOND)) {
					PlayerInventory playerinv = p.getInventory();
					ItemStack itemstack = new ItemStack(Material.DIAMOND, 1);
					playerinv.remove(itemstack);
					UVampires.vampires.put(p, false);
					p.sendMessage(ChatColor.DARK_AQUA + "Your sacrifice to the gods has been accepted. You are no longer a vampire!");
					return true;
				} else {
					if (!sender.hasPermission("uvampires.sacrifice")) {
						sender.sendMessage("You don't have permission to use this command!");
						return true;
					}
					if (!p.getInventory().getItemInHand().equals(Material.DIAMOND)) {
						p.sendMessage(ChatColor.DARK_AQUA + "You must be holding a diamond for the gods to hear you. :P.");
						return true;
					}
					return false;
				}
			}
			if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("uvampires.reload")) {
				uv.reload();
			} else {
				if (!sender.hasPermission("uvampires.reload")) {
					sender.sendMessage("You don't have permission to use this command!");
				}
			}
			return true;
		}
		return false;
	}
	
}
