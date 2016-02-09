using UnityEngine;
using System.Collections;

public class GameBaseEvent
{

    private string m_eventtype;

    public string EventType
    {
        get
        {
            return m_eventtype;
        }
        set

        {
            m_eventtype = value;
        } 
    }


    private GameEventArgs m_eventArgs;
    public GameEventArgs Args
    {
        get
        {
            return m_eventArgs;
        }
        set
        {
            m_eventArgs = value;
        }
    }

    public GameBaseEvent (string a_eventType)
    {
        this.m_eventtype = a_eventType;
    }
	


}
