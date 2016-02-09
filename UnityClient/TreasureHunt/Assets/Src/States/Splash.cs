using UnityEngine;
using System.Collections;

public class Splash : FSMState
{

    // Use this for initialization
    public override void Init()
    {
        base.Init();
    }

    public override void OnEnter()
    {
        base.OnEnter();
        FiniteStateMachine.Instance.LoadLevelChangeState(SceneToLoad, State);
    }

    public override void OnExit()
    {
        base.OnExit();
    }
}
