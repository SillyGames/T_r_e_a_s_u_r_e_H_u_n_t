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
public class THAction extends THElement
{
    /**
     *tells the type of the event
     */
    public enum ActionType
    {
        None,
        OpenTheClue,
        CloseTheClue,
        FinishTheTeamHunt
    }
    
    @JsonProperty
    private ActionType m_eType = ActionType.None;
    public ActionType GetType()
    {
        return m_eType;
    }
    public void setType(ActionType a_eType)
    {
        m_eType = a_eType;
    }    
    
    @JsonProperty
    private String m_strClueID = "";
    public String GetClueID()
    {
        return m_strClueID;
    }
    public void SetClueID(String a_strClueID)
    {
        m_strClueID = a_strClueID;
    }
    
    @JsonProperty
    private String m_strTeamID = "";
    public String GetTeamID()
    {
        return m_strTeamID;
    }
    public void SetTeamID(String a_strTeamID)
    {
        m_strTeamID = a_strTeamID;
    }
}
