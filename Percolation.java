/*********************************
  * Author:       Paul Rodriguez
  * Written:      2/5/2014
  * Last Updated: 2/5/2014
  * 
  * Compilation:  N/A
  * Execution:    N/A
  * 
  * this is the class for the percolation system.
  * it must be created in another java file to use it.
  * 
  * 
  **********************************/
public class Percolation
{
    
    private  WeightedQuickUnionUF uf;        //  tree of connected sites
    private  WeightedQuickUnionUF fullCheck; //  used to check if site is full
    
    private int size;                        //  size of grid
    private int sitesOpen;                   //  number of sites open
    private boolean percolation;             //  if system percolates
    private boolean[] nodeOpen;              //  true if a node is open
 
    /**
     * constructor that creates the nodes for the system and 
     * sets all nodes to be closed
     * @param N: takes size of system in N*N grid
     * throws IllegaArgumentException() if N < 0
     */
    public Percolation (int N)
    {
        if (N < 0) 
        {
            throw new IllegalArgumentException();
        }
        size = N;
        
        //  creates array of nodes so that they can be connected
        uf = new WeightedQuickUnionUF(N*N + 2);
        fullCheck = new WeightedQuickUnionUF(N*N+1);
        
        nodeOpen = new boolean[N*N + 2];
        percolation = false;
        
        sitesOpen = 0;
       
        for (int i = 0; i < N*N + 1; i++)
        {         
            nodeOpen[i] = false;
        }
    }
    
    /**
     * opens a site at the specified
     * index
     * @param i: the row to open a site
     * @param j: the column to open a site
     * 
     * throws IndexOutOfBoundsException when the row
     * and column indeces are out of bounds
     */
    public void open(int i, int j)
    {
       
        
        if (i < 1 || i > size || j < 1 || j > size) 
        {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        //  if site is not open it then open it
        else if (!isOpen(i, j))
        {
            nodeOpen[(i - 1)*size + j] = true;
          
            sitesOpen++;    
            //  connect to top virtual node if current node is in the first row
            if (i == 1)
            {
                uf.union(0, size*(i - 1) + j); 
                fullCheck.union(0, size*(i - 1) + j);
            } 
        }
        else
        {
            return;
        }
       
        if (j - 1 > 0)
        {
           //  check if left node to the currrent one is open
            if (isOpen(i, j - 1))
            {
                //  connect left node with current node if open
                uf.union(size*(i - 1) + j, size*(i - 1) + j - 1);
                fullCheck.union(size*(i - 1) + j, size*(i - 1) + j - 1);
            }
        }
        
        if (j + 1 <= size)
        {
            //  if right side node is open, connect it to current node
            if (isOpen(i, j + 1))
            {    
                uf.union(size*(i - 1) + j, size*(i - 1) + j + 1);
                fullCheck.union(size*(i - 1) + j, size*(i - 1) + j + 1);
            }
        }
 
        if (i - 1 > 0)
        {
            //  connect current node with top node if its open
            if (isOpen(i - 1, j))
            {
                uf.union(size*(i - 1) + j, size*(i - 2) + j);
                fullCheck.union(size*(i - 1) + j, size*(i - 2) + j);
            }
        }
        
        if (i + 1 <= size)
        {
            //  connect current node with bottom node if its open
            if (isOpen(i + 1, j))
            {
                 uf.union(size*(i - 1) + j, size*i + j);
                 fullCheck.union(size*(i - 1) + j, size*i + j);
            }
        }
        
        //  connect bottom virtual node to opened bottom node
        if (i == size && !percolation)
        {
            uf.union(size*size + 1, size*(i - 1) + j);
        }
        
    }
    
    /**
     * checks if a site at the specified
     * index is open
     * 
     * @param i: row in grid
     * @param j: column in grid
     * 
     * throws IndexOutOfBoundsException for i and j
     * out of bounds
     * 
     * returns true if its open, false otherwite
     */
    public boolean isOpen(int i , int j)
    {
        if (i < 1 || i > size || j < 1 || j > size)
        {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
      
        return nodeOpen[size*(i - 1) + j];
    }
    
    /**
     * checks if site at the specified
     * position is connected to a site 
     * on the top row
     * 
     * @param i: row in grid
     * @param j: column in grid
     * 
     * throws IndexOutOfBoundsException for j and j
     * out of bounds
     */
    public boolean isFull(int i, int j)
    {
        if (i < 1 || i > size || j < 1 || j > size) 
        {
            throw new IndexOutOfBoundsException("index out of bounds");  
        }
        //  if site is open then check it
        if (isOpen(i, j))
        {
            //  return true if current site is connected to site at top
            if (fullCheck.connected(size*(i - 1) + j, 0)) 
            {
                return true;
            }   
        }
        return false;
    }
       
    /**
     * check if the system percolates when
     * a site from the bottom and from the top
     * are connected
     * 
     * returns true if system percolates, false otherwise
     */
    public boolean percolates()
    {
        //return true if bottom virtual node is connected to top one  
        if (uf.connected(0, size*size+1) && sitesOpen >= 1)
        {
            percolation = true; //  set to true to know system percolates
            return true;
        }  
        return false;
     }        
}

    
    
   
