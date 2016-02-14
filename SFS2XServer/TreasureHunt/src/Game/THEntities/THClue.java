/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game.THEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

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
    
    @JsonProperty
    private THClueData m_data = new THClueData();
    public String GetTitle(){return m_data.m_strTitle;}
    public String GetText(){return m_data.m_strText;}
    public String GetImageID(){return m_data.m_strImage;}
    public String GetTrackeID(){return m_data.m_strTracker;}
    public void SetTitle(String a_strValue){m_data.m_strTitle = a_strValue;}
    public void SetText(String a_strValue){m_data.m_strText = a_strValue;}
    public void SetImageID(String a_strValue){m_data.m_strImage = a_strValue;}
    public void SetTrackerID(String a_strValue){m_data.m_strTracker = a_strValue;}
    
    @JsonProperty
    private List<String> m_lstSubClueIDs = new ArrayList<String>();

}
