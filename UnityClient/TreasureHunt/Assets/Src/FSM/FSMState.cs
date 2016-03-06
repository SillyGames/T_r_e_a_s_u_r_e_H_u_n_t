using UnityEngine;
using System.Collections;
using System;

public class FSMState : MonoBehaviour {

    public enum EState
    {
        None,
        Splash,
        Login,
        MainMenu,
        GameLobby,
        Game
    }

    [SerializeField]
    private string m_sceneToLoad = string.Empty;
    public string SceneToLoad
    { 
        get { return m_sceneToLoad; }
        set { m_sceneToLoad = value; }
    }


    [SerializeField]
    private EState m_state = EState.None;
    public EState State
    {
        get { return m_state; }
        set { m_state = value; }
    }

    public virtual void Init()
    {
        Debug.Log("------------- OnInit ------------- : " + State);
    }

    public virtual void OnEnter()
    {
        Debug.Log("------------- OnEnter ------------- : " + State);
    }

    public virtual void OnExit()
    {
        Debug.Log("------------- OnExit ------------- : " + State);
    }

    public static implicit operator Type(FSMState v)
    {
        throw new NotImplementedException();
    }
}
