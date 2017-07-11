package me.iran.potato.factions;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Shayan on 6/28/2017.
 */
public class SaveRunnable extends BukkitRunnable {

    private int timer = (3600 * 2);

    @Override
    public void run() {

        timer--;

        if(timer <= 0) {

            System.out.println("[PotatoTeams] Saving all Teams!");
            PlayerFactionManager.getManager().savePlayerFactions();

            System.out.println("[PotatoTeams] Loading all Teams!");
            PlayerFactionManager.getManager().loadPlayerFactions();

            timer = (3600 * 2);

        }

    }
}
