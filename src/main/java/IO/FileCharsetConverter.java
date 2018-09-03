package IO;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class FileCharsetConverter {

	public static void main(String[] args) throws Exception {
		String filePath = "src/main/java/IO/Dom4jControler.java";
		// String sourceCode = "GBK";
		// String aimCode = "UTF-8";
		// convert(filePath,sourceCode,aimCode,new SourceFileFilter());
		UTF8_GBK(filePath, new SourceFileFilter());
		// GBK_UTF8(filePath,new SourceFileFilter());
	}

	public static void UTF8_GBK(String filePath, FilenameFilter filter)
			throws Exception {
		convert(filePath, "UTF-8", "GBK", filter);
	}

	public static void GBK_UTF8(String filePath, FilenameFilter filter)
			throws Exception {
		convert(filePath, "GBK", "UTF-8", filter);
	}

	/**
	 * 把指定文件或目录转换成指定的编码
	 * 
	 * @param fileName
	 *            要转换的文件
	 * @param fromCharsetName
	 *            源文件的编码
	 * @param toCharsetName
	 *            要转换的编码
	 * @throws Exception
	 */
	public static void convert(String fileName, String fromCharsetName,
			String toCharsetName) throws Exception {
		convert(new File(fileName), fromCharsetName, toCharsetName, null);
	}

	/**
	 * 把指定文件或目录转换成指定的编码
	 * 
	 * @param file
	 *            要转换的文件或目录
	 * @param fromCharsetName
	 *            源文件的编码
	 * @param toCharsetName
	 *            要转换的编码
	 * @throws Exception
	 */
	public static void convert(File file, String fromCharsetName,
			String toCharsetName) throws Exception {
		convert(file, fromCharsetName, toCharsetName, null);
	}

	/**
	 * 把指定文件或目录转换成指定的编码
	 * 
	 * @param file
	 *            要转换的文件或目录
	 * @param fromCharsetName
	 *            源文件的编码
	 * @param toCharsetName
	 *            要转换的编码
	 * @param filter
	 *            文件名过滤器
	 * @throws Exception
	 */
	public static void convert(String fileName, String fromCharsetName,
			String toCharsetName, FilenameFilter filter) throws Exception {
		convert(new File(fileName), fromCharsetName, toCharsetName, filter);
	}

	/**
	 * 把指定文件或目录转换成指定的编码
	 * 
	 * @param file
	 *            要转换的文件或目录
	 * @param fromCharsetName
	 *            源文件的编码
	 * @param toCharsetName
	 *            要转换的编码
	 * @param filter
	 *            文件名过滤器
	 * @throws Exception
	 */
	public static void convert(File file, String fromCharsetName,
			String toCharsetName, FilenameFilter filter) throws Exception {
		if (file.isDirectory()) { // 文件夹
			File[] fileList = null;
			if (filter == null) {
				fileList = file.listFiles();
			} else {
				fileList = file.listFiles(filter);
			}
			// 递归
			for (File f : fileList) {
				convert(f, fromCharsetName, toCharsetName, filter);
			}
		} else { // 文件
			if (filter == null
					|| (filter != null && filter.accept(file.getParentFile(),
							file.getName()))) {
				String fileContent = getFileContentFromCharset(file,
						fromCharsetName);
				// 备份
				File bakFile = new File(file.getAbsolutePath() + ".bak");
				saveFile2Charset(bakFile, fromCharsetName, fileContent);
				// 保存转码后的文件
				saveFile2Charset(file, toCharsetName, fileContent);
			}
		}
	}

	/**
	 * 以指定编码方式读取文件，返回文件内容
	 * 
	 * @param file
	 *            要转换的文件
	 * @param fromCharsetName
	 *            源文件的编码
	 * @return
	 * @throws Exception
	 */
	public static String getFileContentFromCharset(File file,
			String fromCharsetName) throws Exception {
		if (!Charset.isSupported(fromCharsetName)) {
			throw new UnsupportedCharsetException(fromCharsetName);
		}
		InputStream inputStream = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(inputStream,
				fromCharsetName);
		char[] chs = new char[(int) file.length()];
		reader.read(chs);
		String str = new String(chs).trim();
		reader.close();
		return str;
	}

	/**
	 * 以指定编码方式写文本文件，存在会覆盖
	 * 
	 * @param file
	 *            要写入的文件
	 * @param toCharsetName
	 *            要转换的编码
	 * @param content
	 *            文件内容
	 * @throws Exception
	 */
	public static void saveFile2Charset(File file, String toCharsetName,
			String content) throws Exception {

		if (!Charset.isSupported(toCharsetName)) {
			throw new UnsupportedCharsetException(toCharsetName);
		}
		// 根据转码后的内容生成新的文件
		OutputStream outputStream = new FileOutputStream(file);
		OutputStreamWriter outWrite = new OutputStreamWriter(outputStream,
				toCharsetName);
		outWrite.write(content);
		outWrite.close();
	}
}

class SourceFileFilter implements FilenameFilter {
	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith("java");
	}

}
