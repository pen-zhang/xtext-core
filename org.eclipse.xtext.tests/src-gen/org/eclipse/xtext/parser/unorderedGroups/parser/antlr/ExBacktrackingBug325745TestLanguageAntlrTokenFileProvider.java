/*
 * generated by Xtext
 */
package org.eclipse.xtext.parser.unorderedGroups.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class ExBacktrackingBug325745TestLanguageAntlrTokenFileProvider implements IAntlrTokenFileProvider {
	
	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
    	return classLoader.getResourceAsStream("org/eclipse/xtext/parser/unorderedGroups/parser/antlr/internal/InternalExBacktrackingBug325745TestLanguageParser.tokens");
	}
}
