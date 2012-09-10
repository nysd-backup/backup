package kosmos.tools.generator.query.template;

import kosmos.tools.generator.query.model.*;
import org.apache.commons.lang.*;

public class NamedReadQueryTemplate
{
  protected static String nl;
  public static synchronized NamedReadQueryTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    NamedReadQueryTemplate result = new NamedReadQueryTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/**" + NL + " * Use is subject to license terms." + NL + " */" + NL + "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import alpha.framework.sqlclient.free.AbstractNamedReadQuery;" + NL + "" + NL + "/**" + NL + " * Query Entity of ";
  protected final String TEXT_3 = NL + " *" + NL + " * @author Tool Auto Making" + NL + " */" + NL + "@AnonymousQuery(query=\"@/";
  protected final String TEXT_4 = "\",resultClass=";
  protected final String TEXT_5 = "Result.class)" + NL + "@Generated(\"alpha.tool.entity-generator\")" + NL + "public class ";
  protected final String TEXT_6 = " extends AbstractNamedReadQuery {" + NL;
  protected final String TEXT_7 = NL + "\t/**" + NL + "\t * @param ";
  protected final String TEXT_8 = " the ";
  protected final String TEXT_9 = " to set" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_10 = " set";
  protected final String TEXT_11 = "(Object ";
  protected final String TEXT_12 = "){\t\t" + NL + "\t\tsetParameter(\"";
  protected final String TEXT_13 = "\",";
  protected final String TEXT_14 = ");" + NL + "\t\treturn this;" + NL + "\t}";
  protected final String TEXT_15 = "\t\t" + NL + "" + NL + "}";
  protected final String TEXT_16 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	ArgumentsMeta meta = (ArgumentsMeta)argument;
	String packageName = meta.packageName;

    stringBuffer.append(TEXT_1);
    stringBuffer.append(packageName);
    stringBuffer.append(TEXT_2);
    stringBuffer.append( meta.fileName );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(meta.filePath);
    stringBuffer.append(TEXT_4);
    stringBuffer.append( meta.fileName );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( meta.fileName );
    stringBuffer.append(TEXT_6);
    	for (Arguments column : meta.arguments) { 
    stringBuffer.append(TEXT_7);
    stringBuffer.append( column.name );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( column.name );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( meta.fileName );
    stringBuffer.append(TEXT_10);
    stringBuffer.append(StringUtils.capitalize(column.name));
    stringBuffer.append(TEXT_11);
    stringBuffer.append( column.name );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( column.name );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( column.name );
    stringBuffer.append(TEXT_14);
    }
    stringBuffer.append(TEXT_15);
    stringBuffer.append(TEXT_16);
    return stringBuffer.toString();
  }
}
