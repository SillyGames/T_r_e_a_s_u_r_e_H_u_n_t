using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System;
using Vuforia;

public class Assets : MonoBehaviour
{

    // Use this for initialization http://localhost:8080/WarGames/images/title.jpg
    [SerializeField]
    private List<string> m_UrlList = new List<string>();
    private int m_loadProgress = 0;
    [SerializeField]
    private Transform m_parent;   
	void Start ()
    {
        for (int i = 0; i < m_UrlList.Count; i++)
        {
            Debug.Log("Start :: ");
            AssetsLoader.Instance.LoadImage(m_UrlList[i], OnImageLoadingComplete, OnImageLoadError);
        }
	}

    private void OnImageLoadingComplete(Texture2D obj)
    {
        m_loadProgress += 1;
        if(m_loadProgress >= m_UrlList.Count)
        {
            m_loadProgress = 0;
            Debug.Log("Change the Scene ");
            //Application.LoadLevel("vuforiaTest");
           
            Application.LoadLevel("vuforiaTest");
        }
        else
        {
            GameObject l_tem = new GameObject("imageTarget");
            l_tem.AddComponent<MeshRenderer>().material.mainTexture = obj;
            l_tem.AddComponent<MeshFilter>();
            l_tem.AddComponent<ImageTargetBehaviour>();
           
         
           l_tem.AddComponent<DefaultTrackableEventHandler>();
            l_tem.transform.parent = m_parent;
            //l_tem.AddComponent<TurnOffBehaviour>();
        }
       
    }

    private void OnImageLoadError(string obj)
    {
        Debug.Log("On Image Load Error :: "+obj);
    }

    // Update is called once per frame
    void Update ()
    {
	
	}
}
