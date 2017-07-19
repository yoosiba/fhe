package core;

/**
 * This class controls all aspects of the application's execution
 */
public class Greeter {

    public void greet(String name) {
        System.out.println(System.currentTimeMillis() + " core.Greeter :: greet : start");
        System.out.println("Hi, my name is " + name);
        System.out.println(System.currentTimeMillis() + " core.Greeter :: greet : end");
    }
}
