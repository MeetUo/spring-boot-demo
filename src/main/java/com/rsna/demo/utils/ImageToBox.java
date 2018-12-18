package com.rsna.demo.utils;

import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageToBox {
    private static Graph graph;
    private static Session sess;
    public ImageToBox(String filepath) throws Exception {
        try{
            graph = new Graph();
            graph.importGraphDef(Files.readAllBytes(Paths.get(
                    filepath
            )));
            sess = new Session(graph);
        }catch (Exception e){
            throw e;
        }
    }
    public float[][][] predict(float[][][][] input) throws Exception {
         {
            try (Tensor x = Tensor.create(input);
                 // input是输入的name，output是输出的name
                 Tensor y = sess.runner().feed("input_1", x)
                         .fetch("output")
                         .run().get(0)) {
                int size1 = (int)y.shape()[1];
                int size2 = (int)y.shape()[2];
                float[][][] result = new float[1][size1][size2];
                y.copyTo(result);
//                System.out.println(Arrays.toString(y.shape()));
                return result;
            }
        }
    }
    public static void main(String arg[]){
        try{
            ImageToBox a = new ImageToBox("");
            float [][][][] input=new float[1][300][300][3];
            float[][][]res = a.predict(input);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}