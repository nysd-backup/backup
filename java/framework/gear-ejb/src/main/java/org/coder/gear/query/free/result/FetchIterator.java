package org.coder.gear.query.free.result;

import java.sql.SQLException;

import javax.persistence.PersistenceException;

import org.coder.gear.query.free.RecordFilter;
import org.coder.gear.query.free.mapper.MetadataMapper;
import org.eclipse.persistence.queries.ScrollableCursor;

/**
 * The list holiding the <code>ResultSet</code> so as to get next record only
 * when the get(i) is called.
 * 
 * @author yoshida-n
 * @version	1.0
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
     * ScrollableCursor .
     */
    private final ScrollableCursor cursor;

    /**
     * MetadataMapper .
     */
    private final MetadataMapper handler;

    /**
     * RecordFilter .
     */
    private final RecordFilter filter;

    /**
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
     * @see java.util.Iterator#next()
     */
    @SuppressWarnings("unchecked")
    @Override
    public E next() {
        try {
            E record = (E) handler.getRecord(cursor.getResultSet());
            return filter != null ? filter.edit(record) : record;
        } catch (Exception t) {
            throw new PersistenceException(t);
        } 
    }

    /**
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
		if(!cursor.isClosed()){
			cursor.close();		
		}
	}
	
	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Exception{
		close();
	}
}
