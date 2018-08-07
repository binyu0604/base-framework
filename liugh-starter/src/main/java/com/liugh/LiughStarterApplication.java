package com.liugh;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.sql.DataSource;

@Slf4j
//@EnableAutoConfiguration
@SpringBootApplication
@EnableCaching
//@ComponentScan({
//		"com.nddl.tax"
//})
//@ContextConfiguration(locations = { "classpath*:spring-quartz-test.xml", "classpath*:spring-quartz-test.xml" ,"classpath*:spring-quartz-test.xml" })
//@ImportResource({"classpath*:application*.yml"})
public class LiughStarterApplication implements CommandLineRunner {

	@Autowired
	DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(LiughStarterApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		//应用起来后,先尝试关闭db连接,避免一些冷门应用,无人访问占用连接资源，同时也测试db连接关闭是否正常

		try {
			dataSource.getConnection().close();
			log.info("db连接正常关闭");
		} catch (Exception e) {
			log.error("db连接关闭失败:", e);
		}
	}
}
