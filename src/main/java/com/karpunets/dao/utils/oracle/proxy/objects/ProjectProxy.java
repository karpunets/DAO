package com.karpunets.dao.utils.oracle.proxy.objects;

import com.karpunets.dao.utils.oracle.proxy.ProxyFactory;
import com.karpunets.pojo.Project;
import com.karpunets.pojo.Sprint;

import java.util.Set;

/**
 * @author Karpunets
 * @since 24.03.2017
 */

public class ProjectProxy extends Project implements ProxyFactory.Proxy {

    private Set<Long> sprints;

    @Override
    public Set<Sprint> getSprints() {
        if (super.getSprints() == null) {
            super.setSprints(ProxyFactory.get(Sprint.class, this.sprints));
        }
        return super.getSprints();
    }

}
