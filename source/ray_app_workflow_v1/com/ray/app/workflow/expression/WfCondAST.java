//Source file: D:\\work\\telcom97\\code\\workflow\\com\\butone\\workflow\\runtime\\conditionParser\\WfCondAST.java
package com.ray.app.workflow.expression;
import antlr.CommonAST;
import antlr.Token;

import com.ray.app.workflow.exception.WfException;
public abstract class WfCondAST extends CommonAST implements PTokenTypes
{
	public WfCondAST(Token tok)
	{
		super(tok);
	}
	public abstract String value() throws WfException;
	public abstract String VType() throws WfException;
}
