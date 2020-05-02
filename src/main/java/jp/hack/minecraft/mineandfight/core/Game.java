package jp.hack.minecraft.mineandfight.core;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract class Game implements Runnable {
    private final JavaPlugin plugin;
    private Map<Integer, Team> teams = new ConcurrentHashMap<>();
    private Map<UUID, Player> players = new ConcurrentHashMap<>();
    private Future future;

    public Game(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public Collection<Player> getJoinPlayers(){
        return players.values();
    }

    public Collection<Player> getTeamPlayers(int teamId){
        return players.values().stream()
                .filter(player -> player.getTeamId() == teamId).collect(Collectors.toList());
    }

    public Player findPlayer(UUID uuid){
        return players.get(uuid);
    }

    public Collection<Team> getTeams(){
        return teams.values();
    }

    public Team getTeam(int teamId){
        return teams.get(teamId);
    }

    public void setTeam(Team team, int teamId){
        teams.put(teamId, team);
    }

    public int getTeamScore(int teamId){
        int playerScoreSum =  players.values().stream().filter(p->p.getTeamId() == teamId).mapToInt(p->p.getScore()).sum();
        int teamScore = getTeam(teamId).getScore();
        return  playerScoreSum + teamScore;
    }

    abstract public void onStart();
    abstract public void onStop();
    abstract public void onEnd();
}
