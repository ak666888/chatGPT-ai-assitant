package cn.zhang.ai.api.application.job;

import cn.zhang.ai.api.domain.ai.IOpenAI;
import cn.zhang.ai.api.domain.zsxq.IzsxqApi;
import cn.zhang.ai.api.domain.zsxq.model.aggregates.UnAnsweredQuestionAggregates;
import cn.zhang.ai.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * 问答任务
 */

@EnableScheduling
@Configuration
public class ChatbotSchedule {

    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

    @Value("${chatai.groupId}")
    private String groupId;
    @Value("${chatai.cookie}")
    private String cookie;

    @Value("${chatai.openAiKey}")
    private String openAiKey;

    @Resource
    private IzsxqApi zsxqApi;

    @Resource
    private IOpenAI openAI;

    //cron表达式代表轮询时间
    @Scheduled(cron = "0/20 * * * * 2")
    public void run(){
        try{

            if (new Random().nextBoolean()) {
                logger.info("随机停滞~~");
                return;
            }

            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour > 22 || hour < 7) {
                logger.info("下班力");
                return;
            }

            UnAnsweredQuestionAggregates unAnsweredQuestionAggregates = zsxqApi.queryUnAnsweredQuestionTopicId(groupId, cookie);
            logger.info("测试结果：{}", JSON.toJSONString(unAnsweredQuestionAggregates));
            List<Topics> topics = unAnsweredQuestionAggregates.getResp_Data().getTopics();

            if(null == topics || topics.isEmpty()) {
                logger.info("本次检索未查询到，待会回答~~");
                return;
            }

            Topics topic = topics.get(0);
            String answer = openAI.doChatGpt(openAiKey, topic.getQuestion().getText().trim());

            boolean status = zsxqApi.anwser(groupId, cookie, topic.getTopic_id(), answer, true);
            logger.info("编号：{} 问题: {} 状态: {} 回答: {}",topic.getTopic_id(),topic.getQuestion().getText(),status, answer);



        }catch (Exception e){
            logger.info("自动回答问题异常", e);
        }
    }

}
