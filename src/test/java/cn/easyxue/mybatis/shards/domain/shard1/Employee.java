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

import cn.easyxue.mybatis.shards.domain.shard0.Company;
import cn.easyxue.mybatis.shards.domain.shard0.User;

/**
 * 企业员工表.
 *
 * @author MaYichao
 */
public class Employee extends AbstractCompanyEntity {

    /**
     * 企业id
     */
    private String comId;
    /**
     * 用户id
     */
    private String userId;

    public Employee() {
    }

    public Employee(Company company, User user) {
        this.company = company;
        comId = company.getId();
        userId = user.getId();
    }

    /**
     * 企业id
     *
     * @return the comId
     */
    public String getComId() {
        return comId;
    }

    /**
     * 企业id
     *
     * @param comId the comId to set
     */
    public void setComId(String comId) {
        this.comId = comId;
    }

    /**
     * 用户id
     *
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     *
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

}
