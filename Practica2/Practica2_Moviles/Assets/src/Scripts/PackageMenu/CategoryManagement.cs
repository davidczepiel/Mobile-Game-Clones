using UnityEngine;


namespace Flow
{
    /// <summary>
    /// Clase que se encarga de colocar los botones en el menu de selecion de paquete
    /// </summary>
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

            //Para cada categoria añadimos los paquetes de esta como botones en la UI
            for (int i  = 0; i< categoriesData.Length; ++i)
            {
                //Tomo la categoria y actualizo el titulo y colo de la UI que la representa
                CategoryData c = categoriesData[i];
                GameObject category = Instantiate(categoryGameObject, new Vector3(0, 0, 0), Quaternion.identity, verticalGameObject.transform);
                TitleBehaviour t = Instantiate(titlePrefab, new Vector3(0, 0, 0), Quaternion.identity, category.transform);
                t.initData(c.categoryName, new Color(1, 1, 1, 1), c.categoryColor);
                extraHeight += t.GetComponent<RectTransform>().sizeDelta.y;
                //Para cada paquete de la categoria creo un boton
                for (int j = 0; j < c.packagesData.Length; ++j)
                {
                    LevelsInfo l = c.packagesData[j];
                    ButtonPackageSelectionbehaviour b = Instantiate(buttonPrefab, new Vector3(0, 0, 0), Quaternion.identity, category.transform);
                    b.initData(l, j, i, c.categoryColor);
                    extraHeight += b.GetComponent<RectTransform>().sizeDelta.y;
                }
            }
            //Le digo al canvas que se ajuste al tamaño de los elementos que le he añadido
            scrollableCanvas.SetSizeWithCurrentAnchors(RectTransform.Axis.Vertical, scrollableCanvas.sizeDelta.y + extraHeight);
        }

    }

}
