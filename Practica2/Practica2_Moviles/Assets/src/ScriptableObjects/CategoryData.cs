using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[CreateAssetMenu(fileName = "CategoryData", menuName = "Flow/CategoryData", order = 1)]
public class CategoryData : ScriptableObject
{
    public string categoryName;
    public Color categoryColor;
    public LevelsInfo[] packagesData;

    public bool isLocked;
}
