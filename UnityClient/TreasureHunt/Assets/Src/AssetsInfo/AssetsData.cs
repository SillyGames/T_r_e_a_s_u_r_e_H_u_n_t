using UnityEngine;
using System.Collections;

public class AssetsData : MonoBehaviour {

    // Use this for initialization
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

    // Update is called once per frame

}
