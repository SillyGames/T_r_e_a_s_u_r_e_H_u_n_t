/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game.THEntities;

import Game.Keys;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Janhavi
 */
public class THClue extends  THElement
{
    static THClue CreateFromData(ISFSObject clueData)
    {
        THClue clue = new THClue();
        clue.setName(clueData.getUtfString(Keys.TH_CLUE_NAME));
        clue.SetText(clueData.getUtfString(Keys.TH_CLUE_TEXT));
        clue.SetImageID(clueData.getUtfString(Keys.TH_CLUE_IMAGE));
        clue.SetTrackerID(clueData.getUtfString(Keys.TH_CLUE_TRACKER));
        return clue;
    }
    
    @JsonProperty
    private String m_strTeamID = "";
    public String GetContainerID()
    {
        return m_strTeamID;
    }
    
    public void SetContainerID(String a_strTeamID)
    {
        m_strTeamID = a_strTeamID;
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
        return GetParentHunt().GetTeamByID(GetContainerID());
    }
    
    @JsonProperty
    private THClueData m_data = new THClueData();
    public String GetTitle(){return getName();}
    public String GetText(){return m_data.m_strText;}
    public String GetImageID(){return m_data.m_strImage;}
    public String GetTrackeID(){return m_data.m_strTracker;}
    public void SetTitle(String a_strValue){setName(a_strValue);}
    public void SetText(String a_strValue){m_data.m_strText = a_strValue;}
    public void SetImageID(String a_strValue){m_data.m_strImage = a_strValue;}
    public void SetTrackerID(String a_strValue){m_data.m_strTracker = a_strValue;}
    
    @JsonProperty
    private List<String> m_lstSubClueIDs = new ArrayList<String>();

}
