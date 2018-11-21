/*
Copyright All rights reserved.
 */

package com.utils.mybatis;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jervis
 * @date 2018/11/9.
 */
public class GeneratorLauncher {
	private static final Logger	LOGGER	= LoggerFactory.getLogger(GeneratorLauncher.class);

	private GeneratorLauncher() {
	}

	public static void generate(String configFilePath) throws IOException, XMLParserException,
			InvalidConfigurationException, SQLException, InterruptedException {
		ArrayList warnings = new ArrayList();
		boolean overwrite = true;
		File configFile = new File(configFilePath);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate((ProgressCallback) null);
		Iterator var8 = warnings.iterator();

		while (var8.hasNext()) {
			String warning = (String) var8.next();
			LOGGER.warn("warning:{}", warning);
		}

	}
}
