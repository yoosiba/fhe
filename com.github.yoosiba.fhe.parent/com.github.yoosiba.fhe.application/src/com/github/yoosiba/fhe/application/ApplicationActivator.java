package com.github.yoosiba.fhe.application;

import static com.github.yoosiba.fhe.core.LogUtil.log;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/** No real work, just for logs. */
public class ApplicationActivator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		ApplicationActivator.context = bundleContext;

		log("app.Activator :: start : begin");
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		log("app.Activator :: stop : begin");
		ApplicationActivator.context = null;
	}
}
