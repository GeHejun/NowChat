package com.ghj.rest.model;

import java.io.Serializable;
import java.util.Date;

/**
 * system_message
 * @author 
 */
public class SystemMessage implements Serializable {
    private Long id;

    /**
     * 来源
     */
    private Integer fromUserId;

    /**
     * 发送目的
     */
    private Integer toUserId;

    private Date sendTime;

    private Boolean status;

    private String content;

    private Integer toGroupId;

    /**
     * 消息类型
     */
    private Integer messageTypeId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getToGroupId() {
        return toGroupId;
    }

    public void setToGroupId(Integer toGroupId) {
        this.toGroupId = toGroupId;
    }

    public Integer getMessageTypeId() {
        return messageTypeId;
    }

    public void setMessageTypeId(Integer messageTypeId) {
        this.messageTypeId = messageTypeId;
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
        SystemMessage other = (SystemMessage) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFromUserId() == null ? other.getFromUserId() == null : this.getFromUserId().equals(other.getFromUserId()))
            && (this.getToUserId() == null ? other.getToUserId() == null : this.getToUserId().equals(other.getToUserId()))
            && (this.getSendTime() == null ? other.getSendTime() == null : this.getSendTime().equals(other.getSendTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getToGroupId() == null ? other.getToGroupId() == null : this.getToGroupId().equals(other.getToGroupId()))
            && (this.getMessageTypeId() == null ? other.getMessageTypeId() == null : this.getMessageTypeId().equals(other.getMessageTypeId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFromUserId() == null) ? 0 : getFromUserId().hashCode());
        result = prime * result + ((getToUserId() == null) ? 0 : getToUserId().hashCode());
        result = prime * result + ((getSendTime() == null) ? 0 : getSendTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getToGroupId() == null) ? 0 : getToGroupId().hashCode());
        result = prime * result + ((getMessageTypeId() == null) ? 0 : getMessageTypeId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fromUserId=").append(fromUserId);
        sb.append(", toUserId=").append(toUserId);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", status=").append(status);
        sb.append(", content=").append(content);
        sb.append(", toGroupId=").append(toGroupId);
        sb.append(", messageTypeId=").append(messageTypeId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}