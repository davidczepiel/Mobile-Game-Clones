using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraPlacer : MonoBehaviour
{
    [SerializeField]
    Camera _camera;
    public void locateCamera()
    {
        float unitWidth = (Screen.width * _camera.orthographicSize * 2) / Screen.height;
        this.transform.position = new Vector3(unitWidth/2, _camera.orthographicSize, -10);
    }

    public float getOrthographicSize()
    {
        return _camera.orthographicSize;
    }
}
