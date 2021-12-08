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

    int myGroup;



    public void initData(string text, int level, int levelGroup)
    {
        mylevel = level;
        buttonText.text = text;
        myGroup = levelGroup;
    }

    public void selectLevel()
    {
        print("Yo represento el nivel Nivel" + mylevel.ToString() + " con grupo " + myGroup.ToString());
        //Llamada correspondiente a quien tenga que mandarnos a un nivel concreto
    }
}
