package com.github.yoosiba.fhe.application.extension;

import static com.github.yoosiba.fhe.core.LogUtil.log;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;

public class EvaluateContributionsHandler {
	private static final String IGREETER_ID = "com.github.yoosiba.fhe.application.extensions";

	public static String executeAndGetData() {
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(IGREETER_ID);
		StringBuilder data = new StringBuilder();
		try {
			log("Collecting data");
			for (IConfigurationElement configurationElement : config) {
				log("Collecting data from " + configurationElement);
				final Object extension = configurationElement.createExecutableExtension("class");
				if (extension instanceof IApplicationExtension) {
					data.append(executeExtension((IApplicationExtension) extension));
				}
			}
		} catch (CoreException ex) {
			log(ex.getMessage());
		}
		return data.toString();
	}

	private static String executeExtension(final IApplicationExtension extension) {
		DataCapturingRunnable runnable = new DataCapturingRunnable(extension);
		SafeRunner.run(runnable);
		return runnable.data;
	}
}
