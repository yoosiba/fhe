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
package com.github.yoosiba.fhe.application;

import static com.github.yoosiba.fhe.core.LogUtil.log;

import java.util.StringJoiner;

import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.github.yoosiba.fhe.application.extension.EvaluateContributionsHandler;
import com.github.yoosiba.fhe.core.Greeter;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

    @Override
    public Object start(IApplicationContext context) throws Exception {
        log("app.Application :: start : begin");
        doMain("IApplication.start");
        log("app.Application :: start : end");
        return IApplication.EXIT_OK;
    }

    @Override
    public void stop() {
        // nothing to do
    }

    public static void main(String[] args) {
        log("app.Application :: main : begin");
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < args.length; i++) {
            sj.add(args[i]);
        }
        doMain("Application.main with args " + sj.toString());
        log("app.Application :: main : end");
    }

    private static void doMain(String location) {
        log("app.Application :: doMain : begin");
        Greeter greet = new Greeter();
        greet.greet(location);
        log("BTW, Platform.isRunning() : " + Platform.isRunning());
        log("Let's exmine extensions" );
        log("Extensions data: " + EvaluateContributionsHandler.executeAndGetData());
        log("app.Application :: doMain : end");
    }
}
