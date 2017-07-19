package check.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Process execution utility */
public class ProcessUtil {

    /** Quick access to system property "os.name" */
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    /** Windows marker */
    private static final String WIN = "win";

    /**
     * Creates and executes process based on the provided commands.
     * Adds platform specific shell call, but does not escape paths or anything.
     * Error output is redirected, standard output is captured and forwarded to 
     * standard out. Propagates exceptions, returns exit code.
     *
     * @param commands to execute
     * @return exit code of the process
     * @throws IOException
     * @throws InterruptedException 
     */
    public static int execute(String... commands) throws IOException, InterruptedException {
        List<String> cmds = new ArrayList<String>();
        if (isWindows()) {
            cmds.add("cmd");
            cmds.add("/c");
        } else {
            cmds.add("sh");
            cmds.add("-c");
        }
        cmds.addAll(Arrays.asList(commands));
        
        ProcessBuilder builder = new ProcessBuilder(cmds);
        builder.redirectErrorStream(true);
        final Process process = builder.start();
        return watch(process);
    }

    private static int watch(final Process process) throws InterruptedException {
        new Thread() {
            public void run() {
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                try {
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return process.waitFor();
    }

    /**
     * @return true for Windows
     */
    public static boolean isWindows() {
        //may be poor check
        return (OS_NAME.contains(WIN));
    }

}
