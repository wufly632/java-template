package com.coraool.bingoplus.dgug.dashboard.tools.plugins;

import com.google.common.collect.Lists;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.config.GeneratedKey;

import java.util.Iterator;
import java.util.List;

public class BatchInsertElementGenerator extends AbstractXmlElementGenerator {
    private static final String BATCH_INSERT_STATEMENT_ID = "batchInsert";
    private static final int NEW_LINE_CHARACTER_LIMIT = 80;

    BatchInsertElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert");

        answer.addAttribute(new Attribute("id", BATCH_INSERT_STATEMENT_ID));

        answer.addAttribute(
                new Attribute("parameterType", FullyQualifiedJavaType.getNewListInstance().getFullyQualifiedName()));

        context.getCommentGenerator().addComment(answer);

        GeneratedKey gk = introspectedTable.getGeneratedKey().get();
        if (gk != null) {
            IntrospectedColumn introspectedColumn = introspectedTable.getColumn(gk.getColumn()).get();
            if (introspectedColumn != null) {
                if (gk.isJdbcStandard()) {
                    answer.addAttribute(new Attribute("useGeneratedKeys", "true"));
                    answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty()));
                } else {
                    answer.addElement(getSelectKey(introspectedColumn, gk));
                }
            }
        }

        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();

        insertClause.append("insert into ");
        insertClause.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" (");

        List<String> valuesClauses = Lists.newArrayList();
        valuesClauses.add("values ");
        valuesClauses.add("<foreach collection=\"list\" separator=\",\" index=\"index\" item=\"item\">");
        valuesClauses.add("  (");

        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns().iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();
            if (introspectedColumn.isIdentity()) {
                // cannot set values on identity fields
                continue;
            }

            insertClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));

            if (introspectedColumn.getDefaultValue() == null) {
                getValueClauseWhenNoDefaultValue(valuesClause, valuesClauses, iter, introspectedColumn, 1);
            } else {
                getChooseString(valuesClause, valuesClauses, introspectedColumn, iter);
            }

            appendCommaWhenHasNext(insertClause, iter);

            if (insertClause.length() > NEW_LINE_CHARACTER_LIMIT) {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtilities.xmlIndent(insertClause, 1);
            }
        }

        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));

        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());
        valuesClause.setLength(0);
        valuesClause.append("</foreach>");
        valuesClauses.add(valuesClause.toString());

        for (String clause : valuesClauses) {
            answer.addElement(new TextElement(clause));
        }

        if (context.getPlugins().sqlMapInsertElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

    private void getValueClauseWhenNoDefaultValue(StringBuilder valuesClause, List<String> valuesClauses,
                                                  Iterator<IntrospectedColumn> iter, IntrospectedColumn introspectedColumn, int indentLevel) {
        OutputUtilities.xmlIndent(valuesClause, indentLevel);
        valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
        insertItemAfterLeftBrace(valuesClause);
        appendCommaWhenHasNext(valuesClause, iter);
        valuesClauses.add(valuesClause.toString());
        valuesClause.setLength(0);
    }

    private void getChooseString(StringBuilder valuesClause, List<String> valuesClauses,
                                 IntrospectedColumn introspectedColumn, Iterator iter) {
        OutputUtilities.xmlIndent(valuesClause, 1);
        valuesClause.append("<choose>");
        valuesClauses.add(valuesClause.toString());
        valuesClause.setLength(0);

        OutputUtilities.xmlIndent(valuesClause, 2);
        valuesClause.append("<when test=\"item.");
        valuesClause.append(introspectedColumn.getJavaProperty(null));
        valuesClause.append(" != null\" >");
        valuesClauses.add(valuesClause.toString());
        valuesClause.setLength(0);

        getValueClauseWhenNoDefaultValue(valuesClause, valuesClauses, iter, introspectedColumn, 3);

        OutputUtilities.xmlIndent(valuesClause, 2);
        valuesClause.append("</when>");
        valuesClauses.add(valuesClause.toString());
        valuesClause.setLength(0);

        OutputUtilities.xmlIndent(valuesClause, 2);
        valuesClause.append("<otherwise>");
        valuesClauses.add(valuesClause.toString());
        valuesClause.setLength(0);

        OutputUtilities.xmlIndent(valuesClause, 3);
        valuesClause.append(wrapQuotaPair(introspectedColumn.getDefaultValue()));
        appendCommaWhenHasNext(valuesClause, iter);
        valuesClauses.add(valuesClause.toString());
        valuesClause.setLength(0);

        OutputUtilities.xmlIndent(valuesClause, 2);
        valuesClause.append("</otherwise>");
        valuesClauses.add(valuesClause.toString());
        valuesClause.setLength(0);

        OutputUtilities.xmlIndent(valuesClause, 1);
        valuesClause.append("</choose>");
        valuesClauses.add(valuesClause.toString());
        valuesClause.setLength(0);
    }

    private void appendCommaWhenHasNext(StringBuilder insertClause, Iterator<IntrospectedColumn> iter) {
        if (iter.hasNext()) {
            insertClause.append(", ");
        }
    }

    private void insertItemAfterLeftBrace(StringBuilder valuesClause) {
        int index = valuesClause.indexOf("{");
        if (index != -1) {
            valuesClause.insert(index + 1, "item.");
        }
    }

    private String wrapQuotaPair(String defaultValue) {
        return "'" + defaultValue + "'";
    }
}
