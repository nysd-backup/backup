package framework.service.ext.apis;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CopyOfTestServiceImpl implements CopyOfTestService{

	@Resource
	private SessionContext ctx;
	
	@Override
	public void test() throws TestException{
		//これでサービス生成可能であるためこれで見るようにしよう
		//CopyOfTestService o = ctx.getBusinessObject(CopyOfTestService.class);
		//o.test();
		
		
		ctx.getContextData().put("TEST",100);
		throw new TestException();
	}

}
