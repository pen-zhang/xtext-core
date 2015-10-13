/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.serializer.diagnostic;

import static org.eclipse.xtext.serializer.impl.FeatureFinderUtil.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.serializer.analysis.IContext;

/**
 * @author Moritz Eysholdt - Initial contribution and API
 */
public class SerializationDiagnostic implements ISerializationDiagnostic {

	protected EStructuralFeature feature;

	protected String message;

	protected EObject semanticObject;

	protected Grammar grammar;

	protected IContext context;

	private String id;

	private Throwable throwable;

	public SerializationDiagnostic(String id, EObject sem, AbstractElement ele, Grammar g, String msg, Throwable t) {
		this(id, sem, sem != null ? getFeature(ele, sem.eClass()) : null, g, msg, t);
	}

	public SerializationDiagnostic(String id, EObject sem, AbstractElement element, Grammar grammar, String message) {
		this(id, sem, sem != null ? getFeature(element, sem.eClass()) : null, grammar, message);
	}

	public SerializationDiagnostic(String id, EObject sem, EStructuralFeature f, Grammar g, String message) {
		super();
		this.id = id;
		this.semanticObject = sem;
		this.grammar = g;
		this.message = message;
		this.feature = f;
	}

	public SerializationDiagnostic(String id, EObject sem, EStructuralFeature f, Grammar g, String m, Throwable t) {
		super();
		this.id = id;
		this.semanticObject = sem;
		this.grammar = g;
		this.throwable = t;
		this.feature = f;
		this.message = m;
	}

	/**
	 * @deprecated use {@link #SerializationDiagnostic(String, EObject, IContext, Grammar, String)}.
	 */
	@Deprecated
	public SerializationDiagnostic(String id, EObject sem, EObject context, Grammar grammar, String message) {

	}

	public SerializationDiagnostic(String id, EObject sem, IContext context, Grammar grammar, String message) {
		super();
		this.id = id;
		this.semanticObject = sem;
		this.grammar = grammar;
		this.message = message;
		this.context = context;
	}

	public SerializationDiagnostic(String id, EObject semanticObject, Grammar grammar, String message) {
		this(id, semanticObject, (EStructuralFeature) null, grammar, message);
	}

	@Override
	public boolean breaksSyntax() {
		return true;
	}

	@Override
	public Throwable getException() {
		return throwable;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public EObject getSemanticObject() {
		return semanticObject;
	}

	@Override
	@Deprecated
	public EObject getContext() {
		return context.getActionOrRule();
	}

	@Override
	public IContext getIContext() {
		return context;
	}

	@Override
	public Grammar getGrammar() {
		return grammar;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public EStructuralFeature getEStructuralFeature() {
		return feature;
	}
}
