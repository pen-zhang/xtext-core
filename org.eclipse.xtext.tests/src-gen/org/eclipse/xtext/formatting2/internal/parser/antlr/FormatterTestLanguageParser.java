/*
 * generated by Xtext
 */
package org.eclipse.xtext.formatting2.internal.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.formatting2.internal.services.FormatterTestLanguageGrammarAccess;

public class FormatterTestLanguageParser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private FormatterTestLanguageGrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	
	@Override
	protected org.eclipse.xtext.formatting2.internal.parser.antlr.internal.InternalFormatterTestLanguageParser createParser(XtextTokenStream stream) {
		return new org.eclipse.xtext.formatting2.internal.parser.antlr.internal.InternalFormatterTestLanguageParser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "Root";
	}
	
	public FormatterTestLanguageGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(FormatterTestLanguageGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}
