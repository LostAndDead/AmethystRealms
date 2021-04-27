package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class TakeMeToTheMoon implements CommandExecutor {

    private final AmethystRealmsCore core;

    public TakeMeToTheMoon(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p;

        if(args.length == 0){
            sender.sendMessage("也 " + ChatColor.RED + "Player Not Found" + ChatColor.RESET + " 也");
            return true;
        }

        Player toFly = Bukkit.getPlayer(args[0]);
        if(toFly == null){
            sender.sendMessage("也 " + ChatColor.RED + "Player Not Found" + ChatColor.RESET + " 也");
            return true;
        }else{
            p = toFly;
        }

        World world = p.getWorld();
        Location startLoc = p.getLocation().clone();

        p.playSound(p.getLocation(), "minecraft:to_the_moon", 1, 1);

        PotionEffect effect = new PotionEffect(PotionEffectType.LEVITATION, 540, 1, true, true, false);
        p.addPotionEffect(effect);

        new BukkitRunnable(){
            int count = 0;
            @Override
            public void run() {
                world.spawnParticle(Particle.CLOUD, p.getLocation().getX(), p.getLocation().getY() - 0.15, p.getLocation().getZ(), 5, 0.1, 0.2, 0.1, 0.01);
                world.spawnParticle(Particle.FLAME, p.getLocation().getX(), p.getLocation().getY() - 0.15, p.getLocation().getZ(), 3, 0.1, 0.2, 0.1, 0.02);
                count ++;
                if (count >= 140){
                    this.cancel();
                }
            }
        }.runTaskTimer(core, 2, 2);

        Bukkit.getScheduler().runTaskLater(core, new Runnable() {
            @Override
            public void run() {
                PotionEffect effect = new PotionEffect(PotionEffectType.LEVITATION, 300, 10, true, true, false);
                p.removePotionEffect(PotionEffectType.LEVITATION);
                p.addPotionEffect(effect);
            }
        }, 260);
        new BukkitRunnable(){
            int count = 0;
            @Override
            public void run() {
                world.spawnParticle(Particle.CLOUD, p.getLocation().getX(), p.getLocation().getY() + 0.25, p.getLocation().getZ(), 20, 0.25, 0.2, 0.25, 0.15);
                world.spawnParticle(Particle.FLAME, p.getLocation().getX(), p.getLocation().getY() + 0.25, p.getLocation().getZ(), 10, 0.1, 0.3, 0.1, 0.2);
                count ++;
                if (count >= 145){
                    this.cancel();
                }
            }
        }.runTaskTimer(core, 260, 2);

        Bukkit.getScheduler().runTaskLater(core, new Runnable() {
            @Override
            public void run() {
                p.sendMessage("也 " + ChatColor.RED + "Low Fuel! Engine failure imminent" + ChatColor.RESET + " 也");
            }
        }, 500);

        Bukkit.getScheduler().runTaskLater(core, new Runnable() {
            @Override
            public void run() {
                PotionEffect effect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5, 99999, true, true, false);
                p.addPotionEffect(effect);
                p.teleport(startLoc);
                p.sendMessage(ChatColor.GRAY + "JK, you are fine.");
            }
        }, 600);
        return true;
    }
}
