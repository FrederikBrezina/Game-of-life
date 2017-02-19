/**
 * 
 */

/**
 * @author Fred
 *
 */
public class GameOfLife {

	/**
	
	 * void
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//start the world
		World world=new World();
		world.createWorld();
		//initialize printing
		Printer printer=new Printer(world);
		printer.start();

	}

}
