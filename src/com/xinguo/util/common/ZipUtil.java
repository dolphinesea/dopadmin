package com.xinguo.util.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.xinguo.dop.entity.CallTicket;

public class ZipUtil {
	/**
	 * 压缩文件
	 * 
	 * @param srcfile
	 *            File[] 需要压缩的文件列表
	 * @param zipfile
	 *            File 压缩后的文件
	 */
	public static void ZipFiles(Map<String,InputStream> srcfile, OutputStream o) {
		byte[] buf = new byte[1024];
		String url = ZipUtil.class.getClassLoader().getResource("/").getPath()+"recordcfg/conmons";
		System.out.println(url);
		try {
			// Create the ZIP file
			ZipOutputStream out = new ZipOutputStream(o);
			File f = new File(url);
			zip(out, f, "conmons");
			// Compress the files
			for (String key: srcfile.keySet()) {
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(key));
				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = srcfile.get(key).read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				out.closeEntry();
				srcfile.get(key).close();
            }
			out.close();
			System.out.println("压缩完成.");
		} catch (IOException e) {
			logger.debug("zip wav file debug :"+e.getMessage());
		} catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}

	
	/**
	 * 压缩文件
	 * 
	 * @param srcfile
	 *            File[] 需要压缩的文件列表
	 * @param zipfile
	 *            File 压缩后的文件
	 */
	public static void ZipFiles(java.io.File[] srcfile, java.io.File zipfile) {
		byte[] buf = new byte[1024];
		try {
			// Create the ZIP file
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipfile));
			// Compress the files
			for (int i = 0; i < srcfile.length; i++) {
				FileInputStream in = new FileInputStream(srcfile[i]);
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(srcfile[i].getName()));
				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				out.closeEntry();
				in.close();
			}
			// Complete the ZIP file
			out.close();
			System.out.println("压缩完成.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void ZipFiles(String srcfile, OutputStream o) {

		byte[] buf = new byte[1024];
		try {

			String path = SystemConfig.recordFilePath + "/";
			// Create the ZIP file
			ZipOutputStream out = new ZipOutputStream(o);
			// Compress the files
			File aFile = null;

			String[] arr = srcfile.split(",");
			for (int j = 0; j < arr.length; j++) {
				if (arr[j].equals("")||arr[j].equals("undefined")){
					continue;
				}
				String saveFile = path + arr[j].substring(4, 6) + "/" + arr[j];
				System.out.println(saveFile);
				aFile = new File(saveFile);
				if (!aFile.exists()) {
					continue;
				}
				FileInputStream in = new FileInputStream(aFile);
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(aFile.getName()));
				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				out.closeEntry();
				in.close();
			}

			// Complete the ZIP file
			out.close();
			System.out.println("压缩完成.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void ZipFiles(List<CallTicket> srcfile, OutputStream o) {
		byte[] buf = new byte[1024];
		try {

			String path = SystemConfig.recordFilePath + "/";
			// Create the ZIP file
			ZipOutputStream out = new ZipOutputStream(o);
			// Compress the files
			File aFile = null;
			for (int i = 0; i < srcfile.size(); i++) {
				String[] arr = srcfile.get(i).getSavefilename().split(",");
				for (int j = 0; j < arr.length; j++) {
					String saveFile = path + arr[j].substring(4, 6) + "/"
							+ arr[j];
					System.out.println(saveFile);
					aFile = new File(saveFile);
					if (!aFile.exists()) {
						continue;
					}
					FileInputStream in = new FileInputStream(aFile);
					// Add ZIP entry to output stream.
					out.putNextEntry(new ZipEntry(aFile.getName()));
					// Transfer bytes from the file to the ZIP file
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					// Complete the entry
					out.closeEntry();
					in.close();
				}
			}
			// Complete the ZIP file
			out.close();
			System.out.println("压缩完成.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压缩
	 * 
	 * @param zipfile
	 *            File 需要解压缩的文件
	 * @param descDir
	 *            String 解压后的目标目录
	 */
	public static void UnZipFiles(java.io.File zipfile, String descDir) {
		try {
			// Open the ZIP file
			ZipFile zf = new ZipFile(zipfile);
			for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
				// Get the entry name
				ZipEntry entry = ((ZipEntry) entries.nextElement());
				String zipEntryName = entry.getName();
				InputStream in = zf.getInputStream(entry);
				// System.out.println(zipEntryName);
				OutputStream out = new FileOutputStream(descDir + zipEntryName);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				// Close the file and stream
				in.close();
				out.close();
				System.out.println("解压缩完成.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		File dirFile = new File(
				"D:\\RioYang\\XG\\Project\\Java\\DOP-ADMIN\\dop-C++\\record");
		// 如果dir对应的文件不存在，或者不是一个目录，则退出

		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();

		ZipFiles(files, new File("d:\\aa.zip"));
	}
	/**
	 * compress directory.
	 * @param out
	 * @param f
	 * @param base
	 * @throws Exception
	 */
    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
           File[] fl = f.listFiles();
           out.putNextEntry(new ZipEntry(base + "/"));
           base = base.length() == 0 ? "" : base + "/";
           for (int i = 0; i < fl.length; i++) {
           zip(out, fl[i], base + fl[i].getName());
         }
        }else {
           out.putNextEntry( new ZipEntry(base));
           FileInputStream in = new FileInputStream(f);
           int b;
           System.out.println(base);
           while ( (b = in.read()) != -1) {
            out.write(b);
         }
         in.close();
       }
    }

	private static Logger logger = Logger.getLogger(ZipUtil.class);
}
