package org.coder.alpha.query.free.result;

import java.sql.SQLException;

import javax.persistence.PersistenceException;

import org.coder.alpha.query.free.RecordFilter;
import org.coder.alpha.query.free.mapper.MetadataMapper;
import org.eclipse.persistence.queries.ScrollableCursor;

/**
 * The list holiding the <code>ResultSet</code> so as to get next record only
 * when the get(i) is called.
 * 
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class FetchIterator<E> extends CloseableIterator<E> {

    /**
     * <pre>
     *    コンストラクタ .
     * </pre>
     * 
     * @param cursor
     *            ScrollableCursor
     * @param handler
     *            MetadataMapper
     * @param filter
     *            RecordFilter
     */
    public FetchIterator(ScrollableCursor cursor, MetadataMapper handler,
            RecordFilter filter) {
       this.cursor = cursor;
       this.filter = filter;
       this.handler = handler;
    }
    /**
     * <pre>
     *   ■説明
     *      ScrollableCursor .
     * </pre>
     */
    private final ScrollableCursor cursor;

    /**
     * <pre>
     *   ■説明
     *      MetadataMapper .
     * </pre>
     */
    private final MetadataMapper handler;

    /**
     * <pre>
     *   ■説明
     *      RecordFilter .
     * </pre>
     */
    private final RecordFilter filter;

    /**
     * <pre>
     *    次の要素があるか判定 .
     * </pre>
     * 
     * @return 次の要素があるか
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        try {
            return cursor.getResultSet().next();
        } catch (SQLException t) {
            throw new PersistenceException(t);
        } 
    }

    /**
     * <pre>
     *    次の要素を取得する .
     * </pre>
     * 
     * @return 次の要素
     * @see java.util.Iterator#next()
     */
    @SuppressWarnings("unchecked")
    @Override
    public E next() {
        try {
            E record = (E) handler.getRecord(cursor.getResultSet());
            return filter != null ? filter.edit(record) : record;
        } catch (SQLException t) {
            throw new PersistenceException(t);
        } 
    }

    /**
     * <pre>
     *    削除する .
     * </pre>
     * 
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

	/**
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception {
		cursor.close();		
	}
}
