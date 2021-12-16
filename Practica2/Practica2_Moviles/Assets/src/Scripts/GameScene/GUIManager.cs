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
    [SerializeField]
    GameObject panel;   //panel que aparece una vez superado el nivel
    [SerializeField]
    GameObject previusLvl;  //boton de nivel anterior.
    [SerializeField]
    GameObject nextLvlButton, finishNextButton;    //botones de nivel siguiente.

    /// <summary>
    /// Inicializa la UI con la informacion requerida. El resto de elementos de la interfaz
    /// se inicializan solos
    /// </summary>
    /// <param name="lvl">nivel que se esta jugando actualmente</param>
    /// <param name="sizeX"></param>
    /// <param name="sizeY"></param>
    /// <param name="hints"></param>
    /// <param name="bestMoves"></param>
    public void initGUI(int lvl, Color colorText, int sizeX, int sizeY, int hints, int bestMoves, bool ended, bool firstLevel, bool nextLvl)
    {
        lvlText.text = "Level " + (lvl%30 + 1);
        lvlText.color = colorText;
        sizeText.text = sizeX + "x" + sizeY;
        hintsText.text = hints + "x";

        flowsText.text = "flows: 0/4";
        changeMoves(0, bestMoves);
        pipeText.text = "pipe: 0%";

        nextLvlButton.SetActive(!nextLvl);
        if (ended)
        {
            nextLvlButton.SetActive(false);
            finishNextButton.SetActive(false);
        }
        if (firstLevel)
            previusLvl.SetActive(false);
    }

    public void changeNFlow(int flow, int maxFlows)
    {
        flowsText.text = "flows: " + flow + "/" + maxFlows;
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
        if(hintsText != null)
            hintsText.text = hint + "x";
    }

    public void activeFinishPane()
    {
        panel.SetActive(true);
    }
}
