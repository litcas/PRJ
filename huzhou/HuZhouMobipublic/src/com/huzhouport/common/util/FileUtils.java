package com.huzhouport.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils {

	private String SDPATH;
	public String getSDPATH(){
		return SDPATH;
	}
	public FileUtils(){
		SDPATH=Environment.getExternalStorageDirectory()+"/";
	}
	public File creatSDFile(String fileName) throws IOException{
		File file=new File(SDPATH+fileName);
		file.createNewFile();
		return file;
	}
	public File creatSDDir(String dirName){
		File dir=new File(SDPATH+dirName);
		dir.mkdir();
		return dir;
	}
	public boolean isFileExist(String fileName){
		File file=new File(SDPATH+fileName);
		return file.exists();
	}
	public File writeSDFrom(String path,String fileName,InputStream input){
		File file=null;
		OutputStream output=null;
		try {
			creatSDDir(path);
			file=creatSDFile(path+fileName);
			output=new FileOutputStream(file);
			int len = 0;
			byte buffer[]= new byte[4*1024];
			while((len=input.read(buffer))!=-1)
			{
				output.write(buffer,0,len);
			}
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try{
				output.close();
			}
			catch(Exception e){
				e.printStackTrace();	
			}
		}
		return file;
	}
	
}
