/**
 * Use is subject to license terms.
 */
package framework.service.ext.advice;

import org.aspectj.lang.ProceedingJoinPoint;

import framework.logics.log.LogWriter;
import framework.logics.log.LogWriterFactory;
import framework.service.core.transaction.ServiceContext;


/**
 * パフォーマンスログ用インターセプター.
 *
 * @author	yoshida-n
 * @version	2011/02/19 new create
 */
public class PerfInterceptor{

	/** ログ */
	private static final LogWriter LOG = LogWriterFactory.getLog(PerfInterceptor.class);
	
	/**
	 * @param ic 起動情報
	 * @return 結果
	 * @throws Throwable 例外
	 */
	public Object around(ProceedingJoinPoint ic) throws Throwable {
		
		if(LOG.isPerfEnabled()){
			ServiceContext context = ServiceContext.getCurrentInstance();
			if(context == null){
				return ic.proceed();
			}else{
				context.pushCallStack();
		
				long start = System.currentTimeMillis();
				try {
					// 処理実行
					return ic.proceed();
		
				} finally {
					long end = System.currentTimeMillis() - start;
		
					// パフォーマンスログの出力
					StringBuilder builder = new StringBuilder();
					for (int i = 1; i < context.getCallStackLevel(); i++) {
						builder.append("\t");
					}
					LOG.perf(String.format("msec %d:\t%s%s.%s", end, builder.toString(), ic.getSignature().getDeclaringTypeName(), ic.getSignature().getName()));
		
					context.popCallStack();
				}
			}
		}else{
			return ic.proceed();
		}
	}
}
