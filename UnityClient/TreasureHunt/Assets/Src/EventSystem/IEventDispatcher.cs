using UnityEngine;
using System.Collections;

public interface IEventDispatcher
{
    // Use this for initialization
    void addEventListener(string a_eventName, DelegateWarapper.GameEventHandler a_eventHandler);
    void removeEventListener(string a_eventName);
    void dispatchEvent(object sender, GameBaseEvent a_baseEvent);
    void clearAll();
}
