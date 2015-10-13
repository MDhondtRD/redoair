package com.realdolmen.air.controllers;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by JDOAX80 on 13/10/2015.
 */
@Named
@RequestScoped
public class ChooseTripConversationController {
    @Inject
    private Conversation conversation;

    @Inject
    private ChooseDestinationController chooseDestinationController;

    public String startConversation() {
        conversation.begin();
        return chooseDestinationController.startBooking();//Start van de flow
    }

    public String endConversation() {
        conversation.end();
        return chooseDestinationController.endBooking();//eind van de flow(booking succeeded)

    }

}
