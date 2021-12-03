using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace Flow
{
    public class InputManager : MonoBehaviour
    {
        [SerializeField]
        private GameObject _debugCircle;

        [SerializeField]
        private BoardManager boardManager;

        Vector2 initUnitPos;

        float tileUnitSize, cameraUnit;
        public void setData(Vector2 initUnitPos,float tileUnitSize, float cameraUnit)
        {
            this.initUnitPos = initUnitPos;
            this.tileUnitSize = tileUnitSize;
            this.cameraUnit = cameraUnit;
        }

        // Update is called once per frame
        void Update()
        {
            foreach(Touch touch in Input.touches) //Todo get touches
            {
                Vector2 tranformatedPos = transformCoord(touch.position);
                  
                boardManager.processTouch(touch, tranformatedPos);
            }
#if UNITY_EDITOR
            Touch touch1 = new Touch();
            bool flag = false;
            if (Input.GetMouseButtonDown(0))
            {
                flag = true;
                touch1.phase = TouchPhase.Began;
            }
            else if (Input.GetMouseButtonUp(0))
            {
                flag = true;
                touch1.phase = TouchPhase.Ended;
            }
            else if (Input.GetMouseButton(0))
            {
                //flag = true;
                touch1.phase = TouchPhase.Moved;
            }
            if (flag)
            {
                touch1.position = Input.mousePosition;
                boardManager.processTouch(touch1, transformCoord(touch1.position));
            }


#endif
        }

        private Vector2 transformCoord(Vector2 pos)
        {
            //Pasamos de pixeles de unidades
            pos.x = (pos.x * cameraUnit * 2) / Screen.height;  
            pos.y =  (pos.y * cameraUnit * 2) / Screen.height;

            if (pos.y <= initUnitPos.y && pos.y >= cameraUnit * 2 - initUnitPos.y)
            {
                //Pasamos de unidades de Unity a unidades en el tablero
                pos = initUnitPos - pos;
                pos = pos / tileUnitSize;

                Vector2Int posInt = new Vector2Int((int)pos.x, (int)pos.y);

                return new Vector2(Mathf.Abs(posInt.x), Mathf.Abs(posInt.y));
            }
            else return new Vector2(-1, -1);
        }
    }
}
