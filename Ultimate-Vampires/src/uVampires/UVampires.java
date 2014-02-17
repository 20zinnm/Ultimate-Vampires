package uVampires;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UVampires extends JavaPlugin {
	
	public static HashMap<Player, Boolean> vampires = new HashMap<Player, Boolean>();
	
	public void onEnable() {
		saveDefaultConfig();
		String path = getDataFolder() + File.separator + "vampires.bin";
		File file = new File(path);
		if(file.exists()) {
			vampires = load(path);
			getLogger().info("uVampires v0.1 enabled!");
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
			}
		}
	}
	
	public void onDisable() {
		save(vampires, getDataFolder() + File.separator + "vampires.bin");
		saveConfig();
	}
	
	public void save(HashMap<Player, Boolean> map, String path)
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(vampires);
			oos.flush();
			oos.close();
		}
		catch(Exception e)
		{
				e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<Player, Boolean> load(String path)
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
		}
		return null;
	}
}
