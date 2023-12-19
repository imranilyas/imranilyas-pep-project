package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
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
    private void userRegistrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = socialMediaService.userRegistration(account);

        if(newAccount==null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(newAccount));
            ctx.status(200);
        }

    }

    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = socialMediaService.login(account);

        if(loginAccount==null) {
            ctx.status(401);
        } else {
            ctx.json(mapper.writeValueAsString(loginAccount));
        }
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
       ctx.json(socialMediaService.getAllMessages()); 
    }

    private void getMessageByIdHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = socialMediaService.getMessageById(message_id);
        if(message != null) {
            // ctx.json(socialMediaService.getMessageById(message_id));
            ctx.json(message);
        }
    }

    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Message message = mapper.readValue(ctx.body(), Message.class);

        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = socialMediaService.deleteMessageById(message_id);

        System.out.println(deletedMessage);
        if(deletedMessage != null) {
            ctx.json(mapper.writeValueAsString(deletedMessage));
        }
    
    }
    
    private void updateMessageByIdHandler(Context ctx) {

    }
    
    private void getMessagesByAccountIdHandler(Context ctx) {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(socialMediaService.getMessagesByAccountId(account_id));
    }



}