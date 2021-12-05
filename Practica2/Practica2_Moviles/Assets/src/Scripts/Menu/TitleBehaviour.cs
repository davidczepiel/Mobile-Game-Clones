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
    public void initData(string textA, Color color)
    {
        text.text = textA;
        background.color = color;
    }
}
