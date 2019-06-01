import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;
import java.lang.Math;

public class NeuralNet {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Invalid Number of Input Arguments");
            return;
        }

        int flag = Integer.valueOf(args[0]);
        int option = flag / 100;

        if (option == 1) {
            double[] rst = fowardPro(args, false, new double[5]);
            System.out.println(String.format("%.5f", rst[0]) + " " + String.format("%.5f", rst[1]));
            System.out.println(String.format("%.5f", rst[2]));
        } else if (option == 2) {
            double[] rst = fowardPro(args, false, new double[5]);
            double y = Double.parseDouble(args[18]);
            double sigma = (rst[2] - y) * rst[2] * (1 - rst[2]);
            System.out.println(String.format("%.5f", sigma));
        } else if (option == 3) {
            double[] rst = fowardPro(args, false, new double[5]);
            double y = Double.parseDouble(args[18]);
            double sigma = (rst[2] - y) * rst[2] * (1 - rst[2]), w1 = Double.parseDouble(args[12]), w2 = Double.parseDouble(args[13]);
            double sigma1 = sigma * w1 * rst[0] * (1 - rst[0]);
            double sigma2 = sigma * w2 * rst[1] * (1 - rst[1]);
            System.out.print(String.format("%.5f", sigma1) + ' ');
            System.out.println(String.format("%.5f", sigma2));
        } else if (option == 4) {
            gradientCalc(true, args, false, new double[5]);
        } else if (option == 5 || option==6) {
            List < List < Double >> train = new LinkedList < > ();
            List < List < Double >> eval = new LinkedList < > ();
            List<List<Double>> test=new LinkedList<>();
            readCSV(train, "train.csv");
            if(option==5){
                readCSV(eval, "eval.csv");
            }
            else{
                readCSV(test,"test.csv");
            }
            double[] w1 = new double[5], w2 = new double[5], w3 = new double[3];
            for (int i = 1; i < 14; ++i) {
                double curr = Double.parseDouble(args[i]);
                if (i < 6) {
                    w1[i - 1] = curr;
                } else if (i < 11) {
                    w2[i - 6] = curr;
                } else if (i < 14) {
                    w3[i - 11] = curr;
                }
            } //initial all weights
            double multi = Double.parseDouble(args[14]);
            //int round = 0;
            for (List < Double > lst: train) {
                double[] init = new double[5];
                init[0] = 1;
                for (int i = 0; i < 4; ++i) {
                    init[i + 1] = lst.get(i);
                }
                //++round;
                adjustWeight(w1, w2, w3, init, lst.get(4), multi,option==5);
                if(option==5){
                    evaluate(w1, w2, w3, eval,false);
                }
            }
            if(option==6){
                //Now the model is trained
                evaluate(w1,w2,w3,test,true); 
            }
        }
    }
    private static double sig(double z) {
        return 1 / (1 + Math.exp(-1 * z));
    }
    private static double[] fowardPro(String[] args, boolean inited, double[] init) {
        double[] w1 = new double[5], w2 = new double[5], w3 = new double[3];
        init[0] = 1; //first round a is 1

        //get all args ready
        for (int i = 1; i < 18; ++i) {
            double curr = Double.parseDouble(args[i]);
            if (i < 6) {
                w1[i - 1] = curr;
            } else if (i < 11) {
                w2[i - 6] = curr;
            } else if (i < 14) {
                w3[i - 11] = curr;
            } else if (!inited) {
                init[i - 13] = curr;
            }
        }
        double z1 = 0, z2 = 0, z3 = 0;
        for (int i = 0; i < 5; ++i) {
            //five weights
            z1 += (w1[i] * init[i]);
            z2 += (w2[i] * init[i]);
        }
        double[] ret = new double[3];
        //second round
        ret[0] = sig(z1);
        ret[1] = sig(z2);
        z3 += (1 * w3[0] + ret[0] * w3[1] + ret[1] * w3[2]);
        //third round
        ret[2] = sig(z3);
        return ret;
    }
    private static void readCSV(List < List < Double >> train, String path) {
        try (Scanner scanner = new Scanner(new File("./" + path));) {
            int count = 0;
            while (scanner.hasNextLine()) {
                List < Double > temp = new ArrayList < > ();
                String line = scanner.nextLine();
                String[] Aline = line.split(",");
                for (int i = 0; i < 5; ++i) {
                    temp.add(Double.parseDouble(Aline[i]));
                }
                train.add(temp);
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }

    //for option 5
    private static void adjustWeight(double[] w1, double[] w2, double[] w3, double[] init, double y, double param,
    boolean printing) {
        double z1 = 0, z2 = 0, z3 = 0;
        for (int i = 0; i < 5; ++i) {
            //five weights
            z1 += (w1[i] * init[i]);
            z2 += (w2[i] * init[i]);
        }
        double[] ret = new double[3];
        //second round
        ret[0] = sig(z1);
        ret[1] = sig(z2);
        z3 += (1 * w3[0] + ret[0] * w3[1] + ret[1] * w3[2]);
        //third round
        ret[2] = sig(z3);
        double sigma = (ret[2] - y) * ret[2] * (1 - ret[2]);
        double sigma1 = sigma * w3[1] * ret[0] * (1 - ret[0]);
        double sigma2 = sigma * w3[2] * ret[1] * (1 - ret[1]);
        double[] params = new double[13];
        //Print First three
        params[0] = sigma;

        for (int i = 0; i < 2; ++i) {
            params[i + 1] = sigma * ret[i];
        }
        //Next two lines
        params[3] = sigma1;
        params[8] = sigma2; //0 item
        for (int i = 0; i < 4; ++i) {
            double curr = init[i + 1];
            params[4 + i] = sigma1 * curr;
            params[9 + i] = sigma2 * curr;
        }
        //update weights
        String l1 = "", l2 = "", l3 = "";
        for (int i = 0; i < params.length; ++i) {
            double temp = param * params[i];
            if (i < 3) {
                //w3
                w3[i] -= temp;
                l3 += String.format("%.5f", w3[i]) + ' ';
            } else if (i < 8) {
                //w1
                w1[i - 3] -= temp;
                l1 += String.format("%.5f", w1[i - 3]) + ' ';
            } else {
                //w2
                w2[i - 8] -= temp;
                l2 += String.format("%.5f", w2[i - 8]) + ' ';
            }
        }
        if(printing){
            System.out.println(l1 + l2 + l3.substring(0, l3.length() - 1));
        }
    }
    private static void evaluate(double[] w1, double[] w2, double[] w3, List < List < Double >> eval, boolean predicting) {
        double result = 0,ratio=0;
        for (List < Double > init: eval) {
            double z1 = w1[0], z2 = w2[0], z3 = 0;

            for (int i = 0; i < 4; ++i) {
                //five weights
                z1 += (w1[i+1] * init.get(i));
                z2 += (w2[i+1] * init.get(i));
            }
            double[] ret = new double[2];
            //second round
            ret[0] = sig(z1);
            ret[1] = sig(z2);
            z3 += (1 * w3[0] + ret[0] * w3[1] + ret[1] * w3[2]);
            //third round
            double a3 = sig(z3);
            if(predicting){
                int act=(int)Math.round(init.get(4)),pre=(a3>0.5)?1:0;
                if(act==pre){
                    ++ratio;
                }
                System.out.println(act+" "+pre+" "+String.format("%.5f",a3));
            }
            else{
                result += (0.5 * Math.pow(a3 - init.get(4), 2));
            }
        }
       if(!predicting){ 
        System.out.println(String.format("%.5f",result));
       }
       else{
           System.out.println(String.format("%.2f",ratio/eval.size()));
       }
    }
    private static void gradientCalc(boolean printing, String[] args, boolean inited, double[] init) {
        double[] rst = fowardPro(args, inited, init);
        double y = Double.parseDouble(args[18]);
        double sigma = (rst[2] - y) * rst[2] * (1 - rst[2]), w1 = Double.parseDouble(args[12]), w2 = Double.parseDouble(args[13]);
        double sigma1 = sigma * w1 * rst[0] * (1 - rst[0]);
        double sigma2 = sigma * w2 * rst[1] * (1 - rst[1]);
        //Print First three
        if (printing) {
            System.out.print(String.format("%.5f", sigma) + ' ');
        }
        for (int i = 0; i < 2; ++i) {
            if (printing && i==0) {
                System.out.print(String.format("%.5f", sigma * rst[i]) + ' ');
            }
            else if(printing){
                System.out.print(String.format("%.5f", sigma * rst[i]));
            }
        }
        if (printing) {
            System.out.println();
        }
        //Next two lines
        String line2 = "" + String.format("%.5f", sigma1), line3 = "" + String.format("%.5f", sigma2);
        for (int i = 0; i < 4; ++i) {
            double curr = Double.parseDouble(args[14 + i]);
            line2 += ' ' + String.format("%.5f", sigma1 * curr);
            line3 += ' ' + String.format("%.5f", sigma2 * curr);
        }
        if (printing) {
            System.out.println(line2);
            System.out.println(line3);
        }

    }
}
