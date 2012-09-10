package kosmos.tools.generator.entity.template;

import kosmos.tools.generator.entity.model.*;
import kosmos.tools.generator.*;
import org.apache.commons.lang.*;

public class EntityTemplate
{
  protected static String nl;
  public static synchronized EntityTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    EntityTemplate result = new EntityTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/**" + NL + " * Use is subject to license terms." + NL + " */" + NL + "package ";
  protected final String TEXT_2 = "entity;" + NL + "" + NL + "import kosmos.framework.base.AbstractEntity;" + NL + "import kosmos.framework.bean.Pair;" + NL + "import kosmos.framework.core.query.Metadata;" + NL + "import javax.annotation.Generated;" + NL + "import javax.persistence.Column;" + NL + "import javax.persistence.Entity;" + NL + "import javax.persistence.Id;" + NL + "import javax.persistence.Table;" + NL + "import java.util.Map;" + NL + "" + NL + "/**" + NL + " * ";
  protected final String TEXT_3 = "エンティティ" + NL + " *" + NL + " * @author Tool Auto Making" + NL + " */" + NL + "@Generated(\"kosmos.tool.entity-generator\")" + NL + "@Entity" + NL + "@Table(name=\"";
  protected final String TEXT_4 = "\")" + NL + "public class ";
  protected final String TEXT_5 = " extends AbstractEntity {" + NL + "" + NL + "\t/** serialVersionUID */" + NL + "\tprivate static final long serialVersionUID = 1L;" + NL + "\t";
  protected final String TEXT_6 = NL + "\t/** ";
  protected final String TEXT_7 = " */" + NL + "\tpublic static final Metadata<";
  protected final String TEXT_8 = ", ";
  protected final String TEXT_9 = "> ";
  protected final String TEXT_10 = " = new Metadata<";
  protected final String TEXT_11 = ", ";
  protected final String TEXT_12 = ">(\"";
  protected final String TEXT_13 = "\");" + NL + "\t";
  protected final String TEXT_14 = "\t" + NL + "\t";
  protected final String TEXT_15 = NL + "\t/**" + NL + "\t * ";
  protected final String TEXT_16 = NL + "\t */" + NL + "\tprivate ";
  protected final String TEXT_17 = " ";
  protected final String TEXT_18 = " = null;";
  protected final String TEXT_19 = NL;
  protected final String TEXT_20 = NL + "\t/**" + NL + "\t * @param ";
  protected final String TEXT_21 = " the ";
  protected final String TEXT_22 = " to set" + NL + "\t */" + NL + "\tpublic void set";
  protected final String TEXT_23 = "(";
  protected final String TEXT_24 = " ";
  protected final String TEXT_25 = "){" + NL + "\t\tthis.";
  protected final String TEXT_26 = " = ";
  protected final String TEXT_27 = ";" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @return ";
  protected final String TEXT_28 = NL + "\t */";
  protected final String TEXT_29 = NL + "\t@Id";
  protected final String TEXT_30 = NL + "\t@javax.persistence.Version";
  protected final String TEXT_31 = NL + "\t@Column(name=\"";
  protected final String TEXT_32 = "\")\t " + NL + "\tpublic ";
  protected final String TEXT_33 = " get";
  protected final String TEXT_34 = "(){" + NL + "\t\treturn ";
  protected final String TEXT_35 = ";" + NL + "\t}" + NL + "\t";
  protected final String TEXT_36 = NL + NL + "\t/**" + NL + "\t * @see kosmos.framework.sqlclient.api.FastEntity#getVersioningValue()" + NL + "\t */" + NL + "\t@Override" + NL + "\tpublic Pair<String> toVersioningValue() {\t";
  protected final String TEXT_37 = "\t\t\t" + NL + "\t\treturn new Pair<String>(";
  protected final String TEXT_38 = ".name(),";
  protected final String TEXT_39 = ");";
  protected final String TEXT_40 = NL + "\t\treturn null;";
  protected final String TEXT_41 = "\t\t" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @see kosmos.framework.sqlclient.api.FastEntity#getPrimaryKeys()" + NL + "\t */" + NL + "\t@Override" + NL + "\tpublic Map<String, Object> toPrimaryKeys() {" + NL + "\t\tMap<String,Object> map = createMap();";
  protected final String TEXT_42 = NL + "\t\tmap.put(";
  protected final String TEXT_43 = ".name(),";
  protected final String TEXT_44 = ");";
  protected final String TEXT_45 = NL + "\t\treturn map;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @see kosmos.framework.sqlclient.api.FastEntity#getAttributes()" + NL + "\t */" + NL + "\t@Override" + NL + "\tpublic Map<String, Object> toAttributes() {" + NL + "\t\tMap<String,Object> map = createMap();";
  protected final String TEXT_46 = NL + "\t\tmap.put(";
  protected final String TEXT_47 = ".name(),";
  protected final String TEXT_48 = ");";
  protected final String TEXT_49 = NL + "\t\treturn map;" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @see kosmos.framework.base.AbstractBean#clone()" + NL + "\t */" + NL + "\t@Override" + NL + "\tpublic ";
  protected final String TEXT_50 = " clone() {" + NL + "\t\t";
  protected final String TEXT_51 = " clone = new ";
  protected final String TEXT_52 = "();";
  protected final String TEXT_53 = NL + "\t\tclone.";
  protected final String TEXT_54 = " = ";
  protected final String TEXT_55 = ";";
  protected final String TEXT_56 = "\t\t" + NL + "\t\treturn clone;" + NL + "\t}" + NL + "}";
  protected final String TEXT_57 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	Table table = (Table)argument;
	String basePkg = table.basePkgName;
	basePkg = basePkg == null ? "" : basePkg;
	String clsName = StringUtils.capitalize(Utils.toCamelCase(table.physicalName));

    stringBuffer.append(TEXT_1);
    stringBuffer.append(basePkg);
    stringBuffer.append(TEXT_2);
    stringBuffer.append( table.logicalName == null ? table.physicalName : table.logicalName );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(table.physicalName);
    stringBuffer.append(TEXT_4);
    stringBuffer.append( clsName );
    stringBuffer.append(TEXT_5);
    	for (Column column : table.columns) { 
    stringBuffer.append(TEXT_6);
    stringBuffer.append( column.logicalName == null ? column.physicalName : column.logicalName );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( clsName);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(column.typeName.getName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(column.physicalName);
    stringBuffer.append(TEXT_10);
    stringBuffer.append( clsName);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(column.typeName.getName());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(column.physicalName);
    stringBuffer.append(TEXT_13);
    }
    stringBuffer.append(TEXT_14);
    	for (Column column : table.columns) { 
    stringBuffer.append(TEXT_15);
    stringBuffer.append( column.logicalName == null ? column.physicalName : column.logicalName );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( column.typeName.getName());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(Utils.toCamelCase(column.physicalName));
    stringBuffer.append(TEXT_18);
    }
    stringBuffer.append(TEXT_19);
    	for (Column column : table.columns) { 
    stringBuffer.append(TEXT_20);
    stringBuffer.append( Utils.toCamelCase(column.physicalName) );
    stringBuffer.append(TEXT_21);
    stringBuffer.append( Utils.toCamelCase(column.physicalName) );
    stringBuffer.append(TEXT_22);
    stringBuffer.append(StringUtils.capitalize(Utils.toCamelCase(column.physicalName)));
    stringBuffer.append(TEXT_23);
    stringBuffer.append( column.typeName.getName());
    stringBuffer.append(TEXT_24);
    stringBuffer.append( Utils.toCamelCase(column.physicalName) );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( Utils.toCamelCase(column.physicalName) );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( Utils.toCamelCase(column.physicalName) );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( Utils.toCamelCase(column.physicalName) );
    stringBuffer.append(TEXT_28);
    
if (column.primaryKey){

    stringBuffer.append(TEXT_29);
    	} 
if (column.versionManaged){

    stringBuffer.append(TEXT_30);
    	} 
    stringBuffer.append(TEXT_31);
    stringBuffer.append(column.physicalName);
    stringBuffer.append(TEXT_32);
    stringBuffer.append( column.typeName.getName());
    stringBuffer.append(TEXT_33);
    stringBuffer.append(StringUtils.capitalize(Utils.toCamelCase(column.physicalName)));
    stringBuffer.append(TEXT_34);
    stringBuffer.append( Utils.toCamelCase(column.physicalName) );
    stringBuffer.append(TEXT_35);
    }
    stringBuffer.append(TEXT_36);
    	
	boolean version = false;
	for (Column column : table.columns) { 
		if(column.versionManaged){
			version = true;

    stringBuffer.append(TEXT_37);
    stringBuffer.append(column.physicalName);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(Utils.toCamelCase(column.physicalName));
    stringBuffer.append(TEXT_39);
    
			break;
		}
	}
	if(!version){

    stringBuffer.append(TEXT_40);
    
	}

    stringBuffer.append(TEXT_41);
    	for (Column column : table.columns) { 
		if(!column.primaryKey){
			continue;
		}

    stringBuffer.append(TEXT_42);
    stringBuffer.append(column.physicalName);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(Utils.toCamelCase(column.physicalName));
    stringBuffer.append(TEXT_44);
    }
    stringBuffer.append(TEXT_45);
    	for (Column column : table.columns) { 
		if(column.primaryKey){
			continue;
		}

    stringBuffer.append(TEXT_46);
    stringBuffer.append(column.physicalName);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(Utils.toCamelCase(column.physicalName));
    stringBuffer.append(TEXT_48);
    }
    stringBuffer.append(TEXT_49);
    stringBuffer.append( clsName );
    stringBuffer.append(TEXT_50);
    stringBuffer.append( clsName );
    stringBuffer.append(TEXT_51);
    stringBuffer.append( clsName );
    stringBuffer.append(TEXT_52);
    	for (Column column : table.columns) { 
    stringBuffer.append(TEXT_53);
    stringBuffer.append(Utils.toCamelCase(column.physicalName));
    stringBuffer.append(TEXT_54);
    stringBuffer.append(Utils.toCamelCase(column.physicalName));
    stringBuffer.append(TEXT_55);
    }
    stringBuffer.append(TEXT_56);
    stringBuffer.append(TEXT_57);
    return stringBuffer.toString();
  }
}
