package com.apache.flume.storm.producer;

import com.apache.flume.storm.common.Constants;
import org.apache.flume.Event;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeaderTextMessageTupleProducer implements TupleProducer {

    public Values toTuple(Event event) throws Exception {

        String msgID = null;
        Map<String,String> evHeaders = event.getHeaders();

        if(null != evHeaders){
            msgID = evHeaders.get(Constants.MESSAGE_ID);
        }else {
            evHeaders = new HashMap<String,String>();
        }

        //generates and sets MessageID, if MessageId header doesn't exists. Update the event header too.
        if(null == msgID) {
            UUID randMsgID = UUID.randomUUID();
            msgID = randMsgID.toString();
            event.getHeaders().put(Constants.MESSAGE_ID, msgID);
        }
        String msg = new String(event.getBody());
        return new Values(msgID, event.getHeaders(),msg);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(Constants.MESSAGE_ID,Constants.HEADERS,Constants.MESSAGE));
    }

}
