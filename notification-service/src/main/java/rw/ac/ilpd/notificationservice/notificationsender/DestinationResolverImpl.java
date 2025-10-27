package rw.ac.ilpd.notificationservice.notificationsender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationDestinationResponse;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DestinationResolverImpl implements DestinationResolver
{
    @Override
    public List<String> resolveDestination(List<NotificationDestinationResponse> destinations)
    {
        if (destinations == null || destinations.isEmpty())
        {
            log.debug("No destinations found");
            return null;
        }

        // this will hold a list of users to get the notification
        List<String> result = new ArrayList<>();

        for (NotificationDestinationResponse destination : destinations)
        {
            if (destination.getIntakeId() != null)
            {
                // TODO: CHECK IF THE INTAKE EXISTS. IF IT DOESN'T NO NEED TO MOVE ON!

                // Since the intake exists, we can check if the notification is specific
                // to a component or a module.
                String componentId = findComponentIfAny(destinations);
                String moduleId = findModuleIfAny(destinations);

                if (componentId != null)
                {
                    // TODO: CHECK IF THE COMPONENT EXISTS IF IT DOESN'T NO NEED TO CONTINUE

                    // TODO: FETCH ALL USERS WHO BELONG TO THE INTAKE AND COMPONENT

                    // Add to the list of users who belong to the intake and component
                    result.add(componentId);
                }

                if (moduleId != null)
                {
                    // TODO: CHECK IF THE MODULE EXISTS IF IT DOESN'T NO NEED TO CONTINUE

                    // TODO: FETCH ALL USERS WHO BELONG TO THE INTAKE AND MODULE

                    // Add to the list of users who belong to the intake and module
                    result.add(moduleId);
                }

                result.add(destination.getIntakeId().toString());
            }
            else if (destination.getRoleId() != null)
            {
                // TODO: CHECK IF THE ROLE EXISTS. IF NOT, NO NEED TO CONTINUE

                // TODO: FIND ALL USERS ASSOCIATED WITH THAT ROLE

                // add the users to the result list
            }
            else if (destination.getUserId() != null)
            {
                // TODO: CHECK IF THE USER EXISTS. IF NOT, NO NEED TO CONTINUE

                // add the user id to the result list
            }
        }
        return result;
    }

    private String findModuleIfAny(List<NotificationDestinationResponse> destinations)
    {
        return destinations.stream().map(destination ->
                destination.getModuleId().toString()).findFirst().orElse(null);
    }

    private String findComponentIfAny(List<NotificationDestinationResponse> destinations)
    {
        return destinations.stream().map(destination ->
                destination.getComponentId().toString()).findFirst().orElse(null);
    }
}
