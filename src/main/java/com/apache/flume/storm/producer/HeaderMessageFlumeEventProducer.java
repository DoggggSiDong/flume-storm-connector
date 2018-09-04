package com.apache.flume.storm.producer;

import com.apache.flume.storm.common.Constants;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HeaderMessageFlumeEventProducer implements FlumeEventProducer {
    private static final Logger LOG = LoggerFactory.getLogger(HeaderMessageFlumeEventProducer.class);

    private static final String CHARSET = "UTF-8";

    public static String getCharset() {
        return CHARSET;
    }

    @SuppressWarnings("unchecked")
    public Event toEvent(Tuple input) throws Exception {

        Map<String, String> headers = null;
        Object headerObj = null;
        String messageStr = null;

        /*If the number of parameters are more than one, they may have headers and Message
         *message is a mandatory field
         */
        if (input.size() >= 1) {
            headerObj = input.getValueByField(Constants.HEADERS);

            if(null != headerObj){
                headers = (Map<String, String>) headerObj;
            }else{
                headers = new HashMap<String, String>();
            }

            messageStr = input.getStringByField(Constants.MESSAGE);

            if(null == messageStr){
                throw new IllegalStateException("invalid data format of touple. No message found " + input.size());
            }
        } else {
            throw new IllegalStateException("Wrong number of touple fields. expected 1 or more. But found " + input.size());
        }

        try {
            LOG.debug("HeaderMessageFlumeEventProducer:" + messageStr);

            Event event = EventBuilder.withBody(messageStr.getBytes(), headers);
            return event;
        } catch (Exception e) {
            throw e;
        }
    }
}
