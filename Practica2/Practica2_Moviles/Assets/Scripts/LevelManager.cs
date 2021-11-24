using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace Flow
{
    public class LevelManager : MonoBehaviour
    {
        // Start is called before the first frame update
        void Start()
        {
            GameManager.getInstance().createMap();
        }

        // Update is called once per frame
        void Update()
        {

        }
    }

}