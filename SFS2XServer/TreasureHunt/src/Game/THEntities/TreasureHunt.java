package Game.THEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Janhavi
 */
public class TreasureHunt extends THTeam
{
    @JsonProperty
    private List<THTeam> m_lstTeams = new ArrayList<THTeam>();
    public int GetTeamCount()
    {
        return m_lstTeams.size();
    }
        
    @JsonProperty
    private List<THPlayer> m_lstPlayers = new ArrayList<THPlayer>();    
    public List<THPlayer> GetAllPlayers()
    {
        return m_lstPlayers.subList(0, m_lstPlayers.size());
    }
    
    @JsonProperty
    private List<THPlayer> m_lstRequesters = new ArrayList<THPlayer>();

    public List<THPlayer> GetTeamPlayers(String a_strTeamID, boolean a_bIncludeGlobal)
    {
        List<THPlayer> lstTeamPlayers = new ArrayList<THPlayer>();
        for (THPlayer player : GetAllPlayers())
        {
            String strTeamID = player.GetTeamID();
            if(strTeamID.equals(a_strTeamID) || (a_bIncludeGlobal && strTeamID == ""))
            {
                lstTeamPlayers.add(player);
            }
        }
        return lstTeamPlayers;
    }
    
    @JsonProperty
    private List<THClue> m_lstClues = new ArrayList<THClue>();
    public List<THClue> GetAllClues()
    {
        return m_lstClues.subList(0, m_lstClues.size());
    }

    public THTeam GetTeamByID(String a_strTeamID)
    {
        for (THTeam team : m_lstTeams)
        {
            if(team.GetID().equals(a_strTeamID))
            {
                return team;
            }
        }
        return null;
    }
    
}