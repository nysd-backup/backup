/**
 * Copyright 2011 the original author
 */
package kosmos.lib.report.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import kosmos.lib.report.flat.impl.CsvWriterImpl;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class CsvWriterTest {
	
	@Test
	public void output() throws IOException{
		
		ICsvListWriter writer = new CsvListWriter(
				new BufferedWriter(new FileWriterWithEncoding(new File("test.csv"), "utf-8"))
				,CsvPreference.NO_COMMENT_PREFERENCE);		
		
		new CsvWriterImpl().write(writer, new Data(), null);
		writer.close();
	}


}
