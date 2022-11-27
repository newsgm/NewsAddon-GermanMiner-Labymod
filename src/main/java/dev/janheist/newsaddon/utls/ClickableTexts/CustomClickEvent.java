package dev.janheist.newsaddon.utls.ClickableTexts;

import com.google.common.hash.Hashing;
import dev.janheist.newsaddon.main.NewsAddon;
import dev.janheist.newsaddon.main.SocketConnection;
import net.minecraft.util.text.event.ClickEvent;
import sun.reflect.ConstructorAccessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomClickEvent extends ClickEvent {
    private final CustomAction action;
    private final String value;
    private static List<String> hashList = new ArrayList<>();

    public CustomClickEvent(CustomAction theAction, String theValue)
    {
        super(theAction.getAction(), theValue);
        this.action = theAction;
        this.value = theValue;
    }

    @Override
    public ClickEvent.Action getAction()
    {
        return this.action.getAction();
    }

    public enum CustomAction {
        CUSTOM_OPEN_URL(Action.OPEN_URL.getCanonicalName(), Action.OPEN_URL.shouldAllowInChat()),
        CUSTOM_OPEN_FILE(Action.OPEN_FILE.getCanonicalName(), Action.OPEN_FILE.shouldAllowInChat()),
        CUSTOM_RUN_COMMAND(Action.RUN_COMMAND.getCanonicalName(), Action.RUN_COMMAND.shouldAllowInChat()),
        CUSTOM_SUGGEST_COMMAND(Action.SUGGEST_COMMAND.getCanonicalName(), Action.SUGGEST_COMMAND.shouldAllowInChat()),
        CUSTOM_CHANGE_PAGE(Action.CHANGE_PAGE.getCanonicalName(), Action.CHANGE_PAGE.shouldAllowInChat()),
        NEWSFUNK("newsfunk", true);


        private final boolean allowedInChat;
        /** The canonical name used to refer to this action. */
        private final String canonicalName;

        private CustomAction(String canonicalNameIn, boolean allowedInChatIn)
        {
            this.canonicalName = canonicalNameIn;
            this.allowedInChat = allowedInChatIn;
        }

        private String getCanonicalName()
        {
            return this.canonicalName;
        }

        public ClickEvent.Action getAction() {
            if (this.ordinal() <= 4)
                return ClickEvent.Action.getValueByCanonicalName(this.canonicalName);

            // Check if ClickEvent.Actions.values() has member
            for (ClickEvent.Action action : ClickEvent.Action.values()) {
                if (action.getCanonicalName().equals(this.canonicalName))
                    return action;
            }

            try {
                Class<Action> actionClass = Action.class;

                Constructor<?> constructor = actionClass.getDeclaredConstructors()[0];
                constructor.setAccessible(true);

                Field constructorAccessorField = Constructor.class.getDeclaredField("constructorAccessor");
                constructorAccessorField.setAccessible(true);

                ConstructorAccessor ca = (ConstructorAccessor) constructorAccessorField.get(constructor);
                if (ca == null) {
                    Method acquireConstructorAccessorMethod = Constructor.class.getDeclaredMethod("acquireConstructorAccessor");
                    acquireConstructorAccessorMethod.setAccessible(true);
                    ca = (ConstructorAccessor) acquireConstructorAccessorMethod.invoke(constructor);
                }

                return (Action) ca.newInstance(new Object[]{this.getCanonicalName(), this.ordinal(), this.getCanonicalName(), this.allowedInChat});
            } catch (Exception e) {
                e.printStackTrace();
            }

            return Action.SUGGEST_COMMAND;
        }
    }

    public static String createMessage(String msg) {
        String hash = "hash-" + Hashing.sha1().hashString(LocalDateTime.now().toString() + NewsAddon.getRandom().nextInt(1000), StandardCharsets.UTF_8).toString();
        hashList.add(hash);
        return hash + " " + msg;
    }

    public static void execute(ClickEvent event) {
        try {
            if (hashList == null)
                hashList = new ArrayList<>();

            if (!event.getAction().getCanonicalName().contains("news"))
                return;

            String[] split = event.getValue().split(" ", 2);
            String hash = split[0];
            if (!hashList.contains(hash))
                return;

            String message = split[1];
            hashList.remove(hash);
            SocketConnection.socket.s(message);
        } catch (NullPointerException ignored) { }
    }

}