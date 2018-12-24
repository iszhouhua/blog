package com.iszhouhua.blog;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;

/**
 * MyBatis-Plus代码生成器
 * @author ZhouHua
 */
@Slf4j
public class CodeGenerator {
    /**
     * 是否使用包含表名的方式生成代码，false表示使用排除表名的方式
     */
    private final Boolean isInclude = true;

    /**
     * 需要包含的表名或需要排除的表名（支持正则）
     */
    private final String[] tableName = {".*?"};

    /**
     * 数据库表名前缀
     */
    private final String tablePrefix = "blog_";

    /**
     * 逻辑删除字段
     */
    private final String logicDelete = "is_deleted";

    /**
     * 是否覆盖已有文件
     */
    private final Boolean fileOverride = true;

    /**
     * 作者名
     */
    private final String author = "ZhouHua";

    /**
     * 数据源配置
     */
    private final String driverName="com.mysql.jdbc.Driver";
    private final String url="jdbc:mysql://localhost:3306/blog?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8";
    private final String username="root";
    private final String password="123456";

    /**
     * 父包名
     */
    private final String parent = "com.iszhouhua.blog";

    public static void main(String[] args) {
        CodeGenerator generator=new CodeGenerator();
        //生成代码
        generator.generateCode();
    }

    private void generateCode(){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(System.getProperty("user.dir")+ "/src/main/java");
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setMapperName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
//        gc.setSwagger2(true);
        gc.setFileOverride(fileOverride);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(driverName);
        dsc.setUsername(username);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parent);
        mpg.setPackageInfo(pc);

        // 自定义配置
        /*InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return System.getProperty("user.dir")+ "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);*/
        //不生成xml文件
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix(tablePrefix);
        strategy.setEntityLombokModel(true);
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.entityTableFieldAnnotationEnable(true);
        strategy.setTablePrefix(tablePrefix);
        strategy.setLogicDeleteFieldName(logicDelete);
        if(isInclude) { strategy.setInclude(tableName); }
        else { strategy.setExclude(tableName); }
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
