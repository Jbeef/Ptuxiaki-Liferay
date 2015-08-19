package org.test.controllers;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Akis
 */
@Named(value = "managedBean")
@RequestScoped
public class ManagedBean {

    @Inject
    FacesContext context;

    private String name;

    public ManagedBean() {
    }

    @PostConstruct
    private void init() {
        name = "Jbeef";
    }

    public void submit() {
        System.out.println("##  Name: " + name);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Warning!", "The entry saved. " + name));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
