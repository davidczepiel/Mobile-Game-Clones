using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Clase que modifica la posicion de la camara dependiendo del tamaño de la pantalla
/// </summary>
public class CameraPlacer : MonoBehaviour
{
    [SerializeField]
    Camera _camera;

    /// <summary>
    /// MEtodo que situa la camara en la posicion correcta para visualizar el (0,0) a la esquina inferior izquierda
    /// </summary>
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
