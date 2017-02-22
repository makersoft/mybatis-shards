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

import org.makersoft.shards.ShardedEntity;
import cn.easyxue.mybatis.shards.domain.shard0.Company;
import org.makersoft.shards.ShardId;

/**
 *
 * @author MaYichao
 */
public interface CompanyEntity extends ShardedEntity {

    /**
     * @return the company
     */
    Company getCompany();

    /**
     * 分区与分表信息.
     *
     * @param shardId the shardId to set
     */
    void setShardId(ShardId shardId);

}
