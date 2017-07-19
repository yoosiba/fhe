package app;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/***/
public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		 System.out.println(System.currentTimeMillis() + " app.Activator :: start : begin");
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println(System.currentTimeMillis() + " app.Activator :: stop : begin");
		Activator.context = null;
	}

}
