package org.test.controllers;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

/**
 *
 * @author Akis
 */
@RequestScoped
public class FacesContextProducer {

    @Produces
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
