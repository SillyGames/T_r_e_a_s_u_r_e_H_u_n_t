using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;
using System.Collections.Generic;

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
       // 
        GameManager.Instance.GetHuntInfo();
       
    }

    

    // Update is called once per frame
    void Update () {
	
	}
}
