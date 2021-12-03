using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlaceBoardInScreen : MonoBehaviour
{
    const int OFFSET = 1;

    float scale;
    float posX, posY;

    /// <summary>
    /// Situa el tablero centrado en el espacio del juego
    /// </summary>
    /// <param name="boardSizeX"> tamaio del tablero en x</param>
    /// <param name="boardSizeY"> tamanio del tablero en y</param>
    /// <param name="canvasHeight"> tamanio del espacio del juego</param>
    /// <param name="cameraUnit"> unidades de la camara</param>
    public void placeBoard(int boardSizeX, int boardSizeY, int canvasHeight, float cameraUnit)
    {
        float unitHeight = (canvasHeight * cameraUnit * 2) / Screen.height;
        float uniWidth = (Screen.width * unitHeight) / canvasHeight;

        if (Screen.height > Screen.width)
            scale = uniWidth / boardSizeX;
        else
            scale = unitHeight / boardSizeY;

        transform.localScale = new Vector3(scale, scale, 0); 

        posX = -(scale * (boardSizeX - OFFSET)) / 2;
        posY = (scale * (boardSizeY - OFFSET)) / 2;

        transform.position = new Vector3(posX, posY, 0);
    }

    public Vector2 getPos()
    {
        return new Vector2(posX, posY);
    }

    public float getScale()
    {
        return scale;
    }


}
