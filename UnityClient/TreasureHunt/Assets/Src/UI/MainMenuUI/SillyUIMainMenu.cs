﻿using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;
using Sfs2X.Requests;
using Sfs2X.Entities.Data;

public class SillyUIMainMenu : MonoBehaviour
{
    [SerializeField]
    private Button m_createHuntBtn;
    [SerializeField]
    private Button m_joinHuntBtn;
    [SerializeField]
    private Button m_editHuntBtn;
    [SerializeField]
    private Button m_settingBtn;
    [SerializeField]
    private Button m_quitBtn;
    // Use this for initialization
    void Start ()
    {
        m_createHuntBtn.onClick.AddListener(OnClickCreateBtn);
        m_joinHuntBtn.onClick.AddListener(OnClickJoinHuntBtn);
        m_editHuntBtn.onClick.AddListener(OnClickEditHuntBtn);
        m_settingBtn.onClick.AddListener(OnClickSettingBtn);
        m_quitBtn.onClick.AddListener(OnClickQuitBtn);
	}

    private void OnClickQuitBtn()
    {
       
    }

    private void OnClickSettingBtn()
    {
        
    }

    private void OnClickJoinHuntBtn()
    {
        FiniteStateMachine.Instance.ChangeState(FSMState.EState.GameLobby);
    }

    private void OnClickEditHuntBtn()
    {
        
    }

    private void OnClickCreateBtn()
    {
        Debug.Log("______________sending custom request 'game'");
        NetworkManager.Instance.SendCustomRequestToCreateHunt();
    }

    // Update is called once per frame
    void Update ()
    {
	
	}
}
