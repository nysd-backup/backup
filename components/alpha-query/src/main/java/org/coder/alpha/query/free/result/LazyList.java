package org.coder.alpha.query.free.result;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
public class LazyList<E> implements List<E> {

    /**
     * <pre>
     *   ■説明
     *      ResultSetIterator .
     * </pre>
     */
    private final ResultSetIterator itr;

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
    public LazyList(ScrollableCursor cursor, MetadataMapper handler,
            RecordFilter filter) {
        this.itr = new ResultSetIterator(cursor, handler, filter);
    }

    /**
     * <pre>
     *    終了処理 .
     * </pre>
     * 
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() {
        itr.close();
    }

    /**
     * <pre>
     *    サイズを取得する .
     * </pre>
     * 
     * @return サイズ
     * @see java.util.List#size()
     */
    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    空かどうか判定する .
     * </pre>
     * 
     * @return 空かどうか
     * @see java.util.List#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    値を含んでいるか判定する .
     * </pre>
     * 
     * @param o
     *            値
     * @return 値を含んでいるか
     * @see java.util.List#contains(java.lang.Object)
     */
    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    イテレータを取得する .
     * </pre>
     * 
     * @return イテレータ
     * @see java.util.List#iterator()
     */
    @Override
    public Iterator<E> iterator() {
        return itr;
    }

    /**
     * <pre>
     *    配列化する .
     * </pre>
     * 
     * @return 配列
     * @see java.util.List#toArray()
     */
    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    配列化する .
     * </pre>
     * 
     * @param <T>
     *            テンプレート
     * @param a
     *            配列の型
     * @return 配列
     * @see java.util.List#toArray(T[])
     */
    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    追加する .
     * </pre>
     * 
     * @param e
     *            要素
     * @return 結果
     * @see java.util.List#add(java.lang.Object)
     */
    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    削除する .
     * </pre>
     * 
     * @param o
     *            削除するオブジェクト
     * @return 結果
     * @see java.util.List#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    すべて含んでいるか判定する .
     * </pre>
     * 
     * @param c
     *            コレクション
     * @return 結果
     * @see java.util.List#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    すべて追加する .
     * </pre>
     * 
     * @param c
     *            コレクション
     * @return 結果
     * @see java.util.List#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    位置を指定して追加する .
     * </pre>
     * 
     * @param index
     *            追加する位置
     * @param c
     *            コレクション
     * @return 結果
     * @see java.util.List#addAll(int, java.util.Collection)
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    すべて削除する .
     * </pre>
     * 
     * @param c
     *            コレクション
     * @return 結果
     * @see java.util.List#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    指定されたコレクション内にない要素をすべて削除する .
     * </pre>
     * 
     * @param c
     *            コレクション
     * @return 結果
     * @see java.util.List#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    クリアする .
     * </pre>
     * 
     * @see java.util.List#clear()
     */
    @Override
    public void clear() {
        itr.close();
    }

    /**
     * <pre>
     *    指定の位置にセットする .
     * </pre>
     * 
     * @param index
     *            位置
     * @param element
     *            要素
     * @return 結果
     * @see java.util.List#set(int, java.lang.Object)
     */
    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    指定の位置に追加する .
     * </pre>
     * 
     * @param index
     *            位置
     * @param element
     *            要素
     * @see java.util.List#add(int, java.lang.Object)
     */
    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    指定の位置の要素を削除する .
     * </pre>
     * 
     * @param index
     *            位置
     * @return 結果
     * @see java.util.List#remove(int)
     */
    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    インデックスを取得 .
     * </pre>
     * 
     * @param o
     *            要素
     * @return インデックス
     * @see java.util.List#indexOf(java.lang.Object)
     */
    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    最終インデックスを取得 .
     * </pre>
     * 
     * @param o
     *            要素
     * @return インデックス
     * @see java.util.List#lastIndexOf(java.lang.Object)
     */
    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    イテレータを取得 .
     * </pre>
     * 
     * @return イテレータ
     * @see java.util.List#listIterator()
     */
    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    イテレータを取得 .
     * </pre>
     * 
     * @param index
     *            インデックス
     * @return イテレータ
     * @see java.util.List#listIterator(int)
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    部分リストを取得 .
     * </pre>
     * 
     * @param fromIndex
     *            開始インデックス
     * @param toIndex
     *            終了インデックス
     * @return 部分リスト
     * @see java.util.List#subList(int, int)
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     *    要素を取得する .
     * </pre>
     * 
     * @param index
     *            インデックス
     * @return 要素
     * @see java.util.List#get(int)
     */
    @Override
    public E get(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * Iterator .
     */
    private class ResultSetIterator implements Iterator<E> {

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
        public ResultSetIterator(ScrollableCursor cursor,
                MetadataMapper handler, RecordFilter filter) {
            this.cursor = cursor;
            this.handler = handler;
            this.filter = filter;
        }

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
                boolean result = cursor.getResultSet().next();
                if (!result) {
                    close();
                }
                return result;
            } catch (SQLException t) {
                close();
                throw new PersistenceException(t);
            } catch (RuntimeException re) {
                close();
                throw re;
            } catch (Error re) {
                close();
                throw re;
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
                close();
                throw new PersistenceException(t);
            } catch (RuntimeException re) {
                close();
                throw re;
            } catch (Error re) {
                close();
                throw re;
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
         * <pre>
         *    クローズ処理 .
         * </pre>
         * 
         */
        public void close() {
            cursor.close();
        }

    }
}
