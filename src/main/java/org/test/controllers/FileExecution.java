package org.test.controllers;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.portlet.PortletRequest;

/**
 *
 * @author jbeef
 */
@Named(value = "fileExecution")
@SessionScoped
public class FileExecution implements Serializable {

    private static final long serialVersionUID = 6859018791478624574L;

    @Inject
    transient FacesContext context;

    private PortletRequest request;
    private ThemeDisplay themeDisplay;
    private User user;
    private List<DLFileEntry> files;
    private DLFolder folder;
    private DLFileEntry selectedFile;

    public FileExecution() {
    }

    @PostConstruct
    private void init() {
        request = (PortletRequest) context.getExternalContext().getRequest();
        themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        user = themeDisplay.getUser();
    }

//-------------------------------- Getters/Setters ---------------------------------------    
    public List<DLFileEntry> getFiles() {

        try {
            folder = DLFolderLocalServiceUtil.fetchFolder(themeDisplay.getLayout().getGroupId(), 0, user.getEmailAddress());

            if (folder != null) {
                files = DLFileEntryLocalServiceUtil.getFileEntries(themeDisplay.getLayout().getGroupId(), folder.getFolderId());
            } else {
                files = new ArrayList<>();
            }
        } catch (SystemException ex) {
            Logger.getLogger(FileExecution.class.getName()).log(Level.SEVERE, null, ex);
        }

        return files;
    }

    public DLFileEntry getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(DLFileEntry selectedFile) {
        this.selectedFile = selectedFile;
        System.out.println(selectedFile.getTitle());
    }

}
