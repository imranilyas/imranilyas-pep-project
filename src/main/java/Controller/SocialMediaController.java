package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Message;
import Service.SocialMediaService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    SocialMediaService socialMediaService;

    public SocialMediaController() {
        socialMediaService = new SocialMediaService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::userRegistrationHandler);
        app.post("login", this::loginHandler);
        app.post("messages", this::postMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageByIdHandler);
        app.put("messages/{message_id}", this::updateMessageByIdHandler);
        app.get("accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    //! TODO: check readme
    private void userRegistrationHandler(Context ctx) {

    }

    private void loginHandler(Context ctx) {

    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = socialMediaService.createMessage(message);

        if(newMessage==null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(newMessage));
        }
    }

    private void getAllMessagesHandler(Context ctx) {

    }

    private void getMessageByIdHandler(Context ctx) {

    }

    private void deleteMessageByIdHandler(Context ctx) {

    }
    
    private void updateMessageByIdHandler(Context ctx) {

    }
    
    private void getMessagesByAccountIdHandler(Context ctx) {

    }



}