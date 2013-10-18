package org.coder.mightyguard.register.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;

/**
 * @author yoshida-n
 *
 */
public class TaskOutputSweaper implements Callable<String> {

    /** バッファーサイズ . */
    private static final int BUFFER_SIZE = 1024;

    /** ストリーム . */
    private InputStream in = null;

    /**
     * <pre>
     *    コンストラクタ .
     * </pre>
     * 
     * @param in
     *            ストリーム
     */
    public TaskOutputSweaper(InputStream in) {
        this.in = in;
    }

    /**
     * <pre>
     *    sweapする .
     * </pre>
     * 
     * @return 結果
     * @throws Exception
     *             例外
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public String call() throws Exception {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int size = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((size = in.read(buffer)) > -1) {
                baos.write(buffer, 0, size);
            }
            return new String(baos.toByteArray(), Charset.forName("UTF-8"));
        } finally {
            in.close();
            in = null;
        }
    }

}
