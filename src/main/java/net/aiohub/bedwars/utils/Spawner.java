package net.aiohub.bedwars.utils;

import net.aiohub.bedwars.BedWars;

import org.bukkit.Location;

import lombok.Getter;

@Getter
public class Spawner {

    private SpawnerType spawnerType;
    private Location location;
    public Spawner(SpawnerType spawnerType, Location location) {
        this.spawnerType = spawnerType;
        this.location = location;
    }

    public enum SpawnerType{
        BRONZE, EISEN, GOLD;
    }



}
