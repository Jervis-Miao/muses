/*
Copyright All rights reserved.
 */

package com.utils.functional;

import java.io.IOException;
import java.sql.SQLException;

import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;

import com.muses.common.orm.generator.GeneratorLauncher;

/**
 * @author Jervis
 * @date 2018/11/8.
 */
public class Generator {
	public static void main(String[] args) throws InterruptedException, SQLException, InvalidConfigurationException,
			XMLParserException, IOException {
		GeneratorLauncher
				.generate("D:\\workspace\\muses\\demo-web\\src\\main\\resources\\mybatis\\generatorConfig.xml");
	}
}
