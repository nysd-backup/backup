/**
 * Copyright 2011 the original author
 */
package kosmos.lib.report.test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import kosmos.lib.report.var.DataCommand;
import kosmos.lib.report.var.Metadata;
import kosmos.lib.report.var.RequestTarget;
import kosmos.lib.report.var.VarFileRequest;
import kosmos.lib.report.var.VarFileWriter;
import kosmos.lib.report.var.impl.ExcelPreferenceVarWriterImpl;
import kosmos.lib.report.var.impl.VarFileServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class CsvWriterTest {
	
	@Test
	public void service() throws IOException{

		Metadata v1 = new Metadata();
		v1.setName("value1");
		v1.setZeroEscape(true);
		
		Metadata v2 = new Metadata();
		v2.setName("value2");
		
		Metadata v3 = new Metadata();
		v3.setName("value3");
		v3.setFormat("yyyyMMdd");
		
		VarFileWriter writer = new ExcelPreferenceVarWriterImpl(new File("test4.csv"),"MS932");
		
		VarFileRequest request = new VarFileRequest();
		request.setOpendWriter(writer);
		RequestTarget target = new RequestTarget();
		target.setCommand(new DataCommand<Data>() {
			
			public String[] getTitle(){
				return new String[]{"タイトル","タイトル","タイトル3"};
			}
			
			@Override
			public List<Data> getResultList() {
				return Arrays.asList(new Data(),new Data(), new Data());
			}
			@Override
			public void close() {
				// TODO Auto-generated method stub	
			}
		});
		target.setIncludes(v1,v2,v3);
		request.setTargets(target);
		new VarFileServiceImpl().create(request);
		writer.close();
	}
	
	@Test
	public void output() throws IOException{

		Metadata v1 = new Metadata();
		v1.setName("value1");
		v1.setZeroEscape(true);
		
		Metadata v2 = new Metadata();
		v2.setName("value2");
		
		Metadata v3 = new Metadata();
		v3.setName("value3");
		v3.setFormat("yyyyMMdd");
		
		VarFileWriter writer = new ExcelPreferenceVarWriterImpl(new File("test.csv"),"MS932");
		writer.write("タイトル","タイトル","タイトル");
		writer.write(new Data(), v1,v2,v3);
		writer.write(new Data(), v1,v2,v3);
		writer.write(new Data(), v1,v2,v3);
		
		writer.close();
	}
	

	@Test
	public void output2() throws IOException{
		
		Metadata v1 = new Metadata();
		v1.setName("value1");
		
		Metadata v3 = new Metadata();
		v3.setName("value3");
		v3.setFormat("yyyyMMdd");
		
		VarFileWriter writer = new ExcelPreferenceVarWriterImpl(new File("test2.csv"),"utf-8");
		writer.write(new Data(), v1,v3);
		writer.close();
	}
	
	@Test
	public void output3() throws IOException{
	
		VarFileWriter writer = new ExcelPreferenceVarWriterImpl(new File("test3.csv"),"utf-8");
		writer.write(new Data());
		writer.close();
	}


}
