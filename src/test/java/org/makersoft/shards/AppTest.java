package org.makersoft.shards;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.junit.Test;
import org.makersoft.shards.domain.User;
import org.makersoft.shards.unit.proxy.TestBean;

/**
 * Unit test for simple App.
 */
public class AppTest {

	/**
	 * Rigourous Test :-)
	 */
	@Test
	public void testApp() throws Exception {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(User.class);
		enhancer.setCallback(new MethodInterceptor() {
			User user = new User(1l, "guofeng", "password", 1);

			@Override
			public Object intercept(Object obj, Method method, Object[] args,
					MethodProxy proxy) throws Throwable {
				return proxy.invokeSuper(obj, args);
			}
		});
		User user = (User) enhancer.create();

		System.out.println(user.getId());

	}

	public void testBean() {
		TB tb = new TB();
		System.out.println(tb.getCid());
		System.out.println("--");
		System.out.println(tb.getBean().getUserName());
	}

	class Lazy implements LazyLoader {
		public Object loadObject() throws Exception {
			// TODO Auto-generated method stub
			System.out.println("开始延迟加载!");
			User user = new User(1l, "guofeng", "password", 1);
			return user;
		}
	}

	class TB {
		private String cid;
		private TestBean bean;
		LazyLoader lazy = new Lazy();

		public TB() {
			cid = "1245454";
			bean = (TestBean) net.sf.cglib.proxy.Enhancer.create(
					TestBean.class, lazy);
		}

		public TestBean getBean() {
			return bean;
		}

		public void setBean(TestBean bean) {
			this.bean = bean;
		}

		public String getCid() {
			return cid;
		}

		public void setCid(String cid) {
			this.cid = cid;
		}
	}

}
