package org.test.controllers;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Akis
 */
@Named(value = "managedBean")
@RequestScoped
public class ManagedBean {

    private String name;

    public ManagedBean() {
    }

    @PostConstruct
    private void init() {
        name = "Jbeef";
    }

    public void submit() {
        System.out.println("##  Name: " + name);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Warning!", "The entry saved. " + name));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
