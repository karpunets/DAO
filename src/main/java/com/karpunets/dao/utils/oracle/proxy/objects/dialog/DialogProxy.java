package com.karpunets.dao.utils.oracle.proxy.objects.dialog;

import com.karpunets.dao.utils.oracle.proxy.ProxyFactory;
import com.karpunets.pojo.dialog.Dialog;
import com.karpunets.pojo.dialog.Message;

import java.util.Set;

/**
 * @author Karpunets
 * @since 24.03.2017
 */
public class DialogProxy extends Dialog implements ProxyFactory.Proxy {

    private Set<Long> messages;

    @Override
    public Set<Message> getMessages() {
        if (super.getMessages() == null) {
            super.setMessages(ProxyFactory.get(Message.class, this.messages));
        }
        return super.getMessages();
    }
}
