/**
 * Copyright 2011 the original author
 */
package alpha.utility;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * Utility for files.
 *
 * @author yoshida-n
 * @version 2011/08/31	created.
 */
public final class FileUtils {
	/** The Unix separator character. */
	private static final char UNIX_SEPARATOR = '/';

	/** The Windows separator character. */
	private static final char WINDOWS_SEPARATOR = '\\';

	/**
	 * プライベートコンストラクタ.
	 */
	private FileUtils() {
	}

	/**
	 * OutputStreamWriterを生成する。
	 * 
	 * @param reportFilePath ファイルパス
	 * @param charsetName CharSet
	 * @return OutputStreamWriter
	 */
	public static OutputStreamWriter createOutputStreamWriter(String reportFilePath, String charsetName) {
		try {
			return new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(reportFilePath)), charsetName);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("It is an initialization by unsupported encode mistake.", e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("The file not found by '" + reportFilePath + "'", e);
		}
	}

	/**
	 * <code>InputStream</code>をクローズする
	 * 
	 * @param is <code>InputStream</code>オブジェクト
	 */
	public static void closeQuietly(InputStream is) {
		try {
			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
			throw new RuntimeException("failed to close", e);
		}
	}

	/**
	 * <code>OutputStream</code>をクローズする
	 * 
	 * @param os <code>OutputStream</code>オブジェクト
	 */
	public static void closeQuietly(OutputStream os) {
		try {
			if (os != null) {
				os.close();
			}
		} catch (IOException e) {
			throw new RuntimeException("failed to close", e);
		}
	}

	/**
	 * <code>Reader</code>をクローズする
	 * 
	 * @param input <code>Reader</code>オブジェクト
	 */
	public static void closeQuietly(Reader input) {
		try {
			if (input != null) {
				input.close();
			}
		} catch (IOException e) {
			throw new RuntimeException("failed to close", e);
		}
	}

	/**
	 * <code>Writer</code>をクローズする
	 * 
	 * @param output <code>Writer</code>オブジェクト
	 */
	public static void closeQuietly(Writer output) {
		try {
			if (output != null) {
				output.close();
			}
		} catch (IOException e) {
			throw new RuntimeException("failed to close", e);
		}
	}

	/**
	 * 二つのパスを結合する
	 * 
	 * @param path1 １つ目のパス
	 * @param path2 ２つ目のパス
	 * @return 結合したパス
	 */
	public static String join(String path1, String path2) {
		return new File(path1, path2).getPath();
	}

	/**
	 * ファイルパスからファイル名を取得する
	 * <p>
	 * ※Windows、UNIX系の両方のセパレータに対応<br />
	 * Example:
	 * 
	 * <pre>
	 * FileUtils.getFileName(&quot;/temp/abc.txt&quot;)       -&gt;  abc.txt
	 * FileUtils.getFileName(&quot;C:\\temp\\abc.txt&quot;)   -&gt;  abc.txt
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param path ファイルパス
	 * @return ファイル名
	 */
	public static String getFileName(String path) {
		if (path == null) {
			return null;
		}
		int index = indexOfLastSeparator(path);
		return path.substring(index + 1);
	}

	/**
	 * ファイルパスから拡張子を取得する
	 * <p>
	 * ※Windows、UNIX系の両方のセパレータに対応<br />
	 * Example:
	 * 
	 * <pre>
	 * FileUtils.getExtension(&quot;/temp/abc.txt&quot;)     -&gt;  txt
	 * FileUtils.getExtension(&quot;C:\\temp\\abc.txt&quot;) -&gt;  txt
	 * FileUtils.getExtension(&quot;C:\\temp\\abc.TXT&quot;) -&gt;  TXT
	 * FileUtils.getExtension(&quot;C:\\temp\\abc&quot;)     -&gt;  &quot;&quot;
	 * FileUtils.getExtension(null)                -&gt;  null
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param path ファイルパス
	 * @return 拡張子（.は含まない）
	 */
	public static String getExtension(String path) {
		if (path == null) {
			return null;
		}
		int index = indexOfExtension(path);
		if (index == -1) {
			return "";
		} else {
			return path.substring(index + 1);
		}
	}

	/**
	 * 拡張子の位置を取得する
	 * 
	 * @param path ファイルパス
	 * @return 拡張子のインデックス
	 */
	private static int indexOfExtension(String path) {
		if (StringUtils.isEmpty(path)) {
			return -1;
		} else {
			int extensionPos = path.lastIndexOf('.');
			int lastSeparator = indexOfLastSeparator(path);
			if (lastSeparator <= extensionPos) {
				return extensionPos;
			}
			return -1;
		}
	}

	// import by commons FilenameUtils
	/**
	 * Returns the index of the last directory separator character.
	 * <p>
	 * This method will handle a file in either Unix or Windows format. The position of the last forward or backslash is returned.
	 * <p>
	 * The output will be the same irrespective of the machine that the code is running on.
	 * 
	 * @param filename the filename to find the last path separator in, null returns -1
	 * @return the index of the last separator character, or -1 if there is no such character
	 */
	private static int indexOfLastSeparator(String filename) {
		if (filename == null) {
			return -1;
		}
		int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
		int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
		return Math.max(lastUnixPos, lastWindowsPos);
	}

}
