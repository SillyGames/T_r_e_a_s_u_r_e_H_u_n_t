using UnityEngine;
using System.Collections;

public class DoNotDestroy : MonoBehaviour {

	// Use this for initialization
    void Awake()
    {
        DontDestroyOnLoad(this);
    }
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}
