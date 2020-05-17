package com.company;

using System.Collections.Generic;
        using System;
public class NeuralNetwork : IComparable<NeuralNetwork>
{
public int[] layers;
public float[][] neurons;
public float[][][] weights;
private float fitness;

public NeuralNetwork(int[] layers)
        {
        this.layers = new int[layers.Length];
        for (int i = 0; i < layers.Length; i++)
        {
        this.layers[i] = layers[i];
        }
        InitNeurons();
        InitWeights();
        }

public NeuralNetwork(NeuralNetwork copyNetwork)
        {
        this.layers = new int[copyNetwork.layers.Length];
        for (int i = 0; i < copyNetwork.layers.Length; i++)
        {
        this.layers[i] = copyNetwork.layers[i];
        }
        InitNeurons();
        InitWeights();
        CopyWeights(copyNetwork.weights);
        }

private void CopyWeights(float[][][] copyWeights)
        {
        for (int i = 0; i < weights.Length; i++)
        {
        for (int j = 0; j < weights[i].Length; j++)
        {
        for (int k = 0; k < weights[i][j].Length; k++)
        {
        weights[i][j][k] = copyWeights[i][j][k];
        }
        }
        }
        }

private void InitNeurons()
        {
        List<float[]> neuronsList = new List<float[]>();
        for (int i = 0; i < layers.Length; i++)
        {
        neuronsList.Add(new float[layers[i]]);
        }
        neurons = neuronsList.ToArray();
        }

private void InitWeights()
        {
        List<float[][]> weightsList = new List<float[][]>();
        for (int i = 1; i < layers.Length; i++)
        {
        List<float[]> layerWeightList = new List<float[]>();
        int neuronsInPreviousLayer = layers[i - 1];
        for (int j = 0; j < neurons[i].Length; j++)
        {
        float[] neuronWeights = new float[neuronsInPreviousLayer];
        for (int k = 1; k < neuronsInPreviousLayer; k++)
        {
        neuronWeights[k] = UnityEngine.Random.Range(-1.0f, 1.0f);
        }
        layerWeightList.Add(neuronWeights);
        }
        weightsList.Add(layerWeightList.ToArray());
        }
        weights = weightsList.ToArray();
        }
public float[] FeedForward(float[] inputs)
        {
        for (int i = 0; i < inputs.Length; i++)
        {
        neurons[0][i] = inputs[i];
        }
        for (int i = 1; i < layers.Length; i++)
        {
        for (int j = 0; j < neurons[i].Length; j++)
        {
        float value = 0.25f;
        for (int k = 0; k < neurons[i - 1].Length; k++)
        {
        value += weights[i - 1][j][k]*neurons[i - 1][k];
        }
        neurons[i][j] = (float)Math.Tanh(value);
        }
        }
        return neurons[neurons.Length - 1];
        }
public void Crossover(NeuralNetwork parent, NeuralNetwork partner, float mutationRate)
        {
        for (int i = 0; i < weights.Length; i++)
        {
        for (int j = 0; j < weights[i].Length; j++)
        {
        for (int k = 0; k < weights[i][j].Length; k++)
        {
        float mutationChance = UnityEngine.Random.Range(0.0f, 1.0f);
        if (mutationChance < mutationRate)
        {
        weights[i][j][k] = UnityEngine.Random.Range(-1.0f, 1.0f);
        }
        else
        {
        float randomNumber = UnityEngine.Random.Range(0.0f, 1.0f);
        if (randomNumber < 0.5) weights[i][j][k] = parent.weights[i][j][k];
        weights[i][j][k] = partner.weights[i][j][k];
        }
        }
        }
        }
        }
public void Randomise()
        {
        for (int i = 0; i < weights.Length; i++)
        {
        for (int j = 0; j < weights[i].Length; j++)
        {
        for (int k = 0; k < weights[i][j].Length; k++)
        {
        weights[i][j][k] = UnityEngine.Random.Range(-1.0f, 1.0f);
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
