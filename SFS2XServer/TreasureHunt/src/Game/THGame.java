/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import Game.Extensions.TreasureHuntRoomExtension;
import Game.THEntities.THPlayer;
import Game.THEntities.TreasureHunt;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Janhavi
 */
public class THGame
{
    public void Trace(Object a_object)        
    {
        m_SFSExtension.trace(a_object);
    }
    
    public void TraceW(Object a_object)        
    {
        Trace("WWWWWWWW: " + a_object);
    }
    
    public void TraceE(Object a_object)        
    {
        Trace("EEEEEEEE: " + a_object);
    }
    
    private TreasureHuntRoomExtension m_SFSExtension = null;
    
    public void setName(String a_name)
    {
        m_treasureHunt.setName(a_name);
    }
    
    public String getName()
    {
        return m_treasureHunt.getName();
    }    
    
    //<editor-fold defaultstate="collapsed" desc="State Handling">
    private enum State
    {
        Preparation,
        Hunting,
        Result
    }
    private State m_state = State.Preparation;
    private State GetState()
    {
        return m_state;
    }
    private void SetState(State a_state)
    {
        m_state = a_state;
    }
    
//</editor-fold>
    
    private static List<THGame> s_runningGames = new ArrayList<THGame>();
    
    TreasureHunt m_treasureHunt = new TreasureHunt();
    public void Init(TreasureHuntRoomExtension a_roomExtension)
    {
        Trace("Initing THGame");
        m_SFSExtension = a_roomExtension;
        //its the default state anyway, but setting it here just for the sake of logic
        SetState(State.Preparation);
        //load treasur hunt from json saved   
        s_runningGames.add(this);
    }
    
    public void Destroy()
    {
        s_runningGames.remove(this);
    }
    
    void UpdateStatePreparation(double deltaTime)
    {
        
    }
    
    void UpdateStateHunting(double deltaTime)
    {
        
    }
    
    void UpdateStateResult(double deltaTime)
    {
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Game Events and callbacks">
    //TODO: Ajay, call these functions according to the commented instructions
    
    /**
     * call this function when player succeeds login, and get in to the treasure hunt
     * he may not be a part of the hunt yet, this will be handled as well
     * @param a_playerID : a player ID, must be valid, undefined behavior otherwise
     * @param a_playerName : a display name for player, must not be null, can be empty if existing player
     */
        
    public void OnPlayerConnected(String a_playerID, String a_playerName)
    {
        //if player is new, create player and add it to requesters (tell other people if you want)
        //else update his online status (tell other people if required)
        
        THPlayer player = m_treasureHunt.GetPlayerByID(a_playerID);
        if(player != null )
        {
            player.SetIsOnline(true);
        }
        else
        {
            player = m_treasureHunt.GetRequesterByID(a_playerID);
            if(player != null)
            {
                player.SetIsOnline(true);
            }
            else
            {
                player = m_treasureHunt.AddReQuester(a_playerID, a_playerName);
                player.SetIsOnline(true);
            }
        }
        //notify all about this player being online/added
    }
    
    /**
     * call this function when player gets disconnected (whatever may be the reason)
     * @param a_playerID : a player ID, must be valid
     */
    public void OnPlayerDisconnected(String a_playerID)
    {
        THPlayer player = m_treasureHunt.GetPlayerByID(a_playerID);
        if(player != null )
        {
            player.SetIsOnline(false);
        }
        else
        {
            player = m_treasureHunt.GetRequesterByID(a_playerID);
            if(player != null)
            {
                player.SetIsOnline(false);
            }
            else
            {
                TraceW(" THGame: player was not connected!! ID: " + a_playerID);
            }
        }
        //notify all about this player being offline
    }
    //</editor-fold>
    
    public void updateGame(double deltaTime)
    {
        THGame.State state = GetState();
        switch(state)
        {
        case Preparation:
        {
            UpdateStatePreparation(deltaTime);
            break;
        }
        case Hunting:
        {
            UpdateStateHunting(deltaTime);
            break;
        }
        case Result:
        {
            UpdateStateResult(deltaTime);
            break;
        }
        default:
            throw new AssertionError(state.name());        
        }
        //Trace("UPdating Game Loop: " + deltaTime);
    }
}
