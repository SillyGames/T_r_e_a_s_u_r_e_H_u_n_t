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
        // FiniteStateMachine.Instance.ChangeState(FSMState.EState.Game);
        NetworkManager.Instance.addEventListener(NetworkManager.eGameEvents.getAssetsInfo.ToString(), OnGetAllAssetsInfo);
        NetworkManager.Instance.SendCustomRequestToGetAssetsInfo("xxx");
    }

    private void OnGetAllAssetsInfo(object sender, GameEventArgs args)
    {
        NetworkManager.Instance.removeEventListener(NetworkManager.eGameEvents.getAssetsInfo.ToString());

        List<AssetsInfo> l_allCurrentInfoList = (List<AssetsInfo>)args.EventData;
        for (int indexAssetinfo = 0; indexAssetinfo < l_allCurrentInfoList.Count; indexAssetinfo++)
        {
            Debug.Log(l_allCurrentInfoList[indexAssetinfo].AssetID);
        }
    }

    // Update is called once per frame
    void Update () {
	
	}
}
