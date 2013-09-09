package com.xinguo.dop.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.apache.log4j.Logger;

import com.xinguo.util.common.JsonDateValueProcessor;
import com.xinguo.dop.entity.BackupRecord;
import com.xinguo.dop.entity.result;
import com.xinguo.util.common.SystemConfig;

@Controller
@RequestMapping("/backupRecord")
public class BackUpFileController {
	ArrayList<File> fileArray = new ArrayList<File>();
	private Logger logger = Logger.getLogger(BackUpFileController.class);
	
	@RequestMapping("/readBackUpfile")
	public ModelAndView readBackUpfile(){
		logger.info("readBackupfile ......");
		
		ArrayList<File> htmlfile = null;
		List<String> urls = null;
		List<String> dates = null;
		
		ModelAndView mv = new ModelAndView("callrecord/basefile");
		File f = new File(SystemConfig.recordBackupPath);
		
		if(f.exists()){
			htmlfile= SearchFile(f);
			
			if(htmlfile.size() == 0){
				mv.addObject("urls", 0);
				mv.addObject("dates", 0);
				mv.addObject("filecount", 0);
				mv.addObject("errorinfo", "目录中没有备份记录！");
			}
			else{
				urls = ConvertToHttpUrl(htmlfile);
				dates = getDates(htmlfile);
				
				/*List<BackupRecord> brs = new ArrayList<BackupRecord>();
				BackupRecord br = new BackupRecord();
				for(int i = 0; i < htmlfile.size(); i++){
					br.setBackupDate(dates.get(i));
					br.setBackupUrl(urls.get(i));
					brs.add(br);
				}*/
				
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Timestamp.class,
						new JsonDateValueProcessor());
				jsonConfig.registerJsonValueProcessor(Time.class,
						new JsonDateValueProcessor());
				jsonConfig.registerJsonValueProcessor(Date.class,
						new JsonDateValueProcessor());
				JSONArray urlsRecord= JSONArray.fromObject(urls, jsonConfig);
				JSONArray datesRecord= JSONArray.fromObject(dates, jsonConfig);
				
				
				//mv.addObject("BackupRecordlist", objectRecord.toString());
				mv.addObject("urls", urlsRecord.toString());
				mv.addObject("dates", datesRecord.toString());
				mv.addObject("filecount", htmlfile.size());
				
				htmlfile.clear();
				urls.clear();
				dates.clear();
				//brs.clear();
			}

		}
		else{
			mv.addObject("urls", 0);
			mv.addObject("dates", 0);
			mv.addObject("filecount", 0);
			mv.addObject("errorinfo", "备份目录不存在，请检查系统配置文件中是否设置正确或共享目录是否打开和有权限访问！");
		}

		mv.addObject("smalltitle", "备份录音");
		
		
		return mv;
	}
	
	@RequestMapping("/todelbasefile")
	public String todelOperator() {
		return "callrecord/delbasefile";
	}
	
	@RequestMapping("/delbasefile")
	public void delbasefile(HttpServletResponse response,HttpServletRequest request) throws IOException{
		String dirname = request.getParameter("dirname");
		File f = new File(SystemConfig.recordBackupPath);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONArray objectRecord = null;
		result result = new result();
		int i = 0;
		try{
			if(f.exists()){
				File[] files = f.listFiles();
				for(File child : files){
					if(child.isDirectory()){
						if(child.getName().equals(dirname)){
							deldir(child);
							child.delete();
						}
					}
					
				}
			}
			
			result.setok("1");
			result.setfailure("0");
			result.setcause(new String("success".getBytes("utf-8"), "utf-8"));
			objectRecord = JSONArray.fromObject(result, jsonConfig);
		}
		catch(Exception ex){
			logger.error(ex.toString());
			result.setok("0");
			result.setfailure("1");
			result.setcause(new String(ex.toString().getBytes("utf-8"), "utf-8"));
			objectRecord = JSONArray.fromObject(result, jsonConfig);
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(objectRecord.toString());
		}
		finally{
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(objectRecord.toString());
		}

	}
	
	@RequestMapping("/playBackUpRecord")
	public ModelAndView playBackUpRecord(HttpServletResponse response,HttpServletRequest request){
		System.out.println("play");
		InputStream in = null; 
	    PrintWriter pw = null;
	    
		try {
			pw = response.getWriter();
	       in = new FileInputStream(new File("C:\\tmp\\backaudio\\1.wav"));
	       int i = 0;
	       while ((i=in.read()) != -1 )
	       pw.write(i);
		} catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
			pw.close();
		return null;
	}
	
	private void deldir(File f){
		if(f.listFiles().length==0){
			f.delete();
		}
		else{
			File delFile[]=f.listFiles();
			for(int j = 0; j < delFile.length; j++){
				if(delFile[j].isDirectory()){ 
					deldir(delFile[j]);
				}
				
				delFile[j].delete();
			}
		}
	}
	
	private boolean ismatch(File f){
		 String filename = f.getName().toLowerCase();
		 Pattern pattern = Pattern.compile("html$");
		 Matcher matcher = pattern.matcher(filename);
		 if(matcher.find()){
			 return true;
		 }
		 else{
			 return false;
		 }
	}
	
	private ArrayList<File> SearchFile(File f){
		File[] files = f.listFiles();
		
		for(File child : files){
			if(child.isDirectory()){
				SearchFile(child);
			}
			else
			{
				if(ismatch(child)){
					this.fileArray.add(child);
				}
			}
		}
		
		return this.fileArray;
	}
	
	private String SubDateStr(String str){
		String[] subarr = str.split("\\.");
		return subarr[0];
	}
	
	private ArrayList<String> ConvertToHttpUrl(ArrayList<File> htmlfile){
		char firstchar;
		String temp = null;
		String url = null;
		String[] strspilt = null;
		ArrayList<String> urls = new ArrayList<String>(); 
		
		for(File child : htmlfile){
			firstchar = child.getPath().charAt(0);
			if((firstchar > 96 && firstchar < 123) || (firstchar > 64 && firstchar < 91)){
				temp = child.getPath().substring(3);//根据本地磁盘
				strspilt = temp.split("\\\\");
				url = "http://"+ SystemConfig.recordIp +":8080/" + strspilt[0] + "/";
			}
			else{
				temp = child.getPath().substring(2);//根据远程共享目录
				strspilt = temp.split("\\\\");	
				url = "http://" + strspilt[0] + ":8080/"; 
			}
			
			for(int i = 1; i < strspilt.length; i++){
				url += 	strspilt[i];
				if(i != strspilt.length - 1){
					url += "/";
				}
			}				
			urls.add(url);
				
			logger.debug(url);
			logger.debug("----------------------------------------");
				
			for(int i = 0; i < strspilt.length; i++){
				strspilt[i] = null;
			}
		}
		
		return urls;

	}
	
	private ArrayList<String> getDates(ArrayList<File> htmlfile){
		char firstchar;
		String temp = null;
		String[] strspilt = null;
		Date date = null;
		ArrayList<String> dates = new ArrayList<String>();
		//SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//SimpleDateFormat dateTimeFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
		
		try{
			for(File child : htmlfile){
				firstchar = child.getPath().charAt(0);
				if((firstchar > 96 && firstchar < 123) || (firstchar > 64 && firstchar < 91)){
					temp = child.getPath().substring(3);//根据本地磁盘
				}
				else{
					temp = child.getPath().substring(2);//根据远程共享目录	
				}
				strspilt = temp.split("\\\\");
				
				//date = dateTimeFormat1.parse(SubDateStr(strspilt[strspilt.length - 1]));
				dates.add(strspilt[strspilt.length - 2]);
				
				logger.debug(strspilt[strspilt.length - 2]); 
				logger.debug("----------------------------------------");
				
				for(int i = 0; i < strspilt.length; i++){
					strspilt[i] = null;
				}	
			}
		}
		catch(Exception ex){
			logger.error(ex.toString());
		}
		
		return dates;

	}
	
}
