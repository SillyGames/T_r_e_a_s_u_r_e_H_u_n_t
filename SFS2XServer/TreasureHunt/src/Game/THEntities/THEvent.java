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
public class THEvent extends THElement
{

    /**
     *tells the type of the event
     */
    public enum EventType
    {
        None,

        /**
         * raised when Treasure Hunt Starts (by Hunt Master)
         */
        OnTHStarted,

        /**
         * raised when Treasure Hunt Ends (by Hunt Master)
         */
        OnTHEnded,

        /**
         * can be raised for any specific time since the Treasure Hunt is started
         */
        OnTHTimeElapsed,

        /**
         * raise whenever any clue is either read, seen or tracked
         */
        OnClueDiscovered,

        /**
         * raised whenever any clue is opened (by hunt flow)
         */
        OnClueOpened,

        /**
         * raised whenever any clue is closed (by hunt flow)
         */
        OnClueClosed,

        /**
         * can be raised for any specific time since the clue is opened
         */
        OnClueTimeElapsed,

        /**
         * raised when any clue text is clicked (to read)
         */
        OnClueRead,

        /**
         * raised when any clue image is clicked (to see)
         */
        OnClueSeen,

        /**
         * raised when any clue is tracked through phone camera
         */
        OnClueTracked,

        /**
         * raised when all the clues for a team are solved
         */
        OnTeamDone,
    }
    
    @JsonProperty
    private EventType m_eType = EventType.None;
    public EventType GetType()
    {
        return m_eType;
    }
    public void setType(EventType a_eType)
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
    private long m_lTargetTime = 0;
    public long GetTargetTime()
    {
        return m_lTargetTime;
    }
    public void SetTargetTime(long a_lTargetTime)
    {
        m_lTargetTime = a_lTargetTime;
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
