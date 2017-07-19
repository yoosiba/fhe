package check;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

import check.utils.ProcessUtil;
import check.utils.SizeUtil;

/**
 * Tests for a given command line tool.
 */
public class ToolTestIT {

	private static final String CLI_JAR_NAME = "cli.jar";
	/** Package depth of this class, binary will be in the root. */
	private static final String JAR_REL_PATH = "../";
	private static final String JAVA_PATH = System.getProperty("java.home") + "/bin/java";
	private static final int OK = 0;
	private static final long ONE_MB = 1024 * 1024;

	/**
	 * Check if cli tool is accessible and callable.
	 */
	@Test
	public void testCliTool() throws URISyntaxException, IOException, InterruptedException {
		System.out.println("Running test on " + CLI_JAR_NAME);

		URL url = ToolTestIT.class.getResource(JAR_REL_PATH + CLI_JAR_NAME);

		File jar = new File(url.toURI());
		assertTrue("Can't locate CLI tool under " + jar.getAbsolutePath(), jar.exists());

		long size = jar.length();
		assertTrue("Expected tool to be bigger than 1 MB", size > ONE_MB);
		System.out.println("tool size is " + SizeUtil.bytesToHuman(size));

		System.out.println("calling tool + " + url);
		int exitCode = ProcessUtil.execute(JAVA_PATH + " -jar " + jar.getAbsolutePath(), "arg1", "2");
		assertEquals("process calling tool returned error code " + exitCode, OK, exitCode);

	}

}
