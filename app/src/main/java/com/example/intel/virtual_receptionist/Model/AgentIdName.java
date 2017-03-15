package com.example.intel.virtual_receptionist.Model;

/**
 * Created by intel on 3/6/2017.
 */
public class AgentIdName {
    private String id;
    private String agent_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public AgentIdName(String id, String agent_name) {
        this.id = id;
        this.agent_name = agent_name;
    }
}
