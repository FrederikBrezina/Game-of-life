/**
 * 
 */

/**
 * @author Fred
 *
 */
import java.util.*;
import java.util.concurrent.locks.*;
public class World {
	private int size=10;
	private int [] lifeSpan = new int [2];
	private double [] fitness = new double [2];
	private Life[][] world=new Life[size][size];
	private int [] flag=new int[2];
	private ReentrantLock counterLock = new ReentrantLock();
	private Condition condition = counterLock.newCondition();
	private Random rand = new Random();
	private boolean check=true,checklife=false;
	private String printing="";
	/**
	 * Initalize world
	 */
	public World(){
		
		lifeSpan[0]=10; lifeSpan[1]=5; fitness[0]=0.8; fitness[1]=0.4;	
		flag[0]=-1;flag[1]=-1;

	}
	/**
	 * Create world of life
	 */
	public void createWorld(){
		
		//randomize the starting points, they ca not overlap
		int i1=Math.abs(rand.nextInt()) % size; int y1=Math.abs(rand.nextInt()) % size;
		int i2=(i1-3+size) % size; int y2=(y1-3+size) % size;
		int i3=(i2-3+size) % size; int y3=(y2-3+size) % size;
		int i4=(i1+2+size) % size; int y4=(y1+2+size) % size;		
		
		//Intializing population from begginning.
		world[i1][y1]=new Life(this,lifeSpan[0],fitness[0],i1,y1,0);
		world[i2][y2]=new Life(this, lifeSpan[1],fitness[1],i2,y2,1);
		world[i3][y3]=new Life(this, lifeSpan[1],fitness[1],i3,y3,1);
		world[i4][y4]=new Life(this, lifeSpan[1],fitness[1],i4,y4,1);
		
		//starting the threads
		world[i1][y1].start();
		world[i2][y2].start();
		world[i3][y3].start();
		world[i4][y4].start();
		
	}
	
	/**
	 * where the killing and new threading happens
	
	 * void
	 * @param i coordinate i
	 * @param y coordinate y
	 */
	public void birth(int i,int y){
		counterLock.lock();
		
		try{if (check){
			//ensure the target life does not give birth more times
			world[i][y].flag(false);
			checklife=false;
			int type=world[i][y].getType();
			
			//for 
			for (int j=0;j<3;j++){
				for (int u=0;u<3;u++){
					//do not check its own spot
					if (u!=1&j!=1){
						//if (i-1+j>0&&i-1+j<size&&y-1+u>0&&y-1+u<size){ // for the second case
					//generate random fitness number
					double i1=Math.abs(rand.nextDouble()) % 10;					
					//keep the numbers in bounds of matrix size
					int r0=(i-1+j+size)%size;int r1=(y-1+u+size)%size;
					//check whether world is null then the probability is different
					if (world[r0][r1]!=null){
						//if the difference in fitness is greater than random fitness give birth to
						if(world[i][y].getFitness()-world[r0][r1].getFitness()>i1){							
							try{
							//condition to wait until the thread which is to be deleted finishes, this thread does not give any birth
							//that is why we need extra conditions
							check=false;
							
							//giving coordinates of the thread to be deleted, and sending information
							//to the particular life that it should not call birth anymore and should finish
							checklife=false;
							world[r0][r1].flag(false);
							condition.await();							
							}
							
							catch(InterruptedException e){}
							//replace finished thread
							world[r0][r1]=new Life(this, lifeSpan[type],fitness[type],r0,r1,type);
							world[r0][r1].start();
						}
						
					}
					else {
						if(world[i][y].getFitness()>i1){							
								world[r0][r1]=new Life(this, lifeSpan[type],fitness[type],r0,r1,type);
								world[r0][r1].start();
						}
					}										
				}
			}
			}
		//} //for the second case 
			//delete the thread
			world[i][y]=null;
			//Signal that birth is over and ready for another life to give birth
			check=true;			
			
		}
		}
		catch (NullPointerException e){}
		finally {
			if (checklife){
				//release the condition
				condition.signalAll();
			}
			counterLock.unlock();
			
		}		
	}
	
	public void print(){
		/**
		 * print the state of the world, 0 is the stronger type, 1 is the weaker.
		 */
		printing="";
		for (int f1=0;f1<size;f1++){
			for (int f2=0;f2<size;f2++){
				if(world[f1][f2]!=null){
				printing=printing+world[f1][f2].getType();}
				else{
					printing+="-";
				}
			}
			printing+="\n";
		}
		
		System.out.println();
		System.out.println();
		System.out.println(printing);
		
	}
	public void setCheckLife(boolean sety){
		this.checklife=sety;
	}
	
	
}
