package com.ghj.rest.model;

import java.io.Serializable;
import java.util.Date;

/**
 * group_message_to_user
 * @author 
 */
public class GroupMessageToUser implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer groupMessageId;

    private Boolean sate;

    private Date sendTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupMessageId() {
        return groupMessageId;
    }

    public void setGroupMessageId(Integer groupMessageId) {
        this.groupMessageId = groupMessageId;
    }

    public Boolean getSate() {
        return sate;
    }

    public void setSate(Boolean sate) {
        this.sate = sate;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        GroupMessageToUser other = (GroupMessageToUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getGroupMessageId() == null ? other.getGroupMessageId() == null : this.getGroupMessageId().equals(other.getGroupMessageId()))
            && (this.getSate() == null ? other.getSate() == null : this.getSate().equals(other.getSate()))
            && (this.getSendTime() == null ? other.getSendTime() == null : this.getSendTime().equals(other.getSendTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getGroupMessageId() == null) ? 0 : getGroupMessageId().hashCode());
        result = prime * result + ((getSate() == null) ? 0 : getSate().hashCode());
        result = prime * result + ((getSendTime() == null) ? 0 : getSendTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", groupMessageId=").append(groupMessageId);
        sb.append(", sate=").append(sate);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}