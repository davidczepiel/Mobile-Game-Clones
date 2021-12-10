using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GUIManager : MonoBehaviour
{
    [SerializeField]
    Text lvlText;       //texto donde aparece el numero del nivel
    [SerializeField]
    Text sizeText;      //texto donde aparece el tamanio del nivel
    [SerializeField]
    Text hintsText;     //texto donde aparecen el numero de pistas disponibles

    [SerializeField]
    Text flowsText;     //texto donde aparecen el numero de tuberias conectadas
    [SerializeField]
    Text moveText;      //texto con el numero de movimientos realizados y la mejor puntuación
    [SerializeField]
    Text pipeText;      //texto donde aparece el porcentaje del nivel superado

    /// <summary>
    /// Inicializa la UI con la informacion requerida. El resto de elementos de la interfaz
    /// se inicializan solos
    /// </summary>
    /// <param name="lvl">nivel que se esta jugando actualmente</param>
    /// <param name="sizeX"></param>
    /// <param name="sizeY"></param>
    /// <param name="hints"></param>
    /// <param name="bestMoves"></param>
    public void initGUI(int lvl, int sizeX, int sizeY, int hints, int bestMoves)
    {
        lvlText.text = "Level " + lvl;
        sizeText.text = sizeX + "x" + sizeY;
        hintsText.text = hints + "x";

        flowsText.text = "flows: 0/4";
        moveText.text = "moves:0 best: " + bestMoves;
        pipeText.text = "pipe: 0%";
    }

    public void changeNFlow(int flow)
    {
        flowsText.text = "flows: " + flow + "/4";
    }

    public void changeLvlPercentage(int percentage)
    {
        pipeText.text = "pipe: " + percentage +"%";
    }

    public void changeMoves(int moves, int bestMoves)
    {
        if(bestMoves != 0)
            moveText.text = "moves:" + moves + " best: " + bestMoves;
        else
            moveText.text = "moves:" + moves + " best: - ";
    }

    public void changeHint(int hint)
    {
        hintsText.text = hint + "x";
    }
}
