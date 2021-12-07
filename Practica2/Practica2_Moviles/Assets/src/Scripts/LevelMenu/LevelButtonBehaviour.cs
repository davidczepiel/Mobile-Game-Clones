using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class LevelButtonBehaviour : MonoBehaviour
{

    //Texto que va a variar y representara el nivel al que lleva este boton
    [SerializeField]
    Text buttonText;

    //Nivel que representa este boton
    int mylevel;


    public void initData(string text, int level)
    {
        mylevel = level;
        buttonText.text = text;
    }

    public void selectLevel()
    {
        print("Yo represento el nivel ");
    }
}
