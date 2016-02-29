using UnityEngine;
using System.Collections;
using System;

[Serializable]
public class AssetsInfo
{
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

