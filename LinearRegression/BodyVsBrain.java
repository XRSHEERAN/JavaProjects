import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;
import java.lang.Math;

public class BodyVsBrain {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Invalid Number of Input Arguments");
            return;
        }

        int flag = Integer.valueOf(args[0]);
        int option = flag / 100;

        //Extract and Parse crv
        List<Double> bodyW=new ArrayList<>(), brainW=new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("./data.csv"));) {
            boolean h=true;
            while (scanner.hasNextLine()) {
                
                String line = scanner.nextLine();
                if(h){
                    h=false;
                    continue;
                }
                String[] Aline = line.split(",");
                bodyW.add(Double.parseDouble(Aline[0]));
                brainW.add(Double.parseDouble(Aline[1]));
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        /*
        for(int i=0;i<bodyW.size();++i){
            System.out.println(i+":"+bodyW.get(i)+" " + brainW.get(i));
        }
        */
        //Perform tasks
        if(option==1){
            System.out.println(bodyW.size());
            double mean=meanCalc(bodyW);
            double sd=sdCalc(mean,bodyW);
            System.out.println(String.format("%.4f", mean)+' '+String.format("%.4f", sd));
            mean=meanCalc(brainW);
            sd=sdCalc(mean,brainW);
            System.out.println(String.format("%.4f", mean)+' '+String.format("%.4f", sd));
        }
        else if(option==2){
            if(args.length<3){
                System.out.println("invalid input length, requires 3 args");
            }
            double b0 = Double.valueOf(args[1]), b1=Double.valueOf(args[2]);
            System.out.println(String.format("%.4f",mseCalc(b0,b1,bodyW,brainW)));
        }
        else if(option==3){
          
            if(args.length<3){
                 System.out.println("invalid input length, requires 3 args");
            }
            double b0 = Double.valueOf(args[1]), b1=Double.valueOf(args[2]);
            double[] rst=mseDescent(b0,b1,bodyW,brainW);
            System.out.println(String.format("%.4f",rst[0]));
            System.out.println(String.format("%.4f",rst[1]));
        }
        else if(option==4){
            //start from 0,0
            double b0=0,b1=0,param=Double.valueOf(args[1]);
            int round=Integer.valueOf(args[2]);
            for(int i=0;i<round;++i){
                System.out.print((i+1)+" ");
                double[] rst=mseDescent(b0,b1,bodyW,brainW);
                b0=b0-param*rst[0];
                b1=b1-param*rst[1];
                System.out.println(String.format("%.4f",b0)+' '+String.format("%.4f",b1)+' '+String.format("%.4f",mseCalc(b0,b1,bodyW,brainW)));

            }
        }
        else if(option==5){
            double[] ret=closeG(bodyW,brainW);//closed form gradients
            System.out.println(String.format("%.4f",ret[0])+' '+String.format("%.4f",ret[1])+' '+String.format("%.4f",mseCalc(ret[0],ret[1],bodyW,brainW)));
        }
        else if(option==6){
            double wgt=Integer.valueOf(args[1]);
            double[]ret=closeG(bodyW,brainW);
            System.out.println(String.format("%.4f",ret[0]+ret[1]*wgt));
        }
        else if(option==7){
            double param=Double.valueOf(args[1]);
            int round=Integer.valueOf(args[2]);
            //Normalization
            double b0=0,b1=0,mean=meanCalc(bodyW),sd=sdCalc(mean,bodyW);
            List<Double> normal=new ArrayList<>();
            for(int i=0;i<bodyW.size();++i){
                normal.add((bodyW.get(i)-mean)/sd);

            }
            
            for(int i=0;i<round;++i){
                System.out.print((i+1)+" ");
                double[] rst=mseDescent(b0,b1,normal,brainW);
                b0=b0-param*rst[0];
                b1=b1-param*rst[1];
                System.out.println(String.format("%.4f",b0)+' '+String.format("%.4f",b1)+' '+String.format("%.4f",mseCalc(b0,b1,normal,brainW)));

            }
        }
        else if(option==8){
            Random nxt=new Random();
            double param=Double.valueOf(args[1]);
            int round=Integer.valueOf(args[2]);
            //Normalization
            double b0=0,b1=0,mean=meanCalc(bodyW),sd=sdCalc(mean,bodyW);
            List<Double> normal=new ArrayList<>();
            for(int i=0;i<bodyW.size();++i){
                normal.add((bodyW.get(i)-mean)/sd);

            }
            
            for(int i=0;i<round;++i){
                System.out.print((i+1)+" ");
                //pick the random number
                int pick=nxt.nextInt(normal.size());
                double[] deriv=new double[2];
                //estimate
                deriv[0]=2*(b0+b1*normal.get(pick)-brainW.get(pick));
                deriv[1]=2*normal.get(pick)*(b0+b1*normal.get(pick)-brainW.get(pick));
                b0=b0-param*deriv[0];
                b1=b1-param*deriv[1];
                System.out.println(String.format("%.4f",b0)+' '+String.format("%.4f",b1)+' '+String.format("%.4f",mseCalc(b0,b1,normal,brainW)));
                
            }
        }
        else{
            System.out.println("Flag not available");
        }
    }
    private static double meanCalc(List<Double> lst){
        double sum=0;
        for(double d : lst){
            sum+=d;
        }
        return sum/lst.size();
    }
    private static double sdCalc(double mean, List<Double>lst){
        double sd=0;
        for(double d : lst){
            sd+=Math.pow(d-mean,2);
        }
        return Math.sqrt(sd/(lst.size()-1));
    }
    private static double mseCalc(double b0, double b1, List<Double> x, List<Double> y){
        double mse=0;
        for(int i=0;i<x.size();++i){
            mse+=Math.pow(b0+b1*x.get(i)-y.get(i),2);
        }
        return mse/x.size();
    }
    private static double[] mseDescent(double b0, double b1, List<Double> x, List<Double> y){
        double[] ret=new double[2];
        for(int i=0;i<x.size();++i){
            ret[0]+=(b0+b1*x.get(i)-y.get(i));//b0 deriv
            ret[1]+=(b0+b1*x.get(i)-y.get(i))*x.get(i);
        }
        ret[0]=(ret[0]*2)/x.size();
        ret[1]=(ret[1]*2)/x.size();
        return ret;
    }
    private static double[] closeG(List<Double> x, List<Double> y){
        double[] ret=new double[2];
        double x_m=meanCalc(x),y_m=meanCalc(y);
        double topSum=0,botSum=0;
        for(int i=0;i<x.size();++i){
            topSum+=((x.get(i)-x_m)*(y.get(i)-y_m));
            botSum+=Math.pow((x.get(i)-x_m),2);
        }
        ret[1]=topSum/botSum;
        ret[0]=y_m-ret[1]*x_m;
        return ret;
    }
}
