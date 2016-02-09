using UnityEngine;
using System.Collections;
using System;

public class GameEventArgs : EventArgs
{
    private object m_eventData;
    public System.Object EventData
    {
        get
        {
            return m_eventData;
        }
        set
        {
            m_eventData = value;
        }
    }
}
