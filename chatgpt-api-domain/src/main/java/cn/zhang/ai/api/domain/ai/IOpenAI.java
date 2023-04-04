package cn.zhang.ai.api.domain.ai;

import java.io.IOException;

public interface IOpenAI {

    String doChatGpt(String openAiKey,String question) throws IOException;

}
