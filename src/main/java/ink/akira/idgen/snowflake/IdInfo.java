package ink.akira.idgen.snowflake;

import java.util.Date;

/**
 * Created by Allen on 2018/6/4.
 */
public class IdInfo {
    private Date createTime;
    private int idType;
    private int workerId;
    private int sequence;

    public IdInfo() {
    }

    public IdInfo(Date createTime, int idType, int workerId, int sequence) {
        this.createTime = createTime;
        this.idType = idType;
        this.workerId = workerId;
        this.sequence = sequence;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
