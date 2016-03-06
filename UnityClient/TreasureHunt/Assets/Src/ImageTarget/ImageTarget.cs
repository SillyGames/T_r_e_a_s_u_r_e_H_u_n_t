using UnityEngine;
using System.Collections;

public class ImageTarget : MonoBehaviour
{
    [SerializeField]
    private string m_imageTargetID;
    public string ImageTargetID
    {
        get
        {
            return m_imageTargetID;
        }
    }
    [SerializeField]
    private string m_currentActiveTargetID;
    public string CurrentCurrentActiveChildID
    {
        get
        {
            return m_currentActiveTargetID;
        }
    }
    // Use this for initialization
    void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}
