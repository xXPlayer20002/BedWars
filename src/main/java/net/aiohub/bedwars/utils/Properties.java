package net.aiohub.bedwars.utils;

import net.minecraft.server.v1_8_R3.DedicatedServer;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class Properties {

    public static void savePropertiesFile() {
        ((DedicatedServer) MinecraftServer.getServer()).propertyManager.savePropertiesFile();
    }

    public static void setServerProperty(ServerProperty property, Object value) {

        ((DedicatedServer) MinecraftServer.getServer()).propertyManager.setProperty(property.getPropertyName(), value);
    }

    public enum ServerProperty {

        SPAWN_PROTECTION("spawn-protection"),
        MAX_PLAYERS("max-players");

        private String propertyName;

        ServerProperty(String propertyName) {
            this.propertyName = propertyName;
        }

        public String getPropertyName() {
            return propertyName;
        }

    }

}
