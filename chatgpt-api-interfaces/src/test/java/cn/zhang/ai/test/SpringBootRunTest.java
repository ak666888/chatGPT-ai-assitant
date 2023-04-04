package cn.zhang.ai.test;

import cn.zhang.ai.api.domain.ai.IOpenAI;
import cn.zhang.ai.api.domain.zsxq.IzsxqApi;
import cn.zhang.ai.api.domain.zsxq.model.aggregates.UnAnsweredQuestionAggregates;
import cn.zhang.ai.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

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

    @Test
    public void test_zsxqApi() throws IOException {
        logger.info("拉取问题数据，groupId: {} ", groupId.toString());
        UnAnsweredQuestionAggregates unAnsweredQuestionAggregates = zsxqApi.queryUnAnsweredQuestionTopicId(groupId, cookie);
        logger.info("测试结果：{}", JSON.toJSONString(unAnsweredQuestionAggregates));

        List<Topics> topics = unAnsweredQuestionAggregates.getResp_Data().getTopics();
        for (Topics topic : topics) {
            String topicId = topic.getTopic_id();
            String text = topic.getQuestion().getText();
            logger.info("topicId: {} text: {}",topicId, text);


             zsxqApi.anwser(groupId, cookie, topicId, text,  false);
        }
    }

    @Test
    public void test_openai() throws IOException {
        String response = openAI.doChatGpt(openAiKey, "新海诚导演的著名电影");

        logger.info("测试结果：{}", response);

    }
}
