package cn.edu.tongji.musicRoom.quartzServer;

import cn.edu.tongji.musicRoom.mapper.ChatRecordMapper;
import cn.edu.tongji.musicRoom.model.ChatRecord;
import cn.edu.tongji.musicRoom.util.RedisOperatingUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public class RedisToMysqlTask extends QuartzJobBean {
    @Resource
    private RedisOperatingUtil redisOperatingUtil;
    @Resource
    private ChatRecordMapper chatRecordMapper;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        long chatRecordListSize = redisOperatingUtil.listLen("chatRecord");

        long resultCount = 0;
        for (int i = 0; i < chatRecordListSize; ++i) {
            ChatRecord chatRecord = (ChatRecord) redisOperatingUtil.listPopLeftKey("chatRecord");
            try {
                chatRecordMapper.insertChatRecord(chatRecord);
                resultCount++;
            } catch (Exception e) {
                log.error("Failed to insert chat record: {}", chatRecord, e);
            }
        }
        log.info("{} chat records were inserted successfully", resultCount);
    }
}
