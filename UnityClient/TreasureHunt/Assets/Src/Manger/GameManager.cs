using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;
public class GameManager : MonoBehaviour {

    // Use this for initialization
    private static GameManager m_instance;
    public static GameManager Instance
    {
        get
        {

            return m_instance;
        }
    }
    List<AssetsInfo> m_allAssetsCurrentInfoList; //= new List<AssetsInfo>();
    public List<AssetsInfo> AllAssetsInfoList
    {
        get
        {
            return m_allAssetsCurrentInfoList;
        }
    }
    void Awake()
    {
        m_instance = this;
    }
    void Start ()
    {
	
	}
	
	void Update () {
	
	}

    internal void GetHuntInfo()
    {
        Debug.Log("GetHuntInfo");
        m_allAssetsCurrentInfoList = null;
        NetworkManager.Instance.addEventListener(NetworkManager.eGameEvents.getAssetsInfo.ToString(), OnGetAllAssetsInfo);
        NetworkManager.Instance.SendCustomRequestToGetAssetsInfo("xxx");
    }
    private void OnGetAllAssetsInfo(object sender, GameEventArgs args)
    {
        NetworkManager.Instance.removeEventListener(NetworkManager.eGameEvents.getAssetsInfo.ToString());
        m_allAssetsCurrentInfoList = (List<AssetsInfo>)args.EventData;
        FiniteStateMachine.Instance.ChangeState(FSMState.EState.Game);

    }
}
