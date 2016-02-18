package Game.THEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.Iterator;
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
    public THTeam AddTeam()
    {
        THTeam team = new THTeam();
        m_lstTeams.add(team);
        return team;
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
        
    @JsonProperty
    private List<THPlayer> m_lstPlayers = new ArrayList<THPlayer>();    
    public List<THPlayer> GetAllPlayers()
    {
        return m_lstPlayers.subList(0, m_lstPlayers.size());
    }
    public List<THPlayer> GetTeamPlayers(String a_strTeamID, boolean a_bIncludeGlobal)
    {
        List<THPlayer> lstTeamPlayers = new ArrayList<THPlayer>();
        for (THPlayer player : GetAllPlayers())
        {
            String strTeamID = player.GetTeamID();
            if(strTeamID.equals(a_strTeamID) || (a_bIncludeGlobal && "".equals(strTeamID)))
            {
                lstTeamPlayers.add(player);
            }
        }
        return lstTeamPlayers;
    }
    public THPlayer GetPlayerByID(String a_playerID)
    {
        return GetPlayerByID(m_lstPlayers, a_playerID);
    }
    
    @JsonProperty
    private List<THPlayer> m_lstRequesters = new ArrayList<THPlayer>();    
    public THPlayer GetRequesterByID(String a_playerID)
    {
        return GetPlayerByID(m_lstRequesters, a_playerID);
    }

    public THPlayer AddReQuester(String a_playerID, String a_playerName)
    {
        THPlayer player = new THPlayer();
        player.SetID(a_playerID);
        player.setName(a_playerName);
        return player;
    }
    
    private THPlayer GetPlayerByID(List<THPlayer> a_lstPlayers, String a_playerID)
    {
        for (THPlayer player : a_lstPlayers)
        {
            if(player.GetID().equals(a_playerID))
            {
                return player;
            }
        }
        return null;
    }
    
    @JsonProperty
    private List<THClue> m_lstClues = new ArrayList<THClue>();
    public List<THClue> GetAllClues()
    {
        return m_lstClues.subList(0, m_lstClues.size());
    }
    public THClue AddClue()
    {
        THClue clue = new THClue();
        m_lstClues.add(clue);
        return clue;
    }

    @JsonProperty
    private List<THConnection> m_lstConnections = new ArrayList<THConnection>();
    public List<THConnection> GetConnections()
    {
        return m_lstConnections.subList(0, m_lstConnections.size());
    }
    
//<editor-fold defaultstate="collapsed" desc="Run time code">
    //connections that were used up during the game
    private List<THConnection> m_lstUsedConnections = new ArrayList<THConnection>();
    
    private EnumMap<THEvent.EventType,List<THConnection>> m_eventDictionary = new EnumMap<THEvent.EventType, List<THConnection>>(THEvent.EventType.class);
    
    private  void PrepareToStart()
    {
        for(THEvent.EventType eventType : THEvent.EventType.values())
        {
            if(eventType != THEvent.EventType.None)
            {
                m_eventDictionary.put(eventType, new ArrayList<THConnection>());
            }
        }
        
        for (THConnection connection : m_lstConnections)
        {
            THEvent.EventType eventType = connection.GetEvent().GetType();
            if(eventType != THEvent.EventType.None)
            {
                m_eventDictionary.get(eventType).add(connection);
            }
            else
            {
                THTrace("Connection with eveent type 'none': " + connection);
            }
        }
    }
//</editor-fold>
    
}