package org.makersoft.shards.unit.transaction;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "multiDataSourcesTransactionManager", defaultRollback = false)
@ActiveProfiles("test")
public class MultipleDataSourcesTransactionManagerTests {

	@Autowired
	@Qualifier("jdbcTemplate_1")
	private JdbcTemplate jdbcTemplate_1;
	
	@Autowired
	@Qualifier("jdbcTemplate_2")
	private JdbcTemplate jdbcTemplate_2;
	
//	@Autowired
//	@Qualifier("jdbcTemplate_3")
//	private JdbcTemplate jdbcTemplate_3;
	
	@Test
	@Transactional(rollbackFor={Exception.class,RuntimeException.class})
	@Rollback
	public void testOfferCreationOnMultipleShardsWithTransactionRollback() throws Exception{

//		User user1 = new User(1L,"makersoft","makersoft");
//		User user2 = new User(1L,"guofeng","guofeng");
							
		int affectedrows = jdbcTemplate_1.update("insert into maker_test_user (id,username,password) values (1,'makersoft','makersoft')");
		Assert.assertEquals(affectedrows, 1);
//		jdbcTemplate_1.update("insert into maker_test_user (id,username,password) values (1,'makersoft','makersoft')");
		affectedrows = jdbcTemplate_2.update("insert into maker_test_user (id,username,password) values (1,'makersoft','makersoft')");
//		jdbcTemplate_2.update("insert into maker_test_user (id,username,password) values (1,'makersoft','makersoft')");
		Assert.assertEquals(affectedrows, 1);
//		if(true){
//			throw new RuntimeException();
//		}

	}

//	public void testOfferCreationOnMultipleShardsWithNormallyOfferService() {
//		String selectSqlActionTwo = "com.alibaba.cobar.client.entities.Offer.findByMemberId";
//
//		for (Long mid : memberIds) {
//			Offer parameter = new Offer();
//			parameter.setMemberId(mid);
//			Offer offer = (Offer) getSqlMapClientTemplate().queryForObject(
//					selectSqlActionTwo, parameter);
//			assertNull(offer);
//		}
//
//		((IOfferService) getApplicationContext().getBean("normalOfferService"))
//				.createOffersInBatch(createOffersWithMemberIdsFrom(memberIds));
//
//		for (Long mid : memberIds) {
//			Offer parameter = new Offer();
//			parameter.setMemberId(mid);
//			Offer offer = (Offer) getSqlMapClientTemplate().queryForObject(
//					selectSqlActionTwo, parameter);
//			assertNotNull(offer);
//			assertEquals(mid, offer.getMemberId());
//		}
//	}
//
//	/**
//	 * need data stores that support transaction to test this behavior.
//	 */
//	public void testOfferCreationOnMultipleShardsWithAbnormalOfferService() {
//		String selectSqlActionTwo = "com.alibaba.cobar.client.entities.Offer.findByMemberId";
//
//		for (Long mid : memberIds) {
//			Offer parameter = new Offer();
//			parameter.setMemberId(mid);
//			Offer offer = (Offer) getSqlMapClientTemplate().queryForObject(
//					selectSqlActionTwo, parameter);
//			assertNull(offer);
//		}
//
//		try {
//			Object offerService = getApplicationContext().getBean(
//					"abnormalOfferService");
//			assertTrue(offerService instanceof Proxy);
//			((IOfferService) offerService)
//					.createOffersInBatch(createOffersWithMemberIdsFrom(memberIds));
//			fail();
//		} catch (RuntimeException e) {
//			// pass
//		}
//
//		for (Long mid : memberIds) {
//			Offer parameter = new Offer();
//			parameter.setMemberId(mid);
//			assertNull(getSqlMapClientTemplate().queryForObject(
//					selectSqlActionTwo, parameter));
//		}
//	}
//
//	public List<Offer> createOffersWithMemberIdsFrom(Long[] mids) {
//		List<Offer> offers = new ArrayList<Offer>();
//		for (Long mid : mids) {
//			Offer offer = new Offer();
//			offer.setGmtUpdated(new Date());
//			offer.setMemberId(mid);
//			offer.setSubject("anything");
//			offers.add(offer);
//		}
//		return offers;
//	}
}
