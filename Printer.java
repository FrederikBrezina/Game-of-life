/**
 * Assists with printing of the state of the world
 */

/**
 * @author Fred
 *
 */
public class Printer extends Thread {
	private boolean still;
	private World world;
	/**
	 * Initializes the printer
	 */
public Printer(World world){

	this.world=world;
}

/**
 * Threads the printing
 */
public void run(){
	
	still=true;
	while(still){
		try{
		sleep(500);
		
		}
		catch(InterruptedException e){}
		world.print();
	}
}
}
