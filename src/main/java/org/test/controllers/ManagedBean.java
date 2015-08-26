package org.test.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Akis
 */
@Named(value = "managedBean")
@RequestScoped
public class ManagedBean {

    @Inject
    FacesContext context;

    private UploadedFile file;

    public ManagedBean() {
    }

    public void handleFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException {

        String extension = FilenameUtils.getExtension(event.getFile().getFileName());
        String path = "/home/jbeef/Desktop";
        String newName = "akisfile";

        InputStream input = event.getFile().getInputstream();
        OutputStream output = new FileOutputStream(new File(path, newName));

        try {
            IOUtils.copy(input, output);
        } catch (IOException ex) {
            Logger.getLogger(ManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The entry saved. "));

    }

//-------------------------------- Getters/Setters ---------------------------------------    
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
