package ink.akira.idgen.snowflake;

import org.junit.Test;

public class SnowFlakeIdGeneratorTest {
    @Test
    public void test(){
        SnowFlakeIdGenerator snowFlakeIdGenerator = new SnowFlakeIdGenerator(0, 0);
        System.out.println(snowFlakeIdGenerator.generate());
    }
}
