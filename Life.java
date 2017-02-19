/**
 * 
 */
import java.util.*;
/**
 * @author Fred
 * check if one random produces more random
 */
public class Life extends Thread {
	
	//instances variables
	private double fitness;
	private Random rand=new Random();
	private long now,lifeSpan;
	private World world;
	private int i,y;
	private int type;
	private boolean flag=true,flag2=true;
	
	/**
	 * Initializes the thread
	 */
	public Life(World world, int lifeSpan,double fitness, int i,int y,int type){
		
		this.lifeSpan=(long) (Math.abs(rand.nextInt())%lifeSpan)*1000;
		this.fitness=fitness;
		this.i=i;this.y=y;
		this.type=type;
		this.world=world;
	}
	
	/**
	 * runs the thread
	 */
	public void run(){
		
		try{
		
		sleep(lifeSpan);
		}
		catch (InterruptedException e){}
		//if the birth is flaged other lives can not give birth
		//therefore their birth calls repeats until they are the ones giving birth and not just passing
		//thorugh the method so that the thread can finish as
		//in the case of threads which are still running
		//but are off the grid (world object) without reference to them
		while (flag){
		
		world.birth(i,y);
		world.setCheckLife(true);
		}
			
	}
	
	/**
	 * condition flag to set to boolean
	 */
	public void flag(boolean flag){
		
		this.flag=flag;
	}
	
	/**
	 * 
	 * @return fitness of the individual
	 */
	public double getFitness(){
		
		return fitness;
	}
	
	/**
	 * 
	
	 * int
	 * @return type of the individual
	 */
	public int getType(){
		return type;
	}
	
	/**
	 * 
	
	 * int
	 * @return i coordinate
	 */
	public int getI(){
		return i;
	}
	
	/**
	 * 
	
	 * int
	 * @return y coordinate
	 */
	public int getY(){
		return y;
	}
	
}
