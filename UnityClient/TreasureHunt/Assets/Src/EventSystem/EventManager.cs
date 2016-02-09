using UnityEngine;
using System.Collections;
using System;

public class DelegateWarapper
{ 
    public delegate void GameEventHandler(object sender, GameEventArgs args);
    public event GameEventHandler RaiseCustomEvent;

    internal void DispatchEvent(object sender, GameEventArgs args)
    {
        if (RaiseCustomEvent != null)
        {
            RaiseCustomEvent(sender, args);
        }
        else
        {
            Debug.Log("Event Is Not Subcribe");
        }
    }
}

public class EventManager 
{
    Hashtable m_eventTable = new Hashtable();
    public void addEvent(string a_eventName , DelegateWarapper.GameEventHandler a_eventHandler)
    {
        if(a_eventName != string.Empty && a_eventName !="")
        {
            if (!isAlReadyEventExists(a_eventName))
            {
                DelegateWarapper eventWrapper = new DelegateWarapper();
                m_eventTable[a_eventName] = eventWrapper;
                eventWrapper.RaiseCustomEvent += a_eventHandler;
            }
            else
            {
                DelegateWarapper eventWrapper = (DelegateWarapper)m_eventTable[a_eventName];
                eventWrapper.RaiseCustomEvent += a_eventHandler;
            }
        }
        else
        {
            Debug.Log("Event is not added ");
        }
       
    }

    private bool isAlReadyEventExists(string a_key)
    {
        if(m_eventTable.ContainsKey(a_key))
        {
            return true;
        }
        return false;
    }
    public void RemoveEvent(string a_eventName)
    {
        if (isAlReadyEventExists(a_eventName))
        {
           // m_eventTable[a_eventName];
            m_eventTable.Remove(a_eventName);
        }
    }
    public void DispatchEvent(object sender ,GameBaseEvent a_baseEvent)
    {
        if(isAlReadyEventExists(a_baseEvent.EventType))
        {
            DelegateWarapper l_delegateWrapper = (DelegateWarapper)m_eventTable[a_baseEvent.EventType];
            //event DelegateWarapper.GameEventHandler RaiseCustomEvent = l_delegateWrapper.RaiseCustomEvent;
            l_delegateWrapper.DispatchEvent(sender, a_baseEvent.Args);
        }
       
    }

    public void ClearAll()
    {
        m_eventTable.Clear();
    }
}
