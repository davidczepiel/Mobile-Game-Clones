using System.Collections;
using System.Collections.Generic;
using UnityEngine;


/// <summary>
/// Clase encargada del posicionamiento del board al inicio de la partida 
/// </summary>
public class PlaceBoardInScreen : MonoBehaviour
{
    const float OFFSET = 1f;    
    float scale;                //Escala a utilizar para ajustarse a la pantalla (esta representado en unidades de Unity)
    float posX, posY;           //Posicion final del tablero en la pantalla (esta representado en unidades de Unity)

    /// <summary>
    /// Situa el tablero centrado en el espacio del juego
    /// </summary>
    /// <param name="boardSizeX"> tamaio del tablero en x</param>
    /// <param name="boardSizeY"> tamanio del tablero en y</param>
    /// <param name="canvasHeight"> tamanio del espacio del juego</param>
    /// <param name="cameraUnit"> unidades de la camara</param>
    public void placeBoard(float boardSizeX, float boardSizeY, float canvasHeight, float cameraUnit)
    {
        //Calculamos las dimensiones del canvas en unidades de Unity
        float unitHeight = (canvasHeight * cameraUnit * 2) / Screen.height;
        float unitWidth = (Screen.width * unitHeight) / canvasHeight;

        //Sacamos la escala ajustandonos a lo ancho       
        scale = unitWidth / boardSizeX;

        //SI al ajustarnos a lo ancho no nos da a lo alto nos ajustamos a lo alto
        if(scale*boardSizeY > unitHeight) {
            scale = unitHeight/ boardSizeY;
        }
        
        //Nos posicionamos en el centro del canvas y nos movemos la mitad del ancho total del tablero
        posX = unitWidth / 2.0f - (((boardSizeX - OFFSET) / 2.0f) * scale);
        posY = cameraUnit + ((boardSizeY - OFFSET) * scale) / 2.0f;

        //Asignamos
        transform.localScale = new Vector3(scale, scale, 0);
        transform.position = new Vector3(posX, posY, 0);
    }


    //-----------------------------------GETTERS-------------------------------------
    public Vector2 getPos()
    {
        return new Vector2(posX - 0.5f * scale, posY + 0.5f * scale);
    }

    public float getScale()
    {
        return scale;
    }
    //-----------------------------------GETTERS-------------------------------------


}
