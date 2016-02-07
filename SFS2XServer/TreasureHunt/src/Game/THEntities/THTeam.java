/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game.THEntities;

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
}
