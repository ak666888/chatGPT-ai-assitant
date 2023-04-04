package cn.zhang.ai.api.domain.zsxq;

import cn.zhang.ai.api.domain.zsxq.model.aggregates.UnAnsweredQuestionAggregates;

import java.io.IOException;

/**
 * API接口
 */
public interface IzsxqApi {

    UnAnsweredQuestionAggregates queryUnAnsweredQuestionTopicId(String groupId, String cookie) throws IOException;

    boolean anwser(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException;
}
