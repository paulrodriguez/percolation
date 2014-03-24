/*********************************
  * Author:       Paul Rodriguez
  * Written:      2/5/2014
  * Last Updated: 2/5/2014
  * 
  * Compilation:  javac PercolationStats.java
  * Execution:    java PercolationStats N T
  * 
  * percolates a system of nodes by randomly opening a site
  * until a node from the top and from the bottom are connected
  * 
  * N and T are integer command line values
  * 
  * 
  **********************************/

import java.util.Random;
public class PercolationStats
{
    private double[] openSites;       //  opened sites for each experiment T
    private int size;                 //  size of square grid
    private Percolation percolation;  //  percolaton constructor
    private int T;                    //  the number of experiments to run
    
    /**
     * constructor for PercolationStats
     * runs the a number of T experiments
     * on a percolation of grid N*N
     * 
     * throws IllegalArgumentException if numbe
     * of experiments T is no enough
     * 
     */
    public PercolationStats(int N, int T)
    {
       
        if (T <= 1)
        {
            throw new IllegalArgumentException("T must be greater than 1.");
        }
        
        //  set object variables
        this.T = T;
        size = N*N;
        openSites = new double[T];
        
        int row = 0;     //  row on where to open a site
        int column = 0;  //  column on where to open a site
        
        Random randomGenerator = new Random();  //  generates number to open a site
        int openedSites = 0;                    //  keeps track of opened sites in each experiemnt
        for (int i = 0; i < T; i++)
        {
            openedSites = 0;
            percolation = new Percolation(N);
            //  run experiment until it percolates
            while (!percolation.percolates())
           {
                //  generate random number for column and row
                row = randomGenerator.nextInt(N) + 1;
                column = randomGenerator.nextInt(N) + 1;
                
                if (!percolation.isOpen(row, column))
                {
                    openedSites++;
                }
               percolation.open(row, column);
           }
          
           openSites[i] = openedSites;
   
           percolation = null;
        }
    }
    
    /**
     * calculatse the mean value of the 
     * opened sites of the experiments
     * 
     * returns the mean 
     */
    public double mean()
    {
        double meanValue = 0;  //  mean value to be returned initialized
        
        for (int i = 0; i < openSites.length; i++)
        {
            meanValue += (openSites[i]/size);
        }
      
       return meanValue/openSites.length;
    }
    
    /**
     * calculates the standard
     * deviation of experiments
     * 
     * returns standard deviation
     */
    public double stddev()
    {
        double meanVal = this.mean(); //  get the mean of experiemnts
        double stdevVal = 0;          //keeps track of standard deviation
        
        //  calculate squares for each experiment
        for (int i = 0; i < openSites.length; i++)
        {
            stdevVal += ((openSites[i]/size) - meanVal)*((openSites[i]/size) - meanVal);
        }
        
        double finalValue = Math.sqrt(stdevVal/(openSites.length - 1)); //  calculate final value of standard dev.
        
        return finalValue;
       
   }
   
    /**
     * calculates the lower boundary
     * confidence of experiments
     * 
     * returns the low confidence
     */
    public double confidenceLo()
    {
        return (this.mean() - (1.96*this.stddev()/Math.sqrt(this.T)));
    }
   
    /**
     * calculates the upper boundary of
     * confidence of experiemtns
     * 
     * returns the high confidence
     */
    public double confidenceHi()
    {
        return (this.mean() + (1.96*this.stddev()/Math.sqrt(this.T)));
    }
   
    /**
     * the main function to test the
     * percoolation class
     * 
     * takes in parameters for grid size and number
     * of experiemtns
     */
    public static void main(String[] args)
    {
        int N = Integer.parseInt(args[0]); //  assign grid size
        int T = Integer.parseInt(args[1]); //  assign number of experiemnts
        
        //start percolation
        PercolationStats test1 = new PercolationStats(N, T);
        
        double mean = test1.mean();     //  get mean of experiments
        double stddev = test1.stddev(); //  get standard dev of experiments
        
        double confintx = test1.confidenceLo();  //  get low confidence
        double confinty = test1.confidenceHi();  //  get high confidence
        //print results
        StdOut.println("Mean: " + mean);
        StdOut.println("Std Dev: " + stddev);
        StdOut.println("95% confidence interval: " + confintx +  ", " + confinty);
    }
}