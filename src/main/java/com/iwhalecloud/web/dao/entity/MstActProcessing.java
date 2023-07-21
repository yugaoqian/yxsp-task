package com.iwhalecloud.web.dao.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mst_act_pending_processing")
public class MstActProcessing {
    private Integer id;

    @Column(name = "act_id")
    private String actId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "crt_date")
    private Date crtDate;

    private Short state;

    @Column(name = "handle_times")
    private Short handleTimes;

    @Column(name = "upd_date")
    private Date updDate;

    @Column(name = "error_msg")
    private String errorMsg;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return act_id
     */
    public String getActId() {
        return actId;
    }

    /**
     * @param actId
     */
    public void setActId(String actId) {
        this.actId = actId;
    }

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return crt_date
     */
    public Date getCrtDate() {
        return crtDate;
    }

    /**
     * @param crtDate
     */
    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    /**
     * @return state
     */
    public Short getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Short state) {
        this.state = state;
    }

    /**
     * @return handle_times
     */
    public Short getHandleTimes() {
        return handleTimes;
    }

    /**
     * @param handleTimes
     */
    public void setHandleTimes(Short handleTimes) {
        this.handleTimes = handleTimes;
    }

    /**
     * @return upd_date
     */
    public Date getUpdDate() {
        return updDate;
    }

    /**
     * @param updDate
     */
    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    /**
     * @return error_msg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}