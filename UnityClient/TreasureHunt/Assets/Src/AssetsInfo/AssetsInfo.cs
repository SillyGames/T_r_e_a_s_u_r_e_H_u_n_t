using UnityEngine;
using System.Collections;
using System;

[Serializable]
public class AssetsInfo
{
    [SerializeField]
    private int m_assetID;
    public int AssetID
    {
        get
        {
            return m_assetID;
        }
        set
        {
            m_assetID = value;
        }
    }

    [SerializeField]
    private string m_imageTargetID;
    public string ImageTargetID
    {
        get
        {
            return m_imageTargetID;
        }
        set
        {
            m_imageTargetID = value;

        }
    }
    [SerializeField]
    private string m_assetsName;
    public string AssetName
    {
        get
        {
            return m_assetsName;
        }
        set
        {
            m_assetsName = value;

        }
    }
    private string m_url;
    public string URL
    {
        get
        {
            return m_url;
        }
        set
        {
            m_url = value;
        }
    }
}	

