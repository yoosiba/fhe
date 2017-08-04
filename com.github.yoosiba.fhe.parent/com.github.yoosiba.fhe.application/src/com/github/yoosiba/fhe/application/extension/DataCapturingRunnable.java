package com.github.yoosiba.fhe.application.extension;

import org.eclipse.core.runtime.ISafeRunnable;

public class DataCapturingRunnable implements ISafeRunnable {
	public static final String NO_DATA = "No Data Recieved";
	String data = NO_DATA;
	private final IApplicationExtension extension;

	DataCapturingRunnable(IApplicationExtension extension) {
		this.extension = extension;
	}

	@Override
	public void run() throws Exception {
		data = extension.getExtensionData();
	}

}
