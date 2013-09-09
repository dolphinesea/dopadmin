package com.xinguo.util.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xinguo.dop.entity.Callrecord;
import com.xinguo.dop.entity.query.RecordJdbcQuery;
import com.xinguo.util.common.FileUtil;

public class DeleteFileThread implements Runnable {
	private Logger log = Logger.getLogger(getClass());

	private String path = "";

	private List fileList = new ArrayList();

	/**
	 * @param path
	 *            存储路径
	 * @param list
	 *            Callrecord List
	 */
	public DeleteFileThread(String path, List list) {
		super();
		this.path = path;
		this.fileList = list;
	}

	@Override
	public void run() {

		System.out.println("Thread Start......");
		try {

			if (fileList == null || fileList.size() == 0) {
				return;
			}
			FileUtil fu = new FileUtil();

			List<Callrecord> cList = (List<Callrecord>) fileList;

			String fileName = "";
			String dir = "";
			for (int i = 0; i < cList.size(); i++) {
				dir = path;
				fileName = cList.get(i).getSavefilename();

				if (fileName == null || fileName.length() < 6) {
					continue;
				}
				// dir = dir + "/" + fileName.substring(0, 4) + "/"
				// + fileName.substring(4, 6) + "/";
				dir = dir + "/" + fileName.substring(4, 6) + "/";
				dir = dir + fileName;

				log.info("delete file :" + dir);
				if (fu.deleteFile(dir)) {
					log.info("delete file suceess");
				}
			}
		} catch (Exception e) {
			log.error("delete file is error :"+e.getMessage());
		}
		System.out.println("Thread End......");
	}
}
