package core;

import static core.LogUtil.log;

/**
 * This class controls all aspects of the application's execution
 */
public class Greeter {

	public void greet(String name) {
		log("core.Greeter :: greet : start");
		log("Hi, my name is " + name);
		log("core.Greeter :: greet : end");
	}
}
