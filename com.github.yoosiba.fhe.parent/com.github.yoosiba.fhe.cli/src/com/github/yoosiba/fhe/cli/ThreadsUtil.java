package com.github.yoosiba.fhe.cli;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Utility for identifying running threads.
 */
public class ThreadsUtil {

	private static String NL = String.format("%n");

	/**
	 * Returns number of running threads, whose description contains given token.
	 */
	public static int getIdentifiedThredsCount(String descriptionToken) {
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		int threadCount = 0;

		for (Iterator<Thread> iterator = threadSet.iterator(); iterator.hasNext();) {
			Thread next = iterator.next();
			String threadDesc = next.toString();
			if (containsToken(threadDesc, descriptionToken)) {
				threadCount++;
			}
		}
		return threadCount;
	}

	/**
	 * Returns information about currently running threads. Information is formatted
	 * in a way that allows to see threads whose description contains provided
	 * token. 
	 *
	 * Clean but slow.
	 * @see https://stackoverflow.com/questions/1323408/get-a-list-of-all-threads-currently-running-in-java
	 */
	public static String getThreadsInfo(String descriptionToken) {
		StringBuilder info = new StringBuilder();
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		Set<Thread> equinoxThreads = new HashSet<Thread>();

		info.append("threads").append(NL);
		for (Iterator<Thread> iterator = threadSet.iterator(); iterator.hasNext();) {
			Thread next = iterator.next();
			String threadDesc = next.toString();
			if (containsToken(threadDesc, descriptionToken)) {
				equinoxThreads.add(next);
			} else {
				info.append(" thread :: " + next.getId() + " : " + next.toString()).append(NL);
			}
		}
		info.append("threads described by " + descriptionToken).append(NL);
		for (Iterator<Thread> iterator = equinoxThreads.iterator(); iterator.hasNext();) {
			Thread next = iterator.next();
			info.append(" thread :: " + next.getId() + " : " + next.toString()).append(NL);
		}
		info.append(NL);
		return info.toString();
	}

	private static boolean containsToken(final String message, String token) {
		return message.toLowerCase().contains(token);
	}
}
