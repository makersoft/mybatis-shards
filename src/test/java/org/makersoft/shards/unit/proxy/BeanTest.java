package org.makersoft.shards.unit.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;

import org.junit.Test;

/**
 * 
 */
public class BeanTest {
	
	@Test
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
			TestBean bean = new TestBean();
			bean.setUserName("test");
			return bean;
		}
	}

	class TB {
		private String cid;
		private TestBean bean;
		LazyLoader lazy = new Lazy();

		public TB() {
			cid = "1245454";
			bean = (TestBean) Enhancer.create(
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
