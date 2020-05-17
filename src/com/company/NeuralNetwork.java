package com.company;

import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork
{
    public int[] layers;
    public ArrayList<float[]> neurons;
    public ArrayList<ArrayList<float[]>> weights;
    private float fitness;

    public static float randFloat(float min, float max) {

        Random rand = new Random();

        return rand.nextFloat() * (max - min) + min;

    }

    public NeuralNetwork(int[] layers)
    {
        this.layers = new int[layers.length];
        for (int i = 0; i < layers.length; i++)
        {
            this.layers[i] = layers[i];
        }
        InitNeurons();
        InitWeights();
    }

    public NeuralNetwork(NeuralNetwork copyNetwork)
    {
        this.layers = new int[copyNetwork.layers.length];

        for (int i = 0; i < copyNetwork.layers.length; i++)
        {
            this.layers[i] = copyNetwork.layers[i];
        }

        InitNeurons();
        InitWeights();
        CopyWeights(copyNetwork.weights);
    }

private void CopyWeights(ArrayList<ArrayList<float[]>> copyWeights)
{
    for (int i = 0; i < weights.size(); i++)
    {
        for (int j = 0; j < weights.get(i).size(); j++)
        {
            for (int k = 0; k < weights.get(i).get(j).length; k++)
            {
                weights.get(i).get(j)[k] = copyWeights.get(i).get(j)[k];
            }
        }
    }
}

private void InitNeurons()
{
    ArrayList<float[]> neuronsList = new ArrayList<>();

    for (int i = 0; i < layers.length; i++)
    {
        neuronsList.add(new float[layers[i]]);
    }
    neurons = neuronsList;
}

private void InitWeights()
{
    ArrayList<ArrayList<float[]>> weightsList = new ArrayList<>();

    for (int i = 1; i < layers.length; i++)
    {
        ArrayList<float[]> layerWeightList = new ArrayList<>();
        int neuronsInPreviousLayer = layers[i - 1];

        for (int j = 0; j < neurons.get(i).length; j++)
        {
            float[] neuronWeights = new float[neuronsInPreviousLayer];
            for (int k = 1; k < neuronsInPreviousLayer; k++)
            {
                neuronWeights[k] = randFloat(-1.0f, 1.0f);
            }
            layerWeightList.add(neuronWeights);
        }
        weightsList.add(layerWeightList);
    }
    weights = weightsList;
}

public float[] FeedForward(float[] inputs)
{
    for (int i = 0; i < inputs.length; i++)
    {
        neurons.get(0)[i] = inputs[i];
    }
    for (int i = 1; i < layers.length; i++)
    {
        for (int j = 0; j < neurons.get(i).length; j++)
        {
            float value = 0.25f;
            for (int k = 0; k < neurons.get(i-1).length; k++)
            {
                value += weights.get(i-1).get(j)[k]*neurons.get(i-1)[k];
            }
            neurons.get(i)[j] = (float)Math.tanh(value);
        }
    }
    return neurons.get(neurons.size() - 1);
}

public void Crossover(NeuralNetwork parent, NeuralNetwork partner, float mutationRate)
{
    for (int i = 0; i < weights.size(); i++)
    {
        for (int j = 0; j < weights.get(i).size(); j++)
        {
            for (int k = 0; k < weights.get(i).get(j).length; k++)
            {
                float mutationChance = randFloat(0.0f, 1.0f);
                if (mutationChance < mutationRate)
                {
                    weights.get(i).get(j)[k] = randFloat(-1.0f, 1.0f);
                }
                else
                {
                    float randomNumber = randFloat(0.0f, 1.0f);
                    if (randomNumber < 0.5)
                        weights.get(i).get(j)[k] = parent.weights.get(i).get(j)[k];

                    weights.get(i).get(j)[k] = partner.weights.get(i).get(j)[k];
                }
            }
        }
    }
}

public void Randomise()
{
    for (int i = 0; i < weights.size(); i++)
    {
        for (int j = 0; j < weights.get(i).size(); j++)
        {
            for (int k = 0; k < weights.get(i).get(j).length; k++)
            {
                weights.get(i).get(j)[k] = randFloat(-1.0f, 1.0f);
            }
        }
    }
}

public void SetFitness(float fit)
{
    fitness = fit;
}

public float GetFitness()
{
    return fitness;
}

public int CompareTo(NeuralNetwork other)
{
    if (other == null) return 1;
    if (fitness > other.fitness) return 1;
    else if (fitness < other.fitness) return -1;
    else return 0;
}

}
