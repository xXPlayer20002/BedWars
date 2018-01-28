package net.aiohub.bedwars.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Random;

public class GoldVoting {

    private HashMap<Player, GoldVotingEnum> getVoteByPlayer = new HashMap<>();
    private HashMap<GoldVotingEnum, Integer> getVotes = new HashMap<>();
    private GoldVotingEnum goldVotingEnum = GoldVotingEnum.YES;

    public GoldVotingEnum getGoldVotingEnum() {
        return goldVotingEnum;
    }

    public void setVoteByPlayer(Player player, GoldVotingEnum goldVotingEnum) {
        if(getVoteByPlayer(player) == goldVotingEnum) {
            return;
        }
        if (getVoteByPlayer(player) != null) {
            setVote(getVoteByPlayer(player), -1);
        }
        getVoteByPlayer.put(player, goldVotingEnum);
        setVote(goldVotingEnum, 1);
    }

    public void removeVoteByPlayer(Player player) {
        if (getVoteByPlayer(player) != null) {

            setVote(getVoteByPlayer(player), -1);
            getVoteByPlayer.remove(player);
        }
    }

    public GoldVotingEnum getVoteByPlayer(Player player) {
        if (!getVoteByPlayer.containsKey(player)) {
            return null;
        }
        return getVoteByPlayer.get(player);
    }

    public void setVote(GoldVotingEnum goldVotingEnum, int vote) {
        if (getVotes.containsKey(goldVotingEnum)) {
            getVotes.put(goldVotingEnum, getVotes.get(goldVotingEnum) + vote);
        } else {
            getVotes.put(goldVotingEnum, vote);
        }
    }

    public int getVotes(GoldVotingEnum goldVotingEnum) {
        if (!getVotes.containsKey(goldVotingEnum)) {
            return 0;
        }
        return getVotes.get(goldVotingEnum);
    }

    public void handleVoting() {
        if (getVotes(GoldVotingEnum.NO) > getVotes(GoldVotingEnum.YES)) {
            goldVotingEnum = GoldVotingEnum.NO;
            return;
        }
        if (getVotes(GoldVotingEnum.NO) < getVotes(GoldVotingEnum.YES)) {
            goldVotingEnum = GoldVotingEnum.YES;
            return;
        }
        if (getVotes(GoldVotingEnum.NO) == getVotes(GoldVotingEnum.YES)) {
            Random ran = new Random();
            switch (ran.nextInt(2)) {
                case 0:
                    goldVotingEnum = GoldVotingEnum.NO;
                    break;
                case 1:
                    goldVotingEnum = GoldVotingEnum.YES;
                    break;
                default:
                    goldVotingEnum = GoldVotingEnum.YES;
                    break;

            }
            return;
        }
    }


    public enum GoldVotingEnum {
        YES, NO;
    }

}
