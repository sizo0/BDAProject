/* Generated By:JJTree&JavaCC: Do not edit this line. XPathConstants.java */
package org.w3c.xqparser;

public interface XPathConstants {

  int EOF = 0;
  int IntegerLiteral = 116;
  int DecimalLiteral = 117;
  int DoubleLiteral = 118;
  int StringLiteral = 119;
  int DUMMYLABEL = 120;
  int skip_ = 121;
  int Minus = 122;
  int Plus = 123;
  int External = 124;
  int Ascending = 125;
  int Descending = 126;
  int Greatest = 127;
  int Least = 128;
  int Prefix = 129;
  int LocalPart = 130;
  int Nmstart = 131;
  int Nmchar = 132;
  int PredefinedEntityRef = 133;
  int EscapeQuot = 134;
  int EscapeApos = 135;
  int HexDigits = 136;
  int Lbrace = 137;
  int LbraceExprEnclosure = 138;
  int Rbrace = 139;
  int RbraceErrorInContent = 140;
  int LCurlyBraceEscape = 141;
  int RCurlyBraceEscape = 142;
  int Amp = 143;
  int LessThanOpOrTagO = 144;
  int StartTagOpen = 145;
  int AttrLTCharError = 146;
  int StartTagClose = 147;
  int OpenQuot = 148;
  int CloseQuot = 149;
  int OpenApos = 150;
  int CloseApos = 151;
  int ExtensionContentChar = 152;
  int ElementContentChar = 153;
  int QuotAttrContentChar = 154;
  int AposAttrContentChar = 155;
  int CommentContentChar = 156;
  int EmptyTagClose = 157;
  int EndTagOpen = 158;
  int EndTagClose = 159;
  int ValueIndicator = 160;
  int PragmaOpen = 161;
  int PragmaClose = 162;
  int XMLCommentDoubleDashError = 163;
  int CommentContentCharDash = 164;
  int ProcessingInstructionStart = 165;
  int ProcessingInstructionStartForElementContent = 166;
  int ProcessingInstructionEnd = 167;
  int PIContentChar = 168;
  int CDataSectionChar = 169;
  int CdataSectionStart = 170;
  int CdataSectionStartForElementContent = 171;
  int CdataSectionEnd = 172;
  int XmlCommentStart = 173;
  int XmlCommentStartForElementContent = 174;
  int XmlCommentEnd = 175;
  int Comment = 176;
  int CommentStart = 177;
  int CommentContent = 178;
  int CommentEnd = 179;
  int Slash = 180;
  int SlashSlash = 181;
  int PITargetError = 182;
  int PITarget = 183;
  int CharRef = 184;
  int QNameToken = 185;
  int QNameForPragma = 186;
  int TagQName = 187;
  int EndTagQName = 188;
  int NCNameTok = 189;
  int NCNameColonStar = 190;
  int StarColonNCName = 191;
  int S = 192;
  int SForPragma = 193;
  int SForPI = 194;
  int Char = 195;
  int Digits = 196;
  int CommentContents = 197;
  int WhitespaceChar = 198;
  int Letter = 199;
  int BaseChar = 200;
  int Ideographic = 201;
  int CombiningChar = 202;
  int Digit = 203;
  int Extender = 204;
  int NotNumber = 205;

  int DEFAULT = 0;
  int FTPOSFILTER = 1;
  int FTMATCHOPTION = 2;
  int PROLOG_SPECIAL = 3;
  int PROLOG_NCNAME = 4;
  int DECLAREORDERING = 5;
  int PROLOG = 6;
  int OPERAND = 7;
  int OPERATOR = 8;
  int KINDTEST = 9;
  int NAMESPACEDECL = 10;
  int SINGLETYPE = 11;
  int ITEMTYPE = 12;
  int NAMESPACEKEYWORD = 13;
  int VARNAME = 14;
  int OCCURRENCEINDICATOR = 15;
  int CLOSEKINDTEST = 16;
  int XQUERYVERSION = 17;
  int PRAGMA = 18;
  int OPTION = 19;
  int URITOOPERATOR = 20;
  int ELEMENT_CONTENT = 21;
  int QUOT_ATTRIBUTE_CONTENT = 22;
  int APOS_ATTRIBUTE_CONTENT = 23;
  int START_TAG = 24;
  int PRAGMACONTENTS = 25;
  int XML_COMMENT = 26;
  int END_TAG = 27;
  int PRAGMACONTENTSSPACEDIVIDER = 28;
  int PROCESSING_INSTRUCTION = 29;
  int PROCESSING_INSTRUCTION_CONTENT = 30;
  int CDATA_SECTION = 31;
  int EXPR_COMMENT = 32;

  String[] tokenImage = {
    "<EOF>",
    "\"%%%\"",
    "\"xquery\"",
    "\"version\"",
    "\"encoding\"",
    "\"module\"",
    "\"namespace\"",
    "\"=\"",
    "\";\"",
    "\"declare\"",
    "\"boundary-space\"",
    "\"preserve\"",
    "\"strip\"",
    "\"default\"",
    "\"element\"",
    "\"function\"",
    "\"option\"",
    "\"ordering\"",
    "\"ordered\"",
    "\"unordered\"",
    "\"order\"",
    "\"empty\"",
    "\"copy-namespaces\"",
    "\",\"",
    "\"no-preserve\"",
    "\"inherit\"",
    "\"no-inherit\"",
    "\"collation\"",
    "\"base-uri\"",
    "\"import\"",
    "\"schema\"",
    "\"at\"",
    "\"variable\"",
    "\"$\"",
    "\":=\"",
    "\"construction\"",
    "\"(\"",
    "\")\"",
    "\"as\"",
    "\"return\"",
    "\"for\"",
    "\"in\"",
    "\"let\"",
    "\"where\"",
    "\"by\"",
    "\"stable\"",
    "\"some\"",
    "\"every\"",
    "\"satisfies\"",
    "\"typeswitch\"",
    "\"case\"",
    "\"if\"",
    "\"then\"",
    "\"else\"",
    "\"or\"",
    "\"and\"",
    "\"to\"",
    "\"*\"",
    "\"div\"",
    "\"idiv\"",
    "\"mod\"",
    "\"union\"",
    "\"|\"",
    "\"intersect\"",
    "\"except\"",
    "\"instance\"",
    "\"of\"",
    "\"treat\"",
    "\"castable\"",
    "\"cast\"",
    "\"!=\"",
    "\"<=\"",
    "\">\"",
    "\">=\"",
    "\"eq\"",
    "\"ne\"",
    "\"lt\"",
    "\"le\"",
    "\"gt\"",
    "\"ge\"",
    "\"is\"",
    "\"<<\"",
    "\">>\"",
    "\"validate\"",
    "\"lax\"",
    "\"strict\"",
    "\"child\"",
    "\"::\"",
    "\"descendant\"",
    "\"attribute\"",
    "\"self\"",
    "\"descendant-or-self\"",
    "\"following-sibling\"",
    "\"following\"",
    "\"@\"",
    "\"parent\"",
    "\"ancestor\"",
    "\"preceding-sibling\"",
    "\"preceding\"",
    "\"ancestor-or-self\"",
    "\"..\"",
    "\"[\"",
    "\"]\"",
    "\".\"",
    "\"document\"",
    "\"text\"",
    "\"comment\"",
    "\"processing-instruction\"",
    "\"?\"",
    "\"empty-sequence\"",
    "\"item\"",
    "\"node\"",
    "\"document-node\"",
    "\"schema-attribute\"",
    "\"schema-element\"",
    "\"type\"",
    "<IntegerLiteral>",
    "<DecimalLiteral>",
    "<DoubleLiteral>",
    "<StringLiteral>",
    "<DUMMYLABEL>",
    "<skip_>",
    "\"-\"",
    "\"+\"",
    "\"external\"",
    "\"ascending\"",
    "\"descending\"",
    "\"greatest\"",
    "\"least\"",
    "<Prefix>",
    "<LocalPart>",
    "<Nmstart>",
    "<Nmchar>",
    "<PredefinedEntityRef>",
    "\"\\\"\\\"\"",
    "\"\\\'\\\'\"",
    "<HexDigits>",
    "\"{\"",
    "\"{\"",
    "\"}\"",
    "\"}\"",
    "\"{{\"",
    "\"}}\"",
    "\"&\"",
    "\"<\"",
    "\"<\"",
    "\"<\"",
    "\">\"",
    "\"\\\"\"",
    "\"\\\"\"",
    "\"\\\'\"",
    "\"\\\'\"",
    "<ExtensionContentChar>",
    "<ElementContentChar>",
    "<QuotAttrContentChar>",
    "<AposAttrContentChar>",
    "<CommentContentChar>",
    "\"/>\"",
    "\"</\"",
    "\">\"",
    "\"=\"",
    "\"(#\"",
    "\"#)\"",
    "<XMLCommentDoubleDashError>",
    "<CommentContentCharDash>",
    "\"<?\"",
    "\"<?\"",
    "\"?>\"",
    "<PIContentChar>",
    "<CDataSectionChar>",
    "\"<![CDATA[\"",
    "\"<![CDATA[\"",
    "<CdataSectionEnd>",
    "\"<!--\"",
    "\"<!--\"",
    "\"-->\"",
    "<Comment>",
    "\"(:\"",
    "<CommentContent>",
    "\":)\"",
    "\"/\"",
    "\"//\"",
    "<PITargetError>",
    "<PITarget>",
    "<CharRef>",
    "<QNameToken>",
    "<QNameForPragma>",
    "<TagQName>",
    "<EndTagQName>",
    "<NCNameTok>",
    "<NCNameColonStar>",
    "<StarColonNCName>",
    "<S>",
    "<SForPragma>",
    "<SForPI>",
    "<Char>",
    "<Digits>",
    "<CommentContents>",
    "<WhitespaceChar>",
    "<Letter>",
    "<BaseChar>",
    "<Ideographic>",
    "<CombiningChar>",
    "<Digit>",
    "<Extender>",
    "<NotNumber>",
  };

}
