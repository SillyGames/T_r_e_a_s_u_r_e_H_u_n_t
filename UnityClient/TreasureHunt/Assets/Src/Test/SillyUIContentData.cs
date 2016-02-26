using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;

public class SillyUIContentData : MonoBehaviour
{

    // Use this for initialization
    [SerializeField]
    private Button m_joinTeamBtn;

    private string m_gameID;
    public string GameID
    {
        get
        {
            return m_gameID;
        }
        set
        {
            m_gameID = value;
        }
    }
	void Start ()
    {
        m_joinTeamBtn.onClick.AddListener(OnClickJoinGame);
	}

    private void OnClickJoinGame()
    {
        Debug.Log(m_gameID);
       // FiniteStateMachine.Instance.ChangeState(FSMState.EState.Game);
    }

    // Update is called once per frame
    void Update () {
	
	}
}
