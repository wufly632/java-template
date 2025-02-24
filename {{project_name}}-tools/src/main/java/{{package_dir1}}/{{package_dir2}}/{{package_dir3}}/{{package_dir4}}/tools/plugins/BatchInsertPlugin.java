package com.coraool.bingoplus.dgug.dashboard.tools.plugins;

import com.google.common.collect.Lists;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.AbstractXmlGenerator;

import java.util.List;

public class BatchInsertPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {

        AbstractXmlGenerator xmlGenerator = new AdditionalXMLMapperGenerator();

        xmlGenerator.setContext(introspectedTable.getContext());
        xmlGenerator.setIntrospectedTable(introspectedTable);

        Document document = xmlGenerator.getDocument();

        List<GeneratedXmlFile> answer = Lists.newArrayListWithCapacity(1);

        if (document != null) {
            GeneratedXmlFile gxf = new GeneratedXmlFile(document, introspectedTable.getMyBatis3XmlMapperFileName(),
                    introspectedTable.getMyBatis3XmlMapperPackage(),
                    context.getSqlMapGeneratorConfiguration().getTargetProject(), true, context.getXmlFormatter());

            answer.add(gxf);
        }

        return answer;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        return true;
    }
}
