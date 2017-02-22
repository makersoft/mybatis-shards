/*
 * Copyright 2017 Makersoft.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.easyxue.mybatis.shards.domain.shard1;

import cn.easyxue.mybatis.shards.domain.AbstractShardEntity;
import cn.easyxue.mybatis.shards.domain.shard0.Company;
import org.makersoft.shards.ShardId;

/**
 * 企业对象基类.
 *
 * @author MaYichao
 */
public abstract class AbstractCompanyEntity extends AbstractShardEntity implements CompanyEntity {

    protected Company company;

    public AbstractCompanyEntity() {
    }

    /**
     * @return the company
     */
    @Override
    public Company getCompany() {
        return company;
    }

    /**
     * 分区与分表信息.
     *
     * @return the shardId
     */
    public ShardId getShardId() {
        return shardId;
    }

    /**
     * 分区与分表信息.
     *
     * @param shardId the shardId to set
     */
    public void setShardId(ShardId shardId) {
        this.shardId = shardId;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
        assert company != null && company.getDbKey() != null : "企业对象不能为空.";
        
        ShardId si = new ShardId(Integer.parseInt(company.getDbKey()));
    }

}
