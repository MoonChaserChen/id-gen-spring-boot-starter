package ink.akira.idgen.snowflake;

import java.util.Date;

/**
 * Created by Allen on 2018/6/1.
 */
public class SnowFlakeIdGenerator implements IdGenerator {
    //   id format  =>
    //   timestamp |idType     | workerId | sequence
    //   41        |10         |  4       | 8
    public static final int TIME_STAMP_BITS = 41;
    public static final int ID_TYPE_BITS = 10;
    public static final int WORKER_ID_BITS = 4;
    public static final int SEQUENCE_BITS = 8;
    public static final long TIME_EPOCH = 1262275200000L; //Fri Jan 01 00:00:00 CST 2010

    private volatile long lastTimestamp = -1L;
    private volatile long sequence = 0L;

    private int defaultIdType;
    private int workerId;

    public SnowFlakeIdGenerator(int idType, int workerId) {
        if (!(idType >= 0 && idType < 1 << ID_TYPE_BITS)) {
            throw new RuntimeException(String.format("IdType.id invalid, it should be in [0,%s)", 1 << ID_TYPE_BITS));
        }
        if (!(workerId >= 0 && workerId < 1 << WORKER_ID_BITS)) {
            throw new RuntimeException(String.format("workerId invalid, it should be in [0,%s)", 1 << WORKER_ID_BITS));
        }
        this.defaultIdType = idType;
        this.workerId = workerId;
    }

    public int getDefaultIdType() {
        return defaultIdType;
    }

    public void setDefaultIdType(int defaultIdType) {
        this.defaultIdType = defaultIdType;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    @Override
    public synchronized long generate() {
        return generate(this.defaultIdType);
    }

    @Override
    public long generate(int idType) {
        long timestamp = System.currentTimeMillis();
        // TODO 时钟回调处理是否可优化
        if (timestamp < lastTimestamp) {
            throw new InvalidSystemClockException("Clock moved backwards.  Refusing to generate id for " + (
                    lastTimestamp - timestamp) + " milliseconds.");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) / (1 << SEQUENCE_BITS);
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return ((timestamp - TIME_EPOCH) << (ID_TYPE_BITS + WORKER_ID_BITS + SEQUENCE_BITS)) |
                (idType << (WORKER_ID_BITS + SEQUENCE_BITS)) |
                (workerId << SEQUENCE_BITS) |
                sequence;
    }

    @Override
    public IdInfo getIdInfo(long id) {
        IdInfo idInfo = new IdInfo();
        idInfo.setIdType(getIdTypeById(id));
        idInfo.setCreateTime(getDateById(id));
        idInfo.setSequence(getSequenceById(id));
        idInfo.setWorkerId(getWorkerIdById(id));
        return idInfo;
    }

    public Date getDateById(long id) {
        long timDiff = id >> ID_TYPE_BITS + WORKER_ID_BITS + SEQUENCE_BITS;
        return new Date(TIME_EPOCH + timDiff);
    }

    public int getIdTypeById(long id) {
        return (int) ((id >> WORKER_ID_BITS + SEQUENCE_BITS) & ((1 << ID_TYPE_BITS) - 1));
    }

    public int getWorkerIdById(long id) {
        return (int) ((id >> SEQUENCE_BITS) & ((1 << WORKER_ID_BITS) - 1));
    }

    public int getSequenceById(long id) {
        return (int) (id & (1 << SEQUENCE_BITS) - 1);
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
