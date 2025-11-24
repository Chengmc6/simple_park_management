package com.example.park;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Collections;

/**
 * MyBatis-Plus 代码生成器 (适用于 3.5.x 及以上版本)
 * 作者: 高明(コウメイ)
 */
public class CodeGenerator {

    // 数据库连接配置
    private static final String URL = "jdbc:mysql://localhost:3306/park_management?serverTimezone=UTC" +
            "&characterEncoding=utf8&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // 项目路径和输出目录
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String OUTPUT_DIR = PROJECT_PATH + "/src/main/java";

    public static void main(String[] args) {
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)

                // 全局配置 (GlobalConfig)
                .globalConfig(builder -> {
                    builder.author("高明(コウメイ)") // 设置作者
                           .outputDir(OUTPUT_DIR) // 输出目录
                           .dateType(DateType.TIME_PACK) // 使用 Java 8 时间类型
                           .disableOpenDir(); // 生成后不自动打开目录
                })

                // 包配置 (PackageConfig)
                .packageConfig(builder -> {
                    builder.parent("com.example.park") // 父包名
                           .entity("domain.entity") // Entity 包
                           .mapper("domain.mapper") // Mapper 包
                           .service("domain.service") // Service 包
                           .serviceImpl("domain.service.impl") // ServiceImpl 包
                           .controller("controller") // Controller 包
                           // 指定 XML 文件输出路径
                           .pathInfo(Collections.singletonMap(OutputFile.xml, PROJECT_PATH + "/src/main/resources/mapper/"));
                })

                // 策略配置 (StrategyConfig)
                .strategyConfig(builder -> {
                    builder.addInclude("car", "user") // ⭐ 指定要生成的表 (示例: car, user)
                           .addTablePrefix("t_", "sys_", "park_"); // 去掉表前缀

                    // Entity 策略
                    builder.entityBuilder()
                           .naming(NamingStrategy.underline_to_camel) // 下划线转驼峰
                           .columnNaming(NamingStrategy.underline_to_camel)
                           .enableLombok() // 启用 Lombok
                           .enableTableFieldAnnotation(); // 字段注解，方便查看数据库字段

                    // Mapper 策略
                    builder.mapperBuilder()
                           .superClass(BaseMapper.class) // 继承 BaseMapper
                           .enableBaseResultMap() // 启用 BaseResultMap
                           .enableBaseColumnList(); // 启用 BaseColumnList

                    // Controller 策略
                    builder.controllerBuilder()
                           .enableRestStyle() // 启用 @RestController 风格
                           .enableHyphenStyle(); // URL 中驼峰转连字符

                    // Service 策略
                    builder.serviceBuilder()
                           .formatServiceFileName("%sService") // Service 接口命名
                           .formatServiceImplFileName("%sServiceImpl"); // Service 实现类命名
                })

                // 执行生成
                .execute();
    }
}
