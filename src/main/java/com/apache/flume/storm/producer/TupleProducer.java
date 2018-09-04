package com.apache.flume.storm.producer;

import org.apache.flume.Event;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Values;

import java.io.Serializable;

public interface TupleProducer extends Serializable {
    Values toTuple(Event event) throws Exception;
    void declareOutputFields(OutputFieldsDeclarer declarer);
}
