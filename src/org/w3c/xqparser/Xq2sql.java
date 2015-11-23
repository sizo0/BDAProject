/*
 Copyright (c) 2005 W3C(r) (http://www.w3.org/) (MIT (http://www.lcs.mit.edu/),
 INRIA (http://www.inria.fr/), Keio (http://www.keio.ac.jp/)),
 All Rights Reserved.
 See http://www.w3.org/Consortium/Legal/ipr-notice-20000612#Copyright.
 W3C liability
 (http://www.w3.org/Consortium/Legal/ipr-notice-20000612#Legal_Disclaimer),
 trademark
 (http://www.w3.org/Consortium/Legal/ipr-notice-20000612#W3C_Trademarks),
 document use
 (http://www.w3.org/Consortium/Legal/copyright-documents-19990405),
 and software licensing rules
 (http://www.w3.org/Consortium/Legal/copyright-software-19980720)
 apply.
 */

/*
 * Credit and thanks to David Carlisle 2005.
 */
package org.w3c.xqparser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * Utility class to convert from JavaCC-generated parser output to a XML
 * serialization.
 */
@SuppressWarnings("all")
public class Xq2sql {

	private static int MAXCHAR = 0xFFFF;

	/**
	 * Convert a SimpleNode tree to an XML Serialization.
	 * 
	 * @param prefix
	 *            String used for indent.
	 * @param sql
	 *            output destination for XML.
	 * @param tree
	 *            input tree root.
	 */
	public static String convert(String prefix, SimpleNode tree) {
		/*String sql = "\n";
		sql += prefix + "<" + XPathTreeConstants.jjtNodeName[tree.id] + ">";
		if (tree.m_value != null) {
			sql += "<data>";
			sql += tree.m_value;
			sql += "</data>";

		}
		if (tree.children != null) {
			for (int i = 0; i < tree.children.length; ++i) {
				SimpleNode n = (SimpleNode) tree.children[i];
				if (n != null) {
					sql += convert(prefix + " ", n);
				}
			}
			sql += "\n";
			sql += prefix;
		}
		sql += "</" + XPathTreeConstants.jjtNodeName[tree.id] + ">";
		return sql;*/
		
		String sql = "";
		while (XPathTreeConstants.jjtNodeName[tree.id] != "FLWORExpr") {
			
		}
		
		return sql;
	}

}