using System;
using System.IO;
using System.Collections.Generic;
using UnityEngine;

namespace Flow
{
    public static class ProgressSerialization
    {
        private static string fileName = "/playerProgress.txt";

        /// <summary>
        /// Guarda los datos del progreso de la partida
        /// </summary>
        /// <param name="playerProgress"></param>
        public static void saveProgress(PlayerProgress playerProgress)
        {
            string filePath = Application.persistentDataPath + fileName;

            //Vaciamos cualquier contenido del campo hash para que no interfiera mas adelante
            playerProgress.hash = " ";
            //Convertimos el objeto en Json
            string json = JsonUtility.ToJson(playerProgress);

            //Creamos el hash asociado al Json de los datos del progreso
            string hashvalue = Hash128.Compute(json).ToString();

            //Lo asignamos al struct para guardarlo
            playerProgress.hash = hashvalue;

            //Convertimos el nuevo objeto con el hash a json y guardamos
            json = JsonUtility.ToJson(playerProgress);
            File.WriteAllText(filePath, json);
        }
        
        /// <summary>
        /// Devuelve un struct con los valores del ultimo progreso guardado
        /// </summary>
        /// <returns></returns>
        public static PlayerProgress loadProgress()
        {
            string filePath = Application.persistentDataPath + fileName;

            //Si no existe el archivo devolvemos valores por defecto
            if (!File.Exists(filePath)) return null;

            //Leemos el archivo json
            string json = File.ReadAllText(filePath);
            PlayerProgress progressRead = JsonUtility.FromJson<PlayerProgress>(json);

            //Nos guardamos el hash del archivo leido
            string readHash = progressRead.hash;
            progressRead.hash = " ";

            //Comprobamos la integridad del json leido
            json = JsonUtility.ToJson(progressRead);
            string currentHash = Hash128.Compute(json).ToString();

            //Si no resulta ser el mismo hash devolvemos valores por defecto
            if (readHash != currentHash) return null;

            return progressRead;
        }
    }   

    [Serializable]
    public class PlayerProgress
    {
        public int availableHints;
        public List<CategoryProgress> categoryProgress;

        public string hash;

        /// <summary>
        /// Constructora por defecto
        /// </summary>
        public PlayerProgress()
        {
            categoryProgress = new List<CategoryProgress>();
        }
    }

    [Serializable]
    public class CategoryProgress
    {
        public List<PackProgress> packProgress;

        /// <summary>
        /// Constructora por defecto
        /// </summary>
        public CategoryProgress()
        {
            packProgress = new List<PackProgress>();
        }
    }

    [Serializable]
    public class PackProgress
    {
        public int completedLevels;
        public List<int> levelTopScore;

        /// <summary>
        /// Constructora para inicializar desde 0 la clase
        /// </summary>
        /// <param name="numLevels"></param>
        public PackProgress(int numLevels)
        {
            completedLevels = 0;
            levelTopScore = new List<int>();

            for (int i = 0; i < numLevels; ++i)
                levelTopScore.Add(0);
        }
    }
}
