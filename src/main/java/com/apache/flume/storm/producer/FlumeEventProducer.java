package com.apache.flume.storm.producer;

import org.apache.storm.tuple.Tuple;
import org.apache.flume.Event;
import java.io.Serializable;

public interface FlumeEventProducer extends Serializable {
    public Event toEvent(Tuple input) throws Exception;
}
