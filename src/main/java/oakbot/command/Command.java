package oakbot.command;

import java.util.Collection;
import java.util.Collections;

import oakbot.bot.BotContext;
import oakbot.bot.ChatCommand;
import oakbot.bot.ChatResponse;

/**
 * A chat bot command.
 * @author Michael Angstadt
 */
public interface Command {
	/**
	 * Gets the command's name. This is what is used to invoke the command.
	 * @return the name
	 */
	String name();

	/**
	 * Gets other names that can be used to invoke the command.
	 * @return the command name aliases
	 */
	default Collection<String> aliases() {
		return Collections.emptyList();
	}

	/**
	 * Gets the command's description. This should be a short, one sentence
	 * description. SO markdown should not be used.
	 * @return the description
	 */
	String description();

	/**
	 * Gets the command's help text. This is shown when this command is queried
	 * with the "help" command. SO markdown should not be used.
	 * @param trigger the command trigger (for including examples in the
	 * description)
	 * @return the help text
	 */
	String helpText(String trigger);

	/**
	 * Called when a user invokes this command.
	 * @param chatCommand the command that the user has sent
	 * @param context the bot context
	 * @return the response or null not to send a response
	 */
	ChatResponse onMessage(ChatCommand chatCommand, BotContext context);
}
