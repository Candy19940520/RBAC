package com.candy.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

/**
 * 修改
 * 修改
 * @author Candy
 * @create 2021-05-11 11:04
 */
public class MybatisPlusGenerator {


    @Test
    public void generator(){
        //1.全局配置globalConfig
        GlobalConfig globalConfig = new GlobalConfig();
//        globalConfig.setActiveRecord(true);//是否支持AR模式
        globalConfig.setAuthor("Candy");//设置作者
        globalConfig.setOutputDir("C:\\Users\\61788\\Desktop");//生成路径
        globalConfig.setFileOverride(true);//如果文件存在相同的，是否覆盖
        globalConfig.setOpen(true);//是否打开生成代码的目录
//        globalConfig.setSwagger2(true);//开启Swagger2模式
        globalConfig.setIdType(IdType.AUTO);//设置主键策略
        globalConfig.setServiceName("%sService");//设置生成的service中不存在I
        globalConfig.setDateType(DateType.ONLY_DATE);
        //2.数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUrl("jdbc:mysql://8.140.190.25:3306/mp");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        //3.包名策略配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.candy.generator");//父级包名
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setEntity("bean");
        packageConfig.setXml("mapper");
        //4.数据库表配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude("department_and_permission","permission","department");//指定要生成的表
        strategyConfig.setCapitalMode(true);//是否大写命名
        strategyConfig.setEntityLombokModel(true);
//        strategyConfig.setTablePrefix("");//指定表前缀
//        strategyConfig.setEntityTableFieldAnnotationEnable(true);//是否生成实体时，生成字段注解@TableField("数据库字段")
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略(下划线转驼峰命名)
        strategyConfig.setEntitySerialVersionUID(true);//实体是否生成 serialVersionUID

        //整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.execute();//执行

    }
}
