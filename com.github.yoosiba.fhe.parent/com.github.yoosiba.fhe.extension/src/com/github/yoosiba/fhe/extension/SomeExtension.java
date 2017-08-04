package com.github.yoosiba.fhe.extension;

import com.github.yoosiba.fhe.application.extension.IApplicationExtension;

/**
 * This class controls all aspects of the application's execution
 */
public class SomeExtension implements IApplicationExtension {

	@Override
	public String getExtensionData() {
		return this.getClass().getName();
	}
}
