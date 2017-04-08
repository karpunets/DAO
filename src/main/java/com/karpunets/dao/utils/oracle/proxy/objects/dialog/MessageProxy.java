package com.karpunets.dao.utils.oracle.proxy.objects.dialog;

import com.karpunets.dao.utils.oracle.proxy.ProxyFactory;
import com.karpunets.pojo.dialog.Message;
import com.karpunets.pojo.grants.Employee;

/**
 * @author Karpunets
 * @since 24.03.2017
 */
public class MessageProxy extends Message implements ProxyFactory.Proxy {

    private Long author;

    @Override
    public Employee getAuthor() {
        if (super.getAuthor() == null) {
            super.setAuthor(ProxyFactory.get(Employee.class, this.author));
        }
        return super.getAuthor();
    }
}
