using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class MulticoloredText : MonoBehaviour
{
    [SerializeField]
    Text text;

    [SerializeField]
    string[] colors;

    [SerializeField]
    string t;

    void Start()
    {
        text.text = "";
        for(int i = 0; i < t.Length; i++)
        {
            text.text += "<color=" + colors[i%colors.Length] + ">"+t[i]+"</color>";
        }
    }


}
