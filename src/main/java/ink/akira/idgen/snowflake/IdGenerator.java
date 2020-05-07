package ink.akira.idgen.snowflake;

/**
 * Created by Allen on 2018/6/1.
 */
public interface IdGenerator {
    long generate();
    long generate(int idType);
    IdInfo getIdInfo(long id);
}
