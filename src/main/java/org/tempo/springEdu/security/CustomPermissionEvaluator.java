package org.tempo.springEdu.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.tempo.springEdu.entity.Project;
import org.tempo.springEdu.entity.User;
import org.tempo.springEdu.exception.ObjectNotFoundException;
import org.tempo.springEdu.service.ProjectService;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ProjectService projectService;

    @Override
    public boolean hasPermission(
            Authentication authentication, Object targetDomainObject, Object permission) {
        var projectId = targetDomainObject.toString();
        var userId = ((User) authentication.getPrincipal()).getId();

        logger.debug("hasPermission test");
        logger.debug(
                String.format("\nuserName: %s; userId: %s; projectId: %s",
                        authentication.getName(),
                        userId,
                        projectId));

        Project project;
        try {
            project = projectService.findById(projectId);
        }
        catch(ObjectNotFoundException e) {
            logger.info(e.getMessage());
            return false;
        }

        if (userId.equals(project.getOwnerId())) {
            logger.info(String.format("%s is passed.",
                    permission));
            return true;
        } else {
            logger.info(String.format("%s; User: %s is not owner %s",
                    permission, authentication.getName(), project.getName()));
            return false;
        }

    }

    @Override
    public boolean hasPermission(
            Authentication authentication, Serializable targetId,
            String targetType, Object permission) {
        logger.debug("hasPermission test2");
        return false;
    }

}
