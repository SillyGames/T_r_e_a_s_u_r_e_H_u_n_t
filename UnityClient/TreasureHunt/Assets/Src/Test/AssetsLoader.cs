using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;

public class AssetsLoader : MonoBehaviour
{
    private static AssetsLoader m_intance;
    public static AssetsLoader Instance
    {
        get
        {
            return m_intance;
        }
    }

    void Awake()
    {
        m_intance = this;
    }
	void Start ()
    {
	
	}
	
	// Update is called once per frame
	void Update ()
    {
	
	}

    public void LoadImage(string a_url, Action<Texture2D> OnImageLoadComplete, Action<string> OnImageLoadError)
    {
        Debug.Log("LoadImage :: ");
        // yield return StartCoroutine(DownLoadImage(a_url, OnImageLoadComplete, OnImageLoadError));
        LoadAsset<Texture2D> l_loadAsset = new LoadAsset<Texture2D>();
        l_loadAsset.RequestToLoadAsset(this, a_url, OnImageLoadComplete, OnImageLoadError);

    }

    

    class LoadAsset<T>
    {

        private Action<T> m_completeCallback;
        private Action<string> m_completeErrorCallback;
        private T m_currentData = default(T);
        public void RequestToLoadAsset(AssetsLoader a_loader, string a_url, Action<T> OnImageLoadComplete, Action<string> OnImageLoadError)
        {
            a_loader.StartCoroutine(DownLoadAsset(a_url, OnImageLoadComplete, OnImageLoadError));
        }
        IEnumerator DownLoadAsset(string a_url, Action<T> OnImageLoadComplete, Action<string> OnImageLoadError)
        {
            m_completeCallback = OnImageLoadComplete;
            m_completeErrorCallback = OnImageLoadError;
            WWW l_wwwRequest = new WWW(a_url);


            Debug.Log("DownLoadImage :: " + l_wwwRequest.isDone);
            while (!l_wwwRequest.isDone)
            {
                
                yield return new WaitForEndOfFrame();
            }
            Type currentLoadType = typeof(T);
            object l_value = null;
            if(currentLoadType == typeof(Texture2D))
            {
                l_value = l_wwwRequest.texture;
            }
            m_currentData = (T)l_value;
            if (l_wwwRequest.error != string.Empty)
            {
                Debug.Log("DownLoadImage ::: >>>> Complete CallBack");
                m_completeCallback.Invoke(m_currentData);
            }
            else
            {
                m_completeErrorCallback.Invoke(l_wwwRequest.error);
            }
        }
    }
}
