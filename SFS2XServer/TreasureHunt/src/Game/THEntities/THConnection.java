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
public class THConnection extends THElement
{
    @JsonProperty
    private THEvent m_event = null;
    public THEvent GetEvent()
    {
        return m_event;
    }

    public void SetEvent(THEvent a_event)
    {
        m_event = a_event;
    }    
    
    @JsonProperty
    private THAction m_action = null;
    public THAction GetAction()
    {
        return m_action;
    }
    
    public void SetAction(THAction a_action)
    {
        m_action = a_action;
    }
    
}
