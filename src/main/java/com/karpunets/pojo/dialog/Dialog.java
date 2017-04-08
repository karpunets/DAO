package com.karpunets.pojo.dialog;

import com.karpunets.dao.utils.xml.SetAdapter;
import com.karpunets.pojo.CompanyObject;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Karpunets
 * @since 10.03.2017
 */
public class Dialog extends CompanyObject {

    @XmlJavaTypeAdapter(SetAdapter.class)
    private Set<Message> messages;

    public Set<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        if (getMessages() == null) {
            setMessages(new LinkedHashSet<>());
        }
        getMessages().add(message);
    }

    public void removeMessage(Message message) {
        if (getMessages() == null) {
            getMessages().remove(message);
        }
    }
}
