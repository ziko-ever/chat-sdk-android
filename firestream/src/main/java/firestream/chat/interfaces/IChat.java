package firestream.chat.interfaces;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import firestream.chat.chat.User;
import firestream.chat.events.UserEvent;
import firestream.chat.firebase.rx.MultiQueueSubject;
import firestream.chat.message.Message;
import firestream.chat.message.Sendable;
import firestream.chat.namespace.FireStreamUser;
import firestream.chat.types.DeliveryReceiptType;
import firestream.chat.types.RoleType;
import firestream.chat.types.TypingStateType;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * This interface is just provided for clarity
 */
public interface IChat extends IAbstractChat {

    /**
     * The unique chat id
     * @return id string
     */
    String getId();

    /**
     * Remove the user from the chat's roster. It may be preferable to call
     * @see IFireStream#leaveChat(IChat)
     * @return completion
     */
    Completable leave();

    /**
     * Get the chat name
     * @return name
     */
    String getName();

    /**
     * Set the chat name.
     * @param name new name
     * @return completion
     */
    Completable setName(String name);

    /**
     * Get the group image url
     * @return image url
     */
    String getImageURL();

    /**
     * Set the chat image url
     * @param url of group image
     * @return completion
     */
    Completable setImageURL(String url);

    /**
     * Get any custom data associated from the chat
     * @return custom data
     */
    HashMap<String, Object> getCustomData();

    /**
     * Associate custom data from the chat - you can add your own
     * data to a chat - topic, extra links etc...
     * @param data custom data to write
     * @return completion
     */
    Completable setCustomData(final HashMap<String, Object> data);

    /**
     * Get a list of members of the chat
     * @return list of users
     */
    ArrayList<User> getUsers();

    /**
     * Get a list of users from the FireStreamUser namespace
     * These are exactly the same users but may be useful if
     * your project already has a User class to avoid a clash
     * @return list of FireStreamUsers
     */
    ArrayList<FireStreamUser> getFireStreamUsers();

    /**
     * Add users to a chat
     * @param sendInvite should an invitation message be sent?
     * @param users users to add, set the role of each user using user.setRoleType()
     * @return completion
     */
    Completable addUsers(Boolean sendInvite, User... users);

    /**
     * @see IChat#addUsers(Boolean, User...)
     */
    Completable addUsers(Boolean sendInvite, List<User> users);

    /**
     * @see IChat#addUsers(Boolean, User...)
     */
    Completable addUser(Boolean sendInvite, User user);

    /**
     * Update users in chat
     * @param users users to update
     * @return completion
     */
    Completable updateUsers(User... users);

    /**
     * @see IChat#updateUsers(User...)
     */
    Completable updateUsers(List<User> users);

    /**
     * @see IChat#updateUsers(User...)
     */
    Completable updateUser(User user);

    /**
     * Remove users from a chat
     * @param users users to remove
     * @return completion
     */
    Completable removeUsers(User... users);

    /**
     * @see IChat#removeUsers(User...)
     */
    Completable removeUsers(List<User> users);

    /**
     * @see IChat#removeUsers(User...)
     */
    Completable removeUser(User user);

    /**
     * Send an invite message to users
     * @param users to invite
     * @return completion
     */
    Completable inviteUsers(List<User> users);

    /**
     * Set the role of a user
     * @param user to update
     * @param roleType new role type
     * @return completion
     */
    Completable setRole(User user, RoleType roleType);

    /**
     * Get the users for a particular role
     * @param roleType to find
     * @return list of users
     */
    List<User> getUsersForRoleType(RoleType roleType);

    /**
     * Get the role for a user
     * @param theUser to who's role to find
     * @return role
     */
    RoleType getRoleTypeForUser(User theUser);

    /**
     * Get an observable which is called when the name changes
     * @return observable
     */
    Observable<String> getNameChangeEvents();

    /**
     * Get an observable which is called when the chat image changes
     * @return observable
     */
    Observable<String> getImageURLChangeEvents();

    /**
     * Get an observable which is called when the custom data associated from the
     * chat is updated
     * @return observable
     */
    Observable<HashMap<String, Object>> getCustomDataChangedEvents();

    /**
     * Get an observable which is called when the a user is added, removed or updated
     * @return observable
     */
    MultiQueueSubject<UserEvent> getUserEvents();

    /**
     * Send a custom message
     * @param body custom message data
     * @param newId message's new ID before sending
     * @return completion
     */
    Completable sendMessageWithBody(HashMap<String, Object> body, @Nullable Consumer<String> newId);

    /**
     * Send a custom message
     * @param body custom message data
     * @return completion
     */
    Completable sendMessageWithBody(HashMap<String, Object> body);

    /**
     * Send a text message
     * @param text message text
     * @param newId message's new ID before sending
     * @return completion
     */
    Completable sendMessageWithText(String text, @Nullable Consumer<String> newId);

    /**
     * Send a text message
     * @param text message text
     * @return completion
     */
    Completable sendMessageWithText(String text);

    /**
     * Send a typing indicator message
     * @param type typing state
     * @param newId message's new ID before sending
     * @return completion
     */
    Completable sendTypingIndicator(TypingStateType type, @Nullable Consumer<String> newId);

    /**
     * Send a typing indicator message. An indicator should be sent when starting and stopping typing
     * @param type typing state
     * @return completion
     */
    Completable sendTypingIndicator(TypingStateType type);

    /**
     * Send a delivery receipt to a user. If delivery receipts are enabled,
     * a 'received' status will be returned as soon as a message is delivered
     * and then you can then manually send a 'read' status when the user
     * actually reads the message
     * @param type receipt type
     * @param newId message's new ID before sending
     * @return completion
     */
    Completable sendDeliveryReceipt(DeliveryReceiptType type, String messageId, @Nullable Consumer<String> newId);

    /**
     * Send a delivery receipt to a user. If delivery receipts are enabled,
     * a 'received' status will be returned as soon as a message is delivered
     * and then you can then manually send a 'read' status when the user
     * actually reads the message
     * @param type receipt type
     * @return completion
     */
    Completable sendDeliveryReceipt(DeliveryReceiptType type, String messageId);

    /**
     * Send a custom sendable
     * @param sendable to send
     * @param newId message's new ID before sending
     * @return completion
     */
    Completable send(Sendable sendable, @Nullable Consumer<String> newId);

    /**
     * Send a custom sendable
     * @param sendable to send
     * @return completion
     */
    Completable send(Sendable sendable);

    /**
     * Mark a message as received
     * @param message to mark as received
     * @return completion
     */
    Completable markReceived(Message message);

    /**
     * Mark a message as read
     * @param message to mark as read
     * @return completion
     */
    Completable markRead(Message message);

}
