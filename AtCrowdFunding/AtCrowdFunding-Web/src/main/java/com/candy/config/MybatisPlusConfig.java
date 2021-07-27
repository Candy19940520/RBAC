package com.candy.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Candy
 * @create 2021-04-25 16:39
 * Mybatis-Plus的自定义配置
 */
@Configuration
@MapperScan(basePackages = {"com.candy.mapper"})
public class MybatisPlusConfig {

    //Mybatis-Plus的全局配置,必须在MybatisSqlSessionFactoryBean引用
    @Bean
    public GlobalConfig globalConfig(){
        GlobalConfig globalConfig = new GlobalConfig();
        //指定全局主键生成策略
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.AUTO);
        globalConfig.setDbConfig(dbConfig);
//        globalConfig.setSqlInjector(new MybatisPlusInjector());
        //公共字段自动填充(例如新增、修改时候的"修改时间字段、新增时间字段")
//        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());
        dbConfig.setLogicDeleteField("isDelete");//全局逻辑删除的实体字段名
        dbConfig.setLogicDeleteValue("-1");//逻辑已删除值(默认为 1)
        dbConfig.setLogicNotDeleteValue("0");//逻辑未删除值(默认为 0)
        return globalConfig;
    }

    /**
     * Mybatis的原生全局配置,必须在MybatisSqlSessionFactoryBean引用
     * 原生Mybatis的配置类叫org.apache.ibatis.session.Configuration,这里使用MybatisConfiguration包装了一下
     * MybatisConfiguration extends Configuration
     */
    @Bean
    public MybatisConfiguration mybatisConfiguration(){
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);//驼峰命名可以不写,默认true
        return mybatisConfiguration;
    }

    /**
     * 分页插件：PaginationInnerInterceptor,
     * 防止全表更新与删除：BlockAttackInnerInterceptor
     * sql性能规范: IllegalSQLInnerInterceptor//一般不使用
     * 乐观锁：OptimisticLockerInnerInterceptor//一般不使用
     *
     * 必须在MybatisSqlSessionFactoryBean引用
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
//        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        return interceptor;
    }


}
