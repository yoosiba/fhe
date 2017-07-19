/** *****************************************************************************
 * Copyright (c) 2008, 2011 Sonatype Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Sonatype Inc. - initial API and implementation
 ****************************************************************************** */
package app;

import java.util.StringJoiner;

import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import core.Greeter;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

    @Override
    public Object start(IApplicationContext context) throws Exception {
        System.out.println(System.currentTimeMillis() + " app.Application :: start : begin");
        doMain("IApplication.start");
        System.out.println(System.currentTimeMillis() + " app.Application :: start : end");
        return IApplication.EXIT_OK;
    }

    @Override
    public void stop() {
        // nothing to do
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis() + " app.Application :: main : begin");
        StringJoiner sj = new StringJoiner(" ,", "[", "]");
        for (int i = 0; i < args.length; i++) {
            sj.add(args[i]);
        }
        doMain("Application.main with args " + sj.toString());
        System.out.println(System.currentTimeMillis() + " app.Application :: main : end");
    }

    private static void doMain(String location) {
        System.out.println(System.currentTimeMillis() + " app.Application :: doMain : begin");
        Greeter greet = new Greeter();
        greet.greet(location);
        System.out.println("BTW, Platform.isRunning() : " + Platform.isRunning());
        System.out.println(System.currentTimeMillis() + " app.Application :: doMain : end");
    }
}
