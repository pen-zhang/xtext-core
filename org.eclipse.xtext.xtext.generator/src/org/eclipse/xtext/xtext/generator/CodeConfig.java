/**
 * Copyright (c) 2015, 2020 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.xtext.xtext.generator;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.XtextVersion;
import org.eclipse.xtext.xtext.generator.model.TypeReference;
import org.eclipse.xtext.xtext.generator.model.annotations.IClassAnnotation;

import com.google.inject.Injector;

/**
 * Configuration object for generated code.
 */
public class CodeConfig implements IGuiceAwareGeneratorComponent {
	private static final String FILE_HEADER_VAR_TIME = "${time}";

	private static final String FILE_HEADER_VAR_DATE = "${date}";

	private static final String FILE_HEADER_VAR_YEAR = "${year}";

	private static final String FILE_HEADER_VAR_USER = "${user}";

	private static final String FILE_HEADER_VAR_VERSION = "${version}";

	private String encoding = Charset.defaultCharset().name();

	private String lineDelimiter = Strings.newLine();

	private String fileHeader;

	private String fileHeaderTemplate = "/*\n * generated by Xtext\n */";

	private final List<IClassAnnotation> classAnnotations = new ArrayList<>();

	private boolean preferXtendStubs = true;

	private XtextVersion xtextVersion;

	/**
	 * Configure a template for file headers. The template can contain
	 * variables:
	 * <ul>
	 * <li><code>${time}</code> - the current time of the day
	 * (hour:minute:second)</li>
	 * <li><code>${date}</code> - the current date (month day, year)</li>
	 * <li><code>${year}</code> - the current year</li>
	 * <li><code>${user}</code> - the content of the 'user.name' system
	 * property</li>
	 * <li><code>${version}</code> - the generator bundle version</li>
	 * </ul>
	 */
	public void setFileHeader(String fileHeaderTemplate) {
		this.fileHeaderTemplate = fileHeaderTemplate;
	}

	/**
	 * Class annotations are used to configure specific Java annotations to be
	 * added to each generated class.
	 */
	public void addClassAnnotation(IClassAnnotation annotation) {
		classAnnotations.add(annotation);
	}

	@Override
	public void initialize(Injector injector) {
		injector.injectMembers(this);
		xtextVersion = XtextVersion.getCurrent();
		if (lineDelimiter == null) {
			lineDelimiter = "\n";
		}
		String fileHeader = fileHeaderTemplate;
		if (fileHeader != null) {
			if (fileHeader.contains(CodeConfig.FILE_HEADER_VAR_TIME)) {
				String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
				fileHeader = fileHeader.replace(CodeConfig.FILE_HEADER_VAR_TIME, time);
			}
			if (fileHeader.contains(CodeConfig.FILE_HEADER_VAR_DATE)) {
				String date = new SimpleDateFormat("MMM d, yyyy").format(new Date());
				fileHeader = fileHeader.replace(CodeConfig.FILE_HEADER_VAR_DATE, date);
			}
			if (fileHeader.contains(CodeConfig.FILE_HEADER_VAR_YEAR)) {
				String year = new SimpleDateFormat("yyyy").format(new Date());
				fileHeader = fileHeader.replace(CodeConfig.FILE_HEADER_VAR_YEAR, year);
			}
			if (fileHeader.contains(CodeConfig.FILE_HEADER_VAR_USER)) {
				String user = System.getProperty("user.name");
				if (user != null) {
					fileHeader = fileHeader.replace(CodeConfig.FILE_HEADER_VAR_USER, user);
				}
			}
			if (fileHeader.contains(CodeConfig.FILE_HEADER_VAR_VERSION)) {
				fileHeader = fileHeader.replace(CodeConfig.FILE_HEADER_VAR_VERSION, xtextVersion.toString());
			}
		}
		this.fileHeader = fileHeader;
	}

	public String getClassAnnotationsAsString() {
		if (classAnnotations.isEmpty()) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (IClassAnnotation annotation : classAnnotations) {
			stringBuilder.append(annotation.toString()).append(Strings.newLine());
		}
		return stringBuilder.toString();
	}

	public String getAnnotationImportsAsString() {
		if (classAnnotations.isEmpty()) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (IClassAnnotation annotation : classAnnotations) {
			TypeReference importString = annotation.getAnnotationImport();
			if ((importString != null)) {
				stringBuilder.append("import ").append(importString).append(";").append(Strings.newLine());
			}
		}
		return stringBuilder.toString();
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getLineDelimiter() {
		return lineDelimiter;
	}

	public void setLineDelimiter(String lineDelimiter) {
		this.lineDelimiter = lineDelimiter;
	}

	public String getFileHeader() {
		return fileHeader;
	}

	public List<IClassAnnotation> getClassAnnotations() {
		return classAnnotations;
	}

	public boolean isPreferXtendStubs() {
		return preferXtendStubs;
	}

	public void setPreferXtendStubs(boolean preferXtendStubs) {
		this.preferXtendStubs = preferXtendStubs;
	}

	public XtextVersion getXtextVersion() {
		return xtextVersion;
	}
}
