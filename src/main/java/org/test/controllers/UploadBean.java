package org.test.controllers;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.portlet.PortletRequest;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jbeef
 */
@Named(value = "uploadBean")
@RequestScoped
public class UploadBean {

    @Inject
    FacesContext context;

    private UploadedFile file;
    private PortletRequest request;
    private ThemeDisplay themeDisplay;
    private DLFolder folder;

    public UploadBean() {
    }

    private void initPersonalFolder() {

        request = (PortletRequest) context.getExternalContext().getRequest();
        themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();

        try {
            folder = DLFolderLocalServiceUtil.fetchFolder(themeDisplay.getLayout().getGroupId(), 0, user.getEmailAddress());

            if (folder == null) {
                folder = DLFolderLocalServiceUtil.addFolder(themeDisplay.getUserId(), themeDisplay.getLayout().getGroupId(), 10181, false, 0,
                        user.getEmailAddress(), "Lisp files folder", false, new ServiceContext());
            }
        } catch (PortalException | SystemException ex) {
            Logger.getLogger(UploadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        initPersonalFolder();
        String extension = FilenameUtils.getExtension(event.getFile().getFileName());        
        String newName = String.valueOf(System.currentTimeMillis());

        InputStream input = event.getFile().getInputstream();

        try {
            DLAppLocalServiceUtil.addFileEntry(themeDisplay.getUserId(), 10181, folder.getFolderId(), null, extension,
                    newName, "This is a Lisp file", "changeLog", input, event.getFile().getSize(), new ServiceContext());

        } catch (PortalException | SystemException ex) {
            Logger.getLogger(UploadBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            IOUtils.closeQuietly(input);
        }

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Lisp file uploaded "));
    }

//-------------------------------- Getters/Setters ---------------------------------------    
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
