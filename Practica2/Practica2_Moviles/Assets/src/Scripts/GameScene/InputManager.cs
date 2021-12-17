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

        float tileUnitSize, cameraUnit;
        public void setData(Vector2 initUnitPos,float tileUnitSize, float cameraUnit)
        {
            this.initUnitPos = initUnitPos;
            this.tileUnitSize = tileUnitSize;
            this.cameraUnit = cameraUnit;
        }

        void Update()
        {
            foreach(Touch touch in Input.touches) //Todo get touches
            {
                Vector2 posToUnit = transformToUnit(touch.position);
                Vector2 tranformatedPos = transformCoord(touch.position);

                //Si la pulsacion no entra dentro del tablero no la procesamos
                if (tranformatedPos.x == -1) return;

                boardManager.processTouch(touch, tranformatedPos, posToUnit);
            }

#if UNITY_EDITOR    //Procesamiento del raton en el editor de Unity
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
                flag = true;
                touch1.phase = TouchPhase.Moved;
            }

            if (flag)
            {
                touch1.position = Input.mousePosition;

                Vector2 posToUnit = transformToUnit(touch1.position);
                Vector2 boardPosition = transformCoord(touch1.position);
                
                //Si la pulsacion no entra dentro del tablero no la procesamos
                if (boardPosition.x == -1) return;

                boardManager.processTouch(touch1, boardPosition, posToUnit);
            }
#endif
        }

        /// <summary>
        /// Transforma coordenadas de pantalla a coordenada en el tablero de tiles
        /// </summary>
        /// <param name="pos"></param>
        /// <returns></returns>
        private Vector2 transformCoord(Vector2 pos)
        {
            //Pasamos de pixeles a unidades
            pos.x = (pos.x * cameraUnit * 2) / Screen.height;  
            pos.y =  (pos.y * cameraUnit * 2) / Screen.height;

            float boardUnitsSize = (initUnitPos.y - cameraUnit) * 2;

            //Si la posicion se encuentra en el tablero de juego
            if ((pos.y <= initUnitPos.y && pos.y >= cameraUnit * 2 - initUnitPos.y) &&
                (pos.x >= initUnitPos.x && pos.x <= boardUnitsSize))
            {
                //Pasamos de unidades de Unity a unidades en el tablero
                pos = initUnitPos - pos;
                pos = pos / tileUnitSize;

                Vector2Int posInt = new Vector2Int((int)pos.x, (int)pos.y);

                return new Vector2(Mathf.Abs(posInt.x), Mathf.Abs(posInt.y));
            }
            else return new Vector2(-1, -1);
        }

        private Vector2 transformToUnit(Vector2 pos)
        {
            Vector2 newPos = new Vector2((pos.x * cameraUnit * 2.0f) / (float)Screen.height,
                                         (pos.y * cameraUnit * 2.0f) / (float)Screen.height);
            return newPos;
        }
    }
}
