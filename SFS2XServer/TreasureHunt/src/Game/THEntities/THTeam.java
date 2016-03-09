/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game.THEntities;

import Game.Keys;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import java.util.List;

/**
 *
 * @author Janhavi
 */
public class THTeam extends THElement
{
    private TreasureHunt m_parent = null;    
    protected TreasureHunt GetParentHunt()
    {
        return m_parent;
    }    
    protected void SetParentHunt(TreasureHunt a_parent)
    {
        m_parent = a_parent;
    }
    
    protected void SetID(Long a_iID)
    {
        SetID(a_iID.toString());
    }
    
    protected int GetIntID()
    {
        return Integer.parseInt(GetID());
    }
    /**
     *
     * @param a_bIncludeGlobal
     * @return
     */
    public List<THPlayer> GetTeamPlayers(boolean a_bIncludeGlobal)
    {
        return GetParentHunt().GetTeamPlayers(GetID(), a_bIncludeGlobal);
    }
    
    public List<THPlayer> GetTeamPlayers()
    {
        return GetTeamPlayers(false);
    }
    
    public List<THPlayer> GetTeamClues(boolean a_bIncludeGlobal)
    {
        return GetParentHunt().GetTeamClues(a_bIncludeGlobal);
    }
    
    public List<THPlayer> GetTeamClues()
    {
        return GetTeamClues(false);
    }
    
    static THTeam CreateFromData(ISFSObject a_data)
    {
        THTeam team = new THTeam();
        team.setName(a_data.getUtfString(Keys.TH_TEAM_NAME));
        return team;
    }
}
