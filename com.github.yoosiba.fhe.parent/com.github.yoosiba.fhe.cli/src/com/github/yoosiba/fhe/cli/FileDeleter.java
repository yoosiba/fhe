/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */

// based on package org.eclipse.n4js.utils.io.FileDeleter;
package com.github.yoosiba.fhe.cli;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;


/**
 * For deleting a file resource or recursively a folder and it content.
 */
public class FileDeleter implements FileVisitor<Path> {

	/**
	 * Deletes a single file or recursively a folder with its content.
	 *
	 * @param resourceToDelete
	 *            the file resource or to the folder to delete.
	 * @throws IOException
	 *             in unrecognized cases.
	 * @see #delete(Path)
	 */
	public static void delete(File resourceToDelete) throws IOException {
		if (resourceToDelete.exists()) {
			delete(resourceToDelete.toPath());
		}
	}

	/**
	 * Deletes a single file or recursively a folder with its content.
	 *
	 * @param resourceToDelete
	 *            path to the file resource or to the folder to delete.
	 * @throws IOException
	 *             in unrecognized cases.
	 */
	public static void delete(Path resourceToDelete) throws IOException {
		if (resourceToDelete.toFile().exists()) {
			Files.walkFileTree(resourceToDelete, new FileDeleter());
		}
	}


	/**
	 * Sugar for {@link #delete(File)}, allows to pass custom error handler. If exception is caught handler will be
	 * invoked, and exception will not be propagated to the caller.
	 *
	 * @param resourceToDelete
	 *            path to the file resource or to the folder to delete.
	 * @param errorHandler
	 *            error handler invoked in case of {@link IOException} is caught
	 */
	public static void delete(File resourceToDelete, Consumer<IOException> errorHandler) {
		if (resourceToDelete.exists()) {
			try {
				Files.walkFileTree(resourceToDelete.toPath(), new FileDeleter());
			} catch (IOException ioe) {
				errorHandler.accept(ioe);
			}
		}
	}

	/**
	 * Sugar for {@link #delete(Path)}, allows to pass custom error handler. If exception is caught handler will be
	 * invoked, and exception will not be propagated to the caller.
	 *
	 * @param resourceToDelete
	 *            path to the file resource or to the folder to delete.
	 * @param errorHandler
	 *            error handler invoked in case of {@link IOException} is caught
	 */
	public static void delete(Path resourceToDelete, Consumer<IOException> errorHandler) {
		if (resourceToDelete.toFile().exists()) {
			try {
				Files.walkFileTree(resourceToDelete, new FileDeleter());
			} catch (IOException ioe) {
				errorHandler.accept(ioe);
			}
		}
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		Files.delete(file);
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		logError(file, exc);
		return CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		Files.delete(dir);
		return CONTINUE;
	}

	private void logError(Path path, IOException e) {
			System.err.println("Unexpected file visiting failure" + path);
			e.printStackTrace();
	}

}
