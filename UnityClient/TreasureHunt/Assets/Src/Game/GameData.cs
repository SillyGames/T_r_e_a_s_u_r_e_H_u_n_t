﻿using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class GameData : MonoBehaviour {

    // Use this for initialization
   
    public GameObject m_mainCamera;
    //[SerializeField]
    public GameObject m_arCamera;

    [SerializeField]
    private List<ImageTarget> m_allTargetImages;
    public List<ImageTarget> AllImageTargetsImage
    {
        get 
        {
            return m_allTargetImages;
        }
    }
    void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}
