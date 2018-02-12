package net.aiohub.bedwars;

import de.dytanic.cloudnet.bridge.CloudServer;
import de.dytanic.cloudnet.lib.server.ServerState;

import net.aiohub.bedwars.commands.ForceMap;
import net.aiohub.bedwars.commands.GetLocation;
import net.aiohub.bedwars.commands.Start;
import net.aiohub.bedwars.commands.Stats;
import net.aiohub.bedwars.listeners.BedListener;
import net.aiohub.bedwars.listeners.CancelListener;
import net.aiohub.bedwars.listeners.DeathListener;
import net.aiohub.bedwars.listeners.JoinListener;
import net.aiohub.bedwars.listeners.QuitListener;
import net.aiohub.bedwars.listeners.ShopItemListeners;
import net.aiohub.bedwars.listeners.ShopListener;
import net.aiohub.bedwars.listeners.ShopUseListeners;
import net.aiohub.bedwars.listeners.TeamSelector;
import net.aiohub.bedwars.listeners.VotingListeners;
import net.aiohub.bedwars.net.aiohub.bedwars.countdown.CountdownHandler;
import net.aiohub.bedwars.shop.ShopItemsByConfig;
import net.aiohub.bedwars.utils.GameUtils;
import net.aiohub.bedwars.utils.GoldVoting;
import net.aiohub.bedwars.utils.MapVoting;
import net.aiohub.bedwars.utils.PlayerUtils;
import net.aiohub.bedwars.utils.Properties;
import net.aiohub.bedwars.utils.StatsOfTheGame;
import net.aiohub.bedwars.utils.Status;
import net.aiohub.bedwars.utils.ServerUtils;
import net.aiohub.bedwars.utils.TeleportUtils;
import net.aiohub.databaseapi.MongoDBConnector;
import net.aiohub.statsmodule.stats.StatsAPI;

import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
public class BedWars extends JavaPlugin {

    private PlayerUtils playerUtils = new PlayerUtils();
    private static BedWars instance;
    private TeleportUtils teleportUtils = new TeleportUtils();
    private ServerUtils serverUtils = new ServerUtils();
    private int minPlayers, teams;
    @Setter
    private String mapName;
    private CountdownHandler countdownHandler = new CountdownHandler();
    private File locationsFile = new File("plugins/BedWars", "locations.yml");
    private FileConfiguration locationsConfig;
    private File gameFile = new File("plugins/BedWars", "game.yml");
    private FileConfiguration gameConfig;
    private File itemFile = new File("plugins/BedWars", "items.yml");
    private FileConfiguration itemConfig;
    private GameUtils gameUtils = new GameUtils();
    private GoldVoting goldVoting = new GoldVoting();
    private MapVoting mapVoting = new MapVoting();
    private StatsOfTheGame statsOfTheGame = new StatsOfTheGame();
    private CloudServer cloudServer;
    private List<String> maps;
    private StatsAPI statsAPI;
    private Location hologramLocation;
    private ShopItemsByConfig shopItemsByConfig;
    @Setter
    private boolean isForceMap;
    private static MongoDBConnector mongoDBConnector;
    @Setter
    private Status status;

    @Override
    public void onEnable() {
        instance = this;
        getDataFolder().mkdir();
        setStatus(Status.LOBBY);
        if (!locationsFile.exists())
            loadFile("locations.yml");
        if (!serverUtils.getTeamsFile().exists())
            loadFile("teams.yml");
        if (!gameFile.exists())
            loadFile("game.yml");
        if (!itemFile.exists())
            loadFile("items.yml");

        locationsConfig = YamlConfiguration.loadConfiguration(locationsFile);
        gameConfig = YamlConfiguration.loadConfiguration(gameFile);
        itemConfig = YamlConfiguration.loadConfiguration(itemFile);
        shopItemsByConfig = new ShopItemsByConfig();
        serverUtils.setConfig();
        getThings("items.yml");
        maps = gameConfig.getStringList("Game.Worlds");
        maps.forEach(s -> {
            WorldCreator worldCreator = new WorldCreator(s);
            World world = worldCreator.createWorld();
            world.setDifficulty(Difficulty.EASY);
            world.setWeatherDuration(0);
            world.setThunderDuration(0);
            world.setTime(1000);
            world.setThundering(false);
            world.setStorm(false);

        });


        CloudServer.getInstance().setServerStateAndUpdate(ServerState.LOBBY);

        String maxSize = serverUtils.getTeamsConfiguration().getString("Teams.MaxSize").replace("[", "").replace("]", "");
        Integer maxSizeInt = Integer.valueOf(maxSize);
        serverUtils.setMaxSize(maxSizeInt);
        String minPlayer = gameConfig.getString("Game.MinPlayers").replace("[", "").replace("]", "");
        minPlayers = Integer.valueOf(minPlayer);
        String maxPlayer = gameConfig.getString("Game.MaxPlayers").replace("[", "").replace("]", "");
        String teams = gameConfig.getString("Game.Teams").replace("[", "").replace("]", "");
        this.teams = Integer.valueOf(teams);
        mapName = gameConfig.getStringList("Game.Map").get(0);
        hologramLocation = serverUtils.getHologramLocation();


        Properties.setServerProperty(Properties.ServerProperty.SPAWN_PROTECTION, 0);
        Properties.setServerProperty(Properties.ServerProperty.MAX_PLAYERS, Integer.valueOf(maxPlayer));
        Properties.savePropertiesFile();
        registerListeners();
        registerCommands();
        List<String> values = new ArrayList<>();
        values.add("kills");
        values.add("deaths");
        values.add("beds");
        values.add("wins");
        values.add("games");
        mongoDBConnector = new MongoDBConnector();
        statsAPI = StatsAPI.getInstance();
        statsAPI.setGameMode("bedwars");
        statsAPI.setDefaultValues(values);
        serverUtils.registerTeams();
        CloudServer.getInstance().setMaxPlayers(Integer.valueOf(maxPlayer));
        countdownHandler.onLobby();
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new TeamSelector(), this);
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new ShopListener(), this);
        pluginManager.registerEvents(new ShopItemListeners(), this);
        pluginManager.registerEvents(new CancelListener(), this);
        pluginManager.registerEvents(new BedListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new DeathListener(), this);
        pluginManager.registerEvents(new VotingListeners(), this);
        pluginManager.registerEvents(new ShopUseListeners(), this);
        //Todo: Nothing
    }

    private void registerCommands() {

        getCommand("getlocation").setExecutor(new GetLocation());
        getCommand("start").setExecutor(new Start());
        getCommand("stats").setExecutor(new Stats());
        getCommand("forcemap").setExecutor(new ForceMap());
    }
    @SneakyThrows
    public void getThings(String file) {
        InputStream is = getClass().getResourceAsStream("/" + file);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line + "\n");
        }

    }

    @SneakyThrows
    public void loadFile(String file) {
        File t = new File(this.getDataFolder(), file);
        System.out.println("Writing new file: " + t.getAbsolutePath());

        t.createNewFile();
        FileWriter out = new FileWriter(t);
        InputStream is = getClass().getResourceAsStream("/" + file);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            out.write(line + "\n");
        }
        System.out.println(line);
        out.flush();
        is.close();
        isr.close();
        br.close();
        out.close();

    }


    public static BedWars getInstance() {
        return instance;
    }
}
