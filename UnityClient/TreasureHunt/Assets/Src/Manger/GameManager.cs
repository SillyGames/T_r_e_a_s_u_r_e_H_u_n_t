using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;
public class GameManager : MonoBehaviour {

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

  
    private int TotalAssets = 0;
    private List<AssetsData> m_assetsGameObjlst = new List<AssetsData>();
    void Awake()
    {
        m_instance = this;
    }
    void Start ()
    {
	
	}
	
	
    internal void GetHuntInfo()
    {
        Debug.Log("GetHuntInfo");
        m_allAssetsCurrentInfoList = null;
        m_assetsGameObjlst.Clear();
        NetworkManager.Instance.addEventListener(NetworkManager.eGameEvents.getAssetsInfo.ToString(), OnGetAllAssetsInfo);
        NetworkManager.Instance.SendCustomRequestToGetAssetsInfo("");
    }
    private void OnGetAllAssetsInfo(object sender, GameEventArgs args)
    {
        NetworkManager.Instance.removeEventListener(NetworkManager.eGameEvents.getAssetsInfo.ToString());
        m_allAssetsCurrentInfoList = (List<AssetsInfo>)args.EventData;
        FiniteStateMachine.Instance.ChangeState(FSMState.EState.Game);

    }

    

    public void OnEnterGameState()
    {
        LoadingGameAssets();
    }
    private void LoadingGameAssets()
    {
        for (int i = 0; i < Instance.AllAssetsInfoList.Count; i++)
        {
            AssetsLoader.Instance.LoadAssetsBundle(GameManager.Instance.AllAssetsInfoList[i], OnAssetsLoadingComplete, OnLoadingError);
        }
    }

    void OnAssetsLoadingComplete(AssetBundle a_assetsbundle, AssetsInfo a_assetsInfo)
    {
        TotalAssets++;
        GameObject loadedObject;
        loadedObject = Instantiate(a_assetsbundle.LoadAsset(a_assetsInfo.AssetName, typeof(GameObject))) as GameObject;
        loadedObject.SetActive(false);
        m_assetsGameObjlst.Add(loadedObject.GetComponent<AssetsData>());
        if (TotalAssets == GameManager.Instance.AllAssetsInfoList.Count)
        {
          GameState l_gameState =  FiniteStateMachine.Instance.GetCurrentState<GameState>();
            l_gameState.MakeActiveArCameraAndImageTarget(m_assetsGameObjlst);
        }
    }

    void OnLoadingError(string a_error)
    {
        Debug.Log(a_error);
    }

    internal void SendRequestOnTrackbleImageFound(string a_targetID)
    {
        NetworkManager.Instance.SendRequestOnTrackbleImageFound(a_targetID);
    }
}
