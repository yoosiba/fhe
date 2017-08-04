package com.github.yoosiba.fhe.extension;

import static com.github.yoosiba.fhe.extension.LogUtil.log;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/** No real work, just for logs. */
public class ExtensionActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		log("ExtensionActivator :: start : begin");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		log("ExtensionActivator :: stop : begin");
	}

}
