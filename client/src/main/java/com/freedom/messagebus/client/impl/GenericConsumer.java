package com.freedom.messagebus.client.impl;

import com.freedom.messagebus.business.model.Node;
import com.freedom.messagebus.client.*;
import com.freedom.messagebus.client.core.config.ConfigManager;
import com.freedom.messagebus.client.message.model.Message;
import com.freedom.messagebus.client.model.MessageCarryType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.List;

/**
 * a generic consumer which implements IConsumer
 */
public class GenericConsumer extends AbstractMessageCarryer implements IConsumer {

    private static final Log logger = LogFactory.getLog(GenericConsumer.class);

    public GenericConsumer() {
        super(MessageCarryType.CONSUME);
    }

    /**
     * consume message
     *
     * @param queueName       the name of queue that the consumer want to connect
     *                        generally, is the app-name
     * @param receiveListener the message receiver
     * @return a consumer's closer used to let the app control the consumer
     * (actually, the message receiver is needed to be controlled)
     * @throws IOException
     */

    @Override
    public IReceiverCloser consume(String queueName,
                                   IMessageReceiveListener receiveListener) throws IOException {
        final MessageContext ctx = new MessageContext();
        ctx.setCarryType(MessageCarryType.CONSUME);
        ctx.setAppId(this.context.getAppId());

        ctx.setSourceNode(ConfigManager.getInstance().getAppIdQueueMap().get(this.context.getAppId()));
        Node node = ConfigManager.getInstance().getQueueNodeMap().get(queueName);
        ctx.setTargetNode(node);

        ctx.setPool(this.context.getPool());
        ctx.setConnection(this.context.getConnection());
        ctx.setListener(receiveListener);
        ctx.setSync(false);

        //launch pipeline
        carry(ctx);

        return new IReceiverCloser() {
            @Override
            public void close() {
                synchronized (this) {
                    if (ctx.getReceiveEventLoop().isAlive()) {
                        ctx.getReceiveEventLoop().shutdown();
                    }
                }
            }
        };
    }

    /**
     * consume with sync-mode, when received messages' num equal the given num
     * or timeout the consume will return
     *
     * @param queueName the name of queue that the consumer want to connect
     * @param num       the num which the client expected (the result's num may not be equals to the given num)
     * @return received message
     */

    @Override
    public List<Message> consume(String queueName, int num) {
        final MessageContext ctx = new MessageContext();
        ctx.setCarryType(MessageCarryType.CONSUME);
        ctx.setAppId(super.context.getAppId());

        ctx.setSourceNode(ConfigManager.getInstance().getAppIdQueueMap().get(this.context.getAppId()));
        Node node = ConfigManager.getInstance().getQueueNodeMap().get(queueName);
        ctx.setTargetNode(node);

        ctx.setPool(this.context.getPool());
        ctx.setConnection(this.context.getConnection());
        ctx.setConsumeMsgNum(num);
        ctx.setSync(true);

        carry(ctx);

        return ctx.getConsumeMsgs();
    }
}
