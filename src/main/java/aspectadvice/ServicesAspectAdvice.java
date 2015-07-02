package aspectadvice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ServicesAspectAdvice
{

	public ServicesAspectAdvice()
	{
		// TODO Auto-generated constructor stub
	}

	// @Before(value = "execution(* service.impl..*.*(..))")
	public void doBefore(JoinPoint jp)
	{
		System.out.println("===========进入before advice============ \n");

	}

	@Around(value = "execution(* service.impl..*.*(..))")
	public Object doAround(ProceedingJoinPoint jp)
	{  
//		System.out.println("方法环绕start....." + jp.toString());
		Object o = null;
		try
		{
			System.out.println(jp.toString());
			o = jp.proceed();
		}
		catch (Throwable e)
		{
			System.out.println("throw around exception!!");
			e.printStackTrace();
		}
//		System.out.println("方法环绕end....." + jp.toString());

		return o;
	}

	/*
	 * AfterThrowing 与 Around 存在冲突(或者说重叠)
	 * Around当中就能catch异常，所以也不需要使用AfterThrowing进行栏截
	 * 去除Around后，AfterThrowing就能工作了
	 */
	// @AfterThrowing(value = "execution(* service.impl..*.*(..))", throwing =
	// "e")
	public void doThrow(JoinPoint jp, Throwable e)
	{
		System.out.println("throw exception!!");
	}

}
