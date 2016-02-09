using UnityEngine;
using System.Collections;
using Sfs2X;
using Sfs2X.Logging;
using Sfs2X.Util;
using Sfs2X.Core;
using Sfs2X.Entities;
using System;
public class Connect : MonoBehaviour
{ 
    private static Connect m_intance;
    public static Connect Intance
    {
        get
        {

            return m_intance;
        }
    }

    [SerializeField]
    private GameObject m_connectingUI;

    void Awake()
    {
        m_intance = this;
    }
    void Start ()
    {
        NetworkManager.Instance.addEventListener(NetworkManager.eGameEvents.ConnectionSuccess.ToString(), OnConnectionSucess);
        NetworkManager.Instance.Connect();
    }

    private void OnConnectionSucess(object sender, GameEventArgs args)
    {
        FiniteStateMachine.Instance.ChangeState(FSMState.EState.Login);
    }
}
