import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SimpleProducer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleProducer.class);

    private static final String TOPIC_NAME = "test";

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    public static void main(String[] args) {
        Properties configs = new Properties();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(configs);

        String messageValue = "testMessage";
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, "pangoyo", "23");

        // send는 즉각적인 전송을 의미하지 않는다. 일정 기간 버퍼링 후 일괄 전송된다.
        producer.send(record, new ProducerCallback());
        logger.info("{}", record);
        producer.flush(); // 버퍼에 존재하는 레코드를 브로커로 전송한다.
        producer.close(); // 애플리케이션 종료 전 close()를 통해 리소스를 안전하게 정리한다.
    }
}
