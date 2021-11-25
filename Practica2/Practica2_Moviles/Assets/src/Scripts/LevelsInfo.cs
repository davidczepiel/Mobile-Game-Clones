using UnityEngine;

[CreateAssetMenu(fileName = "LevelsInfo", menuName = "Flow/LevelsInfo", order = 1)]
public class LevelsInfo : ScriptableObject
{
    public string packName;
    public TextAsset maps;
    public LevelSkin skin;
}