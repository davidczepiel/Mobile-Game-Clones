using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace Flow
{
    public class InputManager : MonoBehaviour
    {
        [SerializeField]
        private BoardManager boardManager;

        // Update is called once per frame
        void Update()
        {
            foreach(Touch touch in Input.touches)
            {
                boardManager.processTouch(touch);
            }
        }
    }
}
