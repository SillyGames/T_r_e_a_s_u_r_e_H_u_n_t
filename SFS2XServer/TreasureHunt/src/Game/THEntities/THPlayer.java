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
public class THPlayer extends THElement
{
    @JsonProperty
    private String m_strTeamID = "";
    public String GetTeamID()
    {
        return m_strTeamID;
    }
    public void setTeamID(String a_strTeamID)
    {
        m_strTeamID = a_strTeamID;
    }
    
}
