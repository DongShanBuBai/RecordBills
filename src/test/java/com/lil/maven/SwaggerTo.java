package com.lil.maven;


import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.nio.file.Paths;

/**
 * Unit test for simple RecordBillsApplication.
 */
@RunWith(SpringRunner.class) //测试类要使用注入的类
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) //用于单元测试
public class SwaggerTo
{

    /**
     * 生成Markdown格式文档
     * @throws Exception
     */
    @Test
     public void generateMarkdownDocs() throws Exception {
         //    输出Markdown格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
        .withMarkupLanguage(MarkupLanguage.MARKDOWN)    //输出格式：ASCIIDOC，MARKDOWN，CONFLUENCE_MARKUP
        .withOutputLanguage(Language.ZH)                //语言类型：中文（ZH） 默认英文（EN）
        .withPathsGroupedBy(GroupBy.TAGS)               //Api排序规则
        .withGeneratedExamples()
        .withoutInlineSchema()
        .build();
        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs?group=user"))  //url，注意端口号与分组.withConfig(config)
        .withConfig(config)
        .build()
        .toFolder(Paths.get("src/docs/markdown"));                //生成文件的存放路径，生成多个文件
//        .toFile(Paths.get("src/docs/markdown/generated/all"));                 //生成文件的存放路径，汇总为一个文件
    }
}
