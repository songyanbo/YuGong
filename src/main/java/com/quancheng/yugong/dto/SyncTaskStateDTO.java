/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.dto;

import static org.elasticsearch.common.settings.Settings.settingsBuilder;

import org.elasticsearch.common.settings.Settings;

import com.quancheng.yugong.repository.SyncTaskStateDao;
import com.quancheng.yugong.repository.entity.SyncTaskDO;
import com.quancheng.yugong.repository.entity.SyncTaskStateDO;

/**
 * @author shimingliu 2017年2月10日 下午3:37:24
 * @version SyncTaskStateDTO.java, v 0.0.1 2017年2月10日 下午3:37:24 shimingliu
 */
public class SyncTaskStateDTO {

    private final SyncTaskStateDao syncTaskStateDao;
    private Integer                id;
    private Integer                taskId;
    private String                 stateSetting;
    private Boolean                isCanceled = Boolean.FALSE;

    public SyncTaskStateDTO(SyncTaskStateDao syncTaskStateDao){
        this.syncTaskStateDao = syncTaskStateDao;
    }

    public synchronized boolean saveOrUpdate(String stateSetting) {
        this.stateSetting = stateSetting;
        SyncTaskStateDO stateDo;
        if (this.id != null) {
            stateDo = syncTaskStateDao.findOne(id);
            stateDo.setStateSetting(stateSetting);
        } else {
            this.isCanceled = false;
            this.isCanceled = false;
            stateDo = new SyncTaskStateDO();
            stateDo.setIsCanceled(this.isCanceled);
            stateDo.setIsCanceled(this.isCanceled);
            stateDo.setStateSetting(stateSetting);
            SyncTaskDO syncTaskDO = new SyncTaskDO();
            syncTaskDO.setId(this.taskId);
            stateDo.setSyncTask(syncTaskDO);
        }
        SyncTaskStateDO savedStatedDo = syncTaskStateDao.save(stateDo);
        this.id = savedStatedDo.getId();
        return this.id != 0;
    }

    public Settings loadConfigSetting() {
        if (this.id != null && this.id != 0) {
            SyncTaskStateDO latestStateDO = syncTaskStateDao.findOne(this.id);
            this.stateSetting = latestStateDO.getStateSetting();
            this.isCanceled = latestStateDO.getIsCanceled();
        }
        return getConfigSetting();
    }

    public SyncTaskStateDTO loadLatestState() {
        if (this.id != null && this.id != 0) {
            SyncTaskStateDO latestStateDO = syncTaskStateDao.findOne(this.id);
            this.stateSetting = latestStateDO.getStateSetting();
            this.isCanceled = latestStateDO.getIsCanceled();
        }
        return this;
    }

    public Settings getConfigSetting() {
        if (this.stateSetting != null) {
            return settingsBuilder().loadFromSource(this.stateSetting).build();
        } else {
            return null;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getStateSetting() {
        return stateSetting;
    }

    public void setStateSetting(String stateSetting) {
        this.stateSetting = stateSetting;
    }

    public Boolean getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(Boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

}
