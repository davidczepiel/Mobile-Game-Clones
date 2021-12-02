using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlaceBoardInScreen : MonoBehaviour
{
    const int OFFSET = 1;

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

        float scale;
        if (Screen.height > Screen.width)
            scale = uniWidth / boardSizeX;
        else
            scale = unitHeight / boardSizeY;

        this.transform.localScale = new Vector3(scale, scale, 0);

        float posX = -(scale * (boardSizeX - OFFSET)) / 2;
        float posY = (scale * (boardSizeY - OFFSET)) / 2;

        this.transform.position = new Vector3(posX, posY, 0);
    }


}
