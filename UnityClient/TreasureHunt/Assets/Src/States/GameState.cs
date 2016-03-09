using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;

public class GameState : FSMState
{

   
    GameData m_GameStaticData = null;
    private EventManager m_eventManager;
    public override void Init()
    {
        base.Init();
    }

    public override void OnEnter()
    {
        base.OnEnter();
        m_eventManager = new EventManager();
        FiniteStateMachine.Instance.LoadLevelChangeState(SceneToLoad, State);
        GameManager.Instance.OnEnterGameState();
        Vuforia.DefaultTrackableEventHandler.Instance.addEventListener(Vuforia.DefaultTrackableEventHandler.TrackableEvent.OnTrackingFound.ToString(), OnTrackingFound);
    }

    
    public override void OnExit()
    {
        base.OnExit();
        Vuforia.DefaultTrackableEventHandler.Instance.clearAll();
    }

   

    internal void MakeActiveArCameraAndImageTarget(List<AssetsData> a_assetsGameObjlst)
    {
        m_GameStaticData = GameObject.Find("Game").GetComponent<GameData>();
        for (int indexAllGameAsset = 0; indexAllGameAsset < a_assetsGameObjlst.Count; indexAllGameAsset++)
        {
            GameObject l_gameObject = getAssetParent(a_assetsGameObjlst[indexAllGameAsset].ImageTargetID);
            if(l_gameObject != null)
            {
                a_assetsGameObjlst[indexAllGameAsset].gameObject.transform.parent = l_gameObject.transform;
            }
        }
        m_GameStaticData.m_arCamera.SetActive(true);
        m_GameStaticData.m_mainCamera.SetActive(false);
        for (int indexImageTarget = 0; indexImageTarget < m_GameStaticData.AllImageTargetsImage.Count; indexImageTarget++)
        {
            m_GameStaticData.AllImageTargetsImage[indexImageTarget].gameObject.SetActive(true);
            setCurrentActiveChild(m_GameStaticData.AllImageTargetsImage[indexImageTarget].CurrentCurrentActiveChildID, a_assetsGameObjlst);
        }
       
    }

    private GameObject getAssetParent(string a_childID)
    {
        Debug.Log("game Object Chind :: + "+ a_childID);
        for (int indexImageTarget = 0; indexImageTarget < m_GameStaticData.AllImageTargetsImage.Count; indexImageTarget++)
        {
          
            if (m_GameStaticData.AllImageTargetsImage[indexImageTarget].ImageTargetID == a_childID)
            {
                Debug.Log("Child Parent Is Found :: ");
                return m_GameStaticData.AllImageTargetsImage[indexImageTarget].gameObject;
            }
        }
        return null;
    }

    private void setCurrentActiveChild(string a_activeID, List<AssetsData> a_assetsGameObjlst)
    {
        for (int indexAllGameAsset = 0; indexAllGameAsset < a_assetsGameObjlst.Count; indexAllGameAsset++)
        {
            if (a_assetsGameObjlst[indexAllGameAsset].AssetID.ToString() == a_activeID)
            {
                a_assetsGameObjlst[indexAllGameAsset].gameObject.SetActive(true);
            }
        }
    }

    private void OnTrackingFound(object sender, GameEventArgs args)
    {

    }

}
