package wordembeddingsevaluator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.shape.Line;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ME16683
 */
public class MolkovFormatWordEmbeddingsAccuracy {

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 8) {
            System.out.println("less than 4 arguments");
            System.exit(0);
        }
        String embeddingsDir = "";
        String benchmarkDir = "";
        String outputdir = "~/home";
        int NoOfCorrectAnser = 1;

        for (int i = 0; i < args.length; ) {
            if (args[i].startsWith("-b")) {
                benchmarkDir = args[i + 1];
            } else if (args[i].startsWith("-e")) {
                embeddingsDir = args[i + 1];

            } else if (args[i].startsWith("-o")) {
                outputdir = args[i + 1];

            } else if (args[i].startsWith("-n")) {
                NoOfCorrectAnser = Integer.parseInt(args[i + 1]);

            }
            else{
                System.out.println("wrong argument");
                System.exit(0);
            }
            i+=2;
        }
        PrintWriter ResPw = new PrintWriter(outputdir);
        int max_size = 2000;         // max length of strings
        int N = NoOfCorrectAnser;                   // number of closest words
        int max_w = 50;              // max length of vocabulary entries

        {
            File f;
            File f2;
//            String st1="";
//            String st2="";
//            String st3="";
//            String st4="";
            String[] bestw = new String[N];
//  String file_name=new char[max_size];
//  String file2_name=new char[max_size];
            char ch;
            float dist, len;
            Float[] bestd = new Float[N];
            Float[] vec = new Float[max_size];
            int words, size, a, b, c, d, b1, b2, b3, threshold = 0;
            float M;
            char vocab;
            int TCN, CCN = 0, TACN = 0, CACN = 0, SECN = 0, SYCN = 0, SEAC = 0, SYAC = 0, QID = 0, TQ = 0, TQS = 0;

            f = new File(embeddingsDir); //embeddings file
            f2 = new File(benchmarkDir); //tuplesFile
            Scanner sc = new Scanner(f, "utf8");
            Scanner sc2 = new Scanner(f2, "utf8");
            words = sc.nextInt();
            size = sc.nextInt();

            sc.nextLine();
            String[] vocabArray = new String[words];
            float[][] m = new float[words][size];

            for (b = 0; b < words; b++) {
                System.out.println(b);
                String next = sc.nextLine().trim();
                String splited[] = next.split("\\s+");
                if (splited.length != size + 1) {
                    System.out.println(" less than size");

                    continue;
                }
                vocabArray[b] = splited[0];

                for (a = 0; a < size; a++) {
                    m[b][a] = Float.valueOf(splited[a + 1]);
                }
                len = 0;
                for (a = 0; a < size; a++) {
                    len += m[b][a] * m[b][a];
                }
                len = (float) Math.sqrt(len);
                for (a = 0; a < size; a++) {
                    m[b][a] = m[b][a] / len;
                }
            }
            sc.close();
            TCN = 0;
            String rel = "";

            while (true) {
                for (a = 0; a < N; a++) {
                    bestd[a] = 0F;
                }
                for (a = 0; a < N; a++) {
                    bestw[a] = "0";
                }
//    fscanf(f2,"%s", st1);
                String line = "";
                if (sc2.hasNextLine()) {
                    line = sc2.nextLine();
                    if (line.startsWith(":")) {

                        if (TCN == 0) {
                            TCN = 1;
                        }
                        if (QID != 0) {
                            System.out.println("ACCURACY TOP1: " + rel + " " + CCN / (float) TCN * 100 + "% " + CCN + "/" + TCN);
                            System.out.println("Total accuracy: " + CACN / (float) TACN * 100 + SEAC / (float) SECN * 100 + "%");
                            ResPw.println("ACCURACY TOP1: " + rel + " " + CCN / (float) TCN * 100 + "% " + CCN + "/" + TCN);
                            ResPw.println("Total accuracy: " + CACN / (float) TACN * 100 + SEAC / (float) SECN * 100 + "%");
                        }
                        rel = line;
                        QID++;

//      if (feof(stdin)) break;
                        TCN = 0;
                        CCN = 0;
                        continue;
                    }
                } else {
                    System.out.println("ACCURACY TOP1: %" + rel + " " + CCN / (float) TCN * 100 + "% " + CCN + "/" + TCN);
                    System.out.println("Total accuracy: " + CACN / (float) TACN * 100 + SEAC / (float) SECN * 100 + "%");
                    ResPw.println("ACCURACY TOP1: " + rel + " " + CCN / (float) TCN * 100 + "% " + CCN + "/" + TCN);
                    ResPw.println("Total accuracy: " + CACN / (float) TACN * 100 + SEAC / (float) SECN * 100 + "%");
                    break;
                }
                String[] splited = line.split("\\s+");
                int flag = 0;
                int[] list = new int[splited.length];
                TQ++;

                for (int i = 0; i < splited.length; i++) {
                    for (b = 1; b < words; b++) {
                        if (vocabArray[b] == null) {
                            continue;
                        }
                        if (vocabArray[b].equals(splited[i])) {
                            list[i] = b;
                            break;
                        }
                        if (b == words) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 1) {
                        continue;
                    }

                }

                for (a = 0; a < N; a++) {
                    bestd[a] = 0f;
                }
                for (a = 0; a < N; a++) {
                    bestw[a] = null;
                }
                ArrayList<Float[]> listofVector = new ArrayList<>();

                for (int z = 0; z < list.length - 2;) {
                    for (a = 0; a < size; a++) {

                        vec[a] = m[list[z + 1]][a] - m[list[z]][a] + m[list[list.length - 2]][a];
                    }
                    listofVector.add(vec);
                    z += 2;
                }
                float[] taregetVec = new float[size];
                for (int aa = 0; aa < size; aa++) {
                    taregetVec[aa] = 0;
                    for (int xx = 0; xx < listofVector.size(); xx++) {
                        taregetVec[aa] = taregetVec[aa] + listofVector.get(xx)[aa];
                    }
                }
                for (int aa = 0; aa < size; aa++) {

                    taregetVec[aa] = taregetVec[aa] / ((list.length - 2) / 2);
//                    System.out.println(String.valueOf(taregetVec[aa]));
                }

                TQS++;
                for (c = 0; c < words; c++) {
//                    make sure that the target  word not in the tuples words
                    int flag2 = 0;
                    for (int zz = 0; zz < list.length; zz++) {
//                         1 is the index of target word
                        if (zz == list.length - 1) {
                            continue;
                        }
                        if (c == list[zz]) {
                            flag2 = 1;
                            break;
                        }

                    }
                    if (flag2 == 1) {
                        continue;
                    }
                    dist = 0;
                    for (a = 0; a < size; a++) {
                        dist += taregetVec[a] * m[c][a];
                    }

                    for (a = 0; a < N; a++) {
                        if (dist > bestd[a]) {
                            for (d = N - 1; d > a; d--) {
                                bestd[d] = bestd[d - 1];
                                bestw[d] = bestw[d - 1];
                            }
                            bestd[a] = dist;
                            bestw[a] = vocabArray[c];
                            break;
                        }
                    }

                }
                int flag3 = 0;
                for (int x = 0; x < N; x++) {
                    if (splited[list.length - 1].equals(bestw[x])) {
                        flag3 = 1;
                    }
                }
                if (flag3 == 1) {
                    CCN++;
                    CACN++;
                    if (QID <= 5) {
                        SEAC++;
                    } else {
                        SYAC++;
                    }
                }
                if (QID <= 5) {
                    SECN++;
                } else {
                    SYCN++;
                }
                TCN++;
                TACN++;
            }
            System.out.println("Questions seen / total: " + TQS + " " + TQ + " " + TQS / (float) TQ * 100 + "%");
            ResPw.println("Questions seen / total: " + TQS + " " + TQ + " " + TQS / (float) TQ * 100 + "%");
        }

    }

}
