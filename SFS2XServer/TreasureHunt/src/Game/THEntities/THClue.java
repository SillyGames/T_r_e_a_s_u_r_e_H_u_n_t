/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game.THEntities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Janhavi
 */
public class THClue extends  THElement
{
    @JsonProperty
    private String m_strTeamID = "";
    public String GetTeamID()
    {
        return m_strTeamID;
    }
    
    private TreasureHunt m_parent = null;    
    protected TreasureHunt GetParentHunt()
    {
        return m_parent;
    }   
    protected void SetParentHunt(TreasureHunt a_parent)
    {
        m_parent = a_parent;
    }
    
    public THTeam GetTeam()
    {
        return GetParentHunt().GetTeamByID(GetTeamID());
    }
}
