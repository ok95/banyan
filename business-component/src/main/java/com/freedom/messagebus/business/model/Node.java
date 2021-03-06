package com.freedom.messagebus.business.model;

import java.io.Serializable;

/**
 * representation a node of the topology
 */
public class Node implements Serializable, Comparable<Node> {

    private int     nodeId;
    private String  name;
    private String  value;
    private int     parentId;
    private short   type;         //0: exchange 1: queue
    private String  routerType;
    private String  routingKey;
    private short   level;
    private boolean available;
    private String  appId;
    private boolean inner;

    public Node() {
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getRouterType() {
        return routerType;
    }

    public void setRouterType(String routerType) {
        this.routerType = routerType;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public boolean isInner() {
        return inner;
    }

    public void setInner(boolean inner) {
        this.inner = inner;
    }

    @Override
    public int compareTo(Node o) {
        if (this.nodeId == o.getNodeId())
            return 0;
        else if (this.nodeId < o.getNodeId())
            return -1;
        else
            return 1;
    }

    @Override
    public String toString() {
        return "Node{" +
            "nodeId=" + nodeId +
            ", name='" + name + '\'' +
            ", value='" + value + '\'' +
            ", parentId=" + parentId +
            ", type=" + type +
            ", routerType='" + routerType + '\'' +
            ", routingKey='" + routingKey + '\'' +
            ", level=" + level +
            ", available=" + available +
            ", appId='" + appId + '\'' +
            ", inner=" + inner +
            '}';
    }
}
