package me.FluffyWolfers.CR;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CRCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if(sender instanceof Player){
			
			Player p = (Player) sender;
			//String name = p.getName();
			
			if(args.length == 0){
				
				p.sendMessage(CR.getPrefix() + "Version: " + ChatColor.GREEN + "v" + CR.pdf.getVersion());
				p.sendMessage(CR.getPrefix() + "Creator: " + ChatColor.GREEN + "FluffyWolfers");
				p.sendMessage(CR.getPrefix() + "Type /cr help");
				
			}else if(args.length > 0){
				
				String command = args[0];
				
				if(command.equalsIgnoreCase("help")){
					
					p.sendMessage(CR.getPrefix() + "/cr help - Displays help menu");
					
				}else if(command.equalsIgnoreCase("create")){
					
					if(p.hasPermission("chestregen.create")){
						
						if(!(args.length > 1)){
							p.sendMessage(CR.getPrefix() + ChatColor.DARK_RED + "Error! /cr create <name> <regentime>");
						}else{
							
							String cName = args[1];
							
							if(p.getTargetBlock(null, 10) != null){
								
								Block targetBlock = p.getTargetBlock(null, 10);
								
								if(targetBlock.getType().equals(Material.CHEST)||targetBlock.getType().equals(Material.TRAPPED_CHEST)){
									
									Chest chest = (Chest) targetBlock;
									Inventory inv = chest.getInventory();
									
									File file = new File(CR.c.getDataFolder(), File.separator + "chests" + File.separator + cName + ".yml");
									YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
									
									int counter = 1;
									
									for(ItemStack is : inv.getContents()){
										
										if(is != null){
											
											yaml.set("inv.slot" + String.valueOf(counter), is);
											
											counter++;
											
										}
										
									}
									
									counter--;
									
									yaml.set("inv.amm", counter);
									
									try{
										yaml.save(file);
									}catch(Exception e){e.printStackTrace();}
									
									p.sendMessage(CR.getPrefix() + "Success! Chest inventory saved to " + cName + ".yml");
									
								}else{
									p.sendMessage(CR.getPrefix() + ChatColor.DARK_RED + "Error! You must be looking at a chest or a trapped chest!");
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
		return false;
	}

}
