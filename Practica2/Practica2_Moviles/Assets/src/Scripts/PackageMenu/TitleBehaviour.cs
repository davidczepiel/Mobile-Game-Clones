using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class TitleBehaviour : MonoBehaviour
{
    [SerializeField]
    Text text;
    [SerializeField]
    Image background;
    public void initData(string textA,Color textColor, Color backgroundolor)
    {
        text.text = textA;
        text.color = textColor;
        background.color = backgroundolor;
    }
}
