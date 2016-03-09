using UnityEngine;
using System;
using System.Collections;
using System.Collections.Generic;

public class FiniteStateMachine : MonoBehaviour {

    private static FiniteStateMachine m_instance;
    public static FiniteStateMachine Instance
    {
        get { return m_instance; }
    }

    [SerializeField]
    private List<FSMState> m_states = null;
    public List<FSMState> States
    {
        get { return m_states; }
        set { m_states = value; }
    }

    [SerializeField]
    private FSMState.EState m_initialState;
    public FSMState.EState InitialState
    {
        get { return m_initialState; }
        set { m_initialState = value; }
    }

    [SerializeField]
    private FSMState.EState m_currentState;
    public FSMState.EState CurrentState
    {
        get { return m_currentState; }
        set { m_currentState = value; }
    }

    [SerializeField]
    private FSMState.EState m_previousState;
    public FSMState.EState PreviousState
    {
        get { return m_previousState; }
        set { m_previousState = value; }
    }

    void Awake()
    {
        m_instance = this;
        DontDestroyOnLoad(gameObject);
    }

    void Start()
    {
        Initialize();
    }

    public virtual void Initialize()
    {
        for (int l_statesIndex = 0; l_statesIndex < States.Count; l_statesIndex++)
        {
            if (States[l_statesIndex] != null)
            {
                States[l_statesIndex].Init();
            }
        }

        FSMState l_initState = GetGameState(InitialState);
        if (l_initState != null && l_initState.State != FSMState.EState.None)
        {
            ChangeState(InitialState);
        }
        else
        {
            Debug.LogWarning("FiniteStateMachine::Initialize:: Initial state is not set");
        }
    }

    public virtual void ChangeState(FSMState.EState a_newState)
    {
        Debug.Log("FiniteStateMachine::ChangeState:: Change state requested to " + a_newState);

        FSMState l_currentState = GetGameState(CurrentState);
        FSMState l_newState = GetGameState(a_newState);

        if (l_currentState != null)
        {
            l_currentState.OnExit();
        }

        //Swapping  ENUMS
        PreviousState = CurrentState;
        CurrentState = a_newState;

        l_currentState = l_newState;

        if (l_currentState != null)
            l_currentState.OnEnter();
    }

    /// <summary>
    /// 
    /// </summary>
    /// <param name="a_levelIndex"></param>
    //void OnLevelWasLoaded(int a_levelIndex)
    //{
    //    Debug.Log("Loaded Level");

    //    if (Application.loadedLevelName.Equals(m_sceneToLoad))
    //    {
    //        Debug.Log("FiniteStateMachine::OnLevelWasLoaded:: Memory Allocated before GC: " + GC.GetTotalMemory(false));
    //        System.GC.Collect();
    //        Debug.Log("FiniteStateMachine::OnLevelWasLoaded:: Memory Allocated after GC: " + GC.GetTotalMemory(false));
    //        m_sceneToLoad = string.Empty;
    //        ChangeState(m_stateToChange);
    //        m_stateToChange = FSMState.EState.None;
    //    }
    //    else
    //    {
    //        Debug.Log("FiniteStateMachine::OnLevelLoaded::Loaded is not in action");
    //    }

    //}


    [SerializeField]
    string m_sceneToLoad = string.Empty;
    [SerializeField]
    FSMState.EState m_stateToChange;

    public virtual void LoadLevelChangeState(string a_newSceneName, FSMState.EState a_newState)
    {
        Debug.Log("FiniteStateMachine::LoadLevelChangeState:: Change state with level change requested to " + a_newState);
        if (a_newSceneName.Equals(string.Empty))
        {
            Debug.LogWarning("FiniteStateMachine::LoadLevelChangeState:: Scene Name is Empty");
        }
        else
        {
            m_sceneToLoad = a_newSceneName;
            m_stateToChange = a_newState;
            Application.LoadLevel(a_newSceneName);
        }
    }

    private FSMState GetGameState(FSMState.EState a_newState)
    {
        FSMState l_state = null;

        foreach (FSMState l_fsmState in States)
        {
            if (l_fsmState.State == a_newState)
            {
                l_state = l_fsmState;
                break;
            }
        }

        return l_state;
    }

    
}
