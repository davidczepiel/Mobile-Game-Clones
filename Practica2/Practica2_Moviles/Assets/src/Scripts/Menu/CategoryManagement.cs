using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CategoryManagement : MonoBehaviour
{

    [SerializeField]
    GameObject verticalGameObject;
    [SerializeField]
    GameObject categoryGameObject;
    [SerializeField]
    TitleBehaviour titlePrefab;
    [SerializeField]
    ButtonPackageSelectionbehaviour buttonPrefab;

    [SerializeField]
    CategoryData[] categoriesData;

    void Start()
    {
        foreach (CategoryData c in categoriesData)
        {
            GameObject category = Instantiate(categoryGameObject, new Vector3(0, 0, 0), Quaternion.identity, verticalGameObject.transform);
            TitleBehaviour t = Instantiate(titlePrefab, new Vector3(0,0,0), Quaternion.identity, category.transform);
            t.initData(c.categoryName, c.categoryColor);

            foreach (LevelsInfo l in c.packagesData)
            {
                ButtonPackageSelectionbehaviour b = Instantiate(buttonPrefab, new Vector3(0, 0, 0), Quaternion.identity, category.transform);
                b.initData(l);
            }
        }

    }

}
