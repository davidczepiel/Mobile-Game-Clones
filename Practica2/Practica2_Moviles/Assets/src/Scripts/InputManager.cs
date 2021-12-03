using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace Flow
{
    public class InputManager : MonoBehaviour
    {
        [SerializeField]
        private BoardManager boardManager;

        Vector2 initUnitPos;

        int boardSizeX, boardSizeY;

        float tileUnitSize, cameraUnit;
        public void setData(Vector2 initUnitPos,int boardSizeX, int boardSizeY, float tileUnitSize, float cameraUnit)
        {
            this.initUnitPos = initUnitPos;
            this.boardSizeX = boardSizeX;
            this.boardSizeY = boardSizeY;
            this.tileUnitSize = tileUnitSize;
            this.cameraUnit = cameraUnit;
        }

        // Update is called once per frame
        void Update()
        {
            foreach(Touch touch in Input.touches)
            {
                Vector2 tranformatedPos = transformCoord(touch.position);
                   //si se encuentra dentro del tablero
                    boardManager.processTouch(touch, tranformatedPos);
            }
        }

        private Vector2 transformCoord(Vector2 pos)
        {
            //Pasamos de pixeles de unidades
            pos.x = (pos.x * cameraUnit * 2) / Screen.height;  
            pos.y = (pos.y * cameraUnit * 2) / Screen.height;

            if (pos.y > initUnitPos.y && pos.y < cameraUnit * 2 - initUnitPos.y)
            {
                //Pasamos de unidades de Unity a unidades en el tablero
                pos = pos - initUnitPos;
                return new Vector2(Mathf.Floor(pos.x), Mathf.Floor(pos.y));
            }
            else return new Vector2(-1, -1);
        }
    }
}
