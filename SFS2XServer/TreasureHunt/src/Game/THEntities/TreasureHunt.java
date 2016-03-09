package Game.THEntities;

import Game.IUIDGenerator;
import Game.Keys;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import java.util.ArrayList;
import java.util.EnumMap;
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
public class TreasureHunt extends THTeam implements IUIDGenerator
{
    void TreasureHunt()
    {
        SetID(GetUID());
        SetParentHunt(this);
    }
    
    @JsonProperty
    private List<THTeam> m_lstTeams = new ArrayList<THTeam>();
    public int GetTeamCount()
    {
        return m_lstTeams.size();
    }
    
    public void AddTeam(THTeam a_team)
    {
        for (THTeam team : m_lstTeams)
        {
            if(team.getName().endsWith(a_team.getName()))
            {
                TraceE("Team with a same name already Exists: " + team.getName());
            }
        }
        Trace("Adding Team with a name: " + a_team.getName());
        m_lstTeams.add(a_team);
        a_team.SetParentHunt(this);
        a_team.SetID(GetUID());
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
    private void AddClue(THClue a_clue)
    {
        a_clue.SetID(GetUID().toString());            
        m_lstClues.add(a_clue);
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
                Trace("Connection with eveent type 'none': " + connection);
            }
        }
    }
//</editor-fold>
    
    ///this clue is work in progress
    public static TreasureHunt CreateFromData(ISFSObject a_data)
    {
        //creating a new Hunt is anyways getter since its clean and thread safe
        TreasureHunt hunt = new TreasureHunt();
        String huntName = a_data.getUtfString(Keys.TH_NAME);
        
        Trace("Creating a treasure hunt with a name: " + huntName);
        hunt.setName(huntName);
        
        //read clues that are directly in the hunt
        Trace("Reading Global Clues");
        ReadClues(hunt, hunt, a_data);
        
        //read teams
        int iTeamCount = a_data.getInt(Keys.TH_TEAM);
        Trace("Total Teams in hunt: " + iTeamCount);
        for (int i = 0; i < iTeamCount; i++)
        {
            String strTeamKey = Keys.TH_TEAM+i;
            Trace("Reading team: " + strTeamKey);
            ISFSObject teamData = a_data.getSFSObject(strTeamKey);   
            THTeam  team = THTeam.CreateFromData(teamData);
            hunt.AddTeam(team);
            //read clues in that team
            ReadClues(hunt, team, teamData);
        }       
        
        return hunt;
    }
    
    private static void ReadClues(TreasureHunt hunt, THElement a_parentElement, ISFSObject a_data)
    {
        int iSubClueCount = a_data.getInt(Keys.TH_CLUE);
        if(iSubClueCount > 0)
        {
            Trace("Retrieving clues for the parent: " + a_parentElement + ", count: " + iSubClueCount);
            for (int i = 0; i < iSubClueCount; i++)
            {
                ISFSObject subClueData = a_data.getSFSObject(Keys.TH_CLUE+i);
                THClue clue = THClue.CreateFromData(subClueData);
                clue.SetContainerID(a_parentElement.GetID());
                hunt.AddClue(clue);
                Trace("Created Clue["+i+"]: " + clue);
                ReadClues(hunt, clue,subClueData);
            }
        }
    }
    

    ///its used internally, just for the internal elements, so 
    ///it doesnt need to be static
    private long m_lUIDGenerator = 0;
    @Override
    public Long GetUID()
    {
        return m_lUIDGenerator++;        
    }
    
   
}
