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
    RectTransform scrollableCanvas;

    [SerializeField]
    CategoryData[] categoriesData;

    void Start()
    {
        float extraHeight = 0f;
        foreach (CategoryData c in categoriesData)
        {
            GameObject category = Instantiate(categoryGameObject, new Vector3(0, 0, 0), Quaternion.identity, verticalGameObject.transform);
            TitleBehaviour t = Instantiate(titlePrefab, new Vector3(0,0,0), Quaternion.identity, category.transform);
            t.initData(c.categoryName, c.categoryColor);
            extraHeight += t.GetComponent<RectTransform>().sizeDelta.y;

            foreach (LevelsInfo l in c.packagesData)
            {
                ButtonPackageSelectionbehaviour b = Instantiate(buttonPrefab, new Vector3(0, 0, 0), Quaternion.identity, category.transform);
                b.initData(l);
                extraHeight += b.GetComponent<RectTransform>().sizeDelta.y;
            }
        }

        scrollableCanvas.SetSizeWithCurrentAnchors(RectTransform.Axis.Vertical, scrollableCanvas.sizeDelta.y + extraHeight);
    }

}
