using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ButtonPackageSelectionbehaviour : MonoBehaviour
{
    [SerializeField]
    Text packageTitle;
    [SerializeField]
    Text numLevelsCompleted;

    LevelsInfo package;


    public void initData(LevelsInfo p)
    {
        packageTitle.text = p.packName;
        numLevelsCompleted.text = p.levelsCompleted.ToString();
    }

    public static void LoadLevelPackage()
    {
        //GameManager...(package)
    }
}

