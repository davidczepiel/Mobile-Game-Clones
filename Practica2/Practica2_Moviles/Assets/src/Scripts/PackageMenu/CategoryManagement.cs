using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace Flow
{

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

        void Start()
        {
            CategoryData[] categoriesData = GameManager.getInstance().getCategoryData();
            float extraHeight = 0f;
            for (int i  = 0; i< categoriesData.Length; ++i)
            {
                CategoryData c = categoriesData[i];
                GameObject category = Instantiate(categoryGameObject, new Vector3(0, 0, 0), Quaternion.identity, verticalGameObject.transform);
                TitleBehaviour t = Instantiate(titlePrefab, new Vector3(0, 0, 0), Quaternion.identity, category.transform);
                t.initData(c.categoryName, new Color(1, 1, 1, 1), c.categoryColor);
                extraHeight += t.GetComponent<RectTransform>().sizeDelta.y;

                for (int j = 0; j < c.packagesData.Length; ++j)
                {
                    LevelsInfo l = c.packagesData[j];
                    ButtonPackageSelectionbehaviour b = Instantiate(buttonPrefab, new Vector3(0, 0, 0), Quaternion.identity, category.transform);
                    b.initData(l, j, i, c.categoryColor);
                    extraHeight += b.GetComponent<RectTransform>().sizeDelta.y;
                }
            }

            scrollableCanvas.SetSizeWithCurrentAnchors(RectTransform.Axis.Vertical, scrollableCanvas.sizeDelta.y + extraHeight);
        }

    }

}
