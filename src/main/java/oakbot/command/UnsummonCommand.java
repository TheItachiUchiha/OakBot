package oakbot.command;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import oakbot.bot.BotContext;
import oakbot.bot.ChatCommand;
import oakbot.bot.ChatResponse;
import oakbot.util.ChatBuilder;

/**
 * Makes the bot leave a room.
 * @author Michael Angstadt
 */
public class UnsummonCommand implements Command {
	@Override
	public String name() {
		return "unsummon";
	}

	@Override
	public List<String> aliases() {
		return Arrays.asList("leave");
	}

	@Override
	public String description() {
		return "Makes the bot leave a room.";
	}

	@Override
	public String helpText(String trigger) {
		return description();
	}

	@Override
	public ChatResponse onMessage(ChatCommand chatCommand, BotContext context) {
		String content = chatCommand.getContent().trim();

		int roomToLeave;
		boolean inRoomToLeave;
		if (content.isEmpty()) {
			roomToLeave = chatCommand.getMessage().getRoomId();
			inRoomToLeave = true;
		} else {
			try {
				roomToLeave = Integer.parseInt(content);
			} catch (NumberFormatException e) {
				return reply("Please specify the room ID.", chatCommand);
			}
			inRoomToLeave = roomToLeave == chatCommand.getMessage().getRoomId();
		}

		if (!context.getCurrentRooms().contains(roomToLeave)) {
			return reply("I'm not in that room... -_-", chatCommand);
		}

		if (context.getHomeRooms().contains(roomToLeave)) {
			if (inRoomToLeave) {
				return reply("This is one of my home rooms, I can't leave it.", chatCommand);
			}
			return reply("That's one of my home rooms, I can't leave it.", chatCommand);
		}

		context.leaveRoom(roomToLeave);

		String reply;
		if (inRoomToLeave) {
			reply = random("*poof*", "Hasta la vista, baby.", "Bye.");
		} else {
			reply = random("They smelled funny anyway.", "Good riddance.", "Less for me to worry about.");
		}

		return reply(reply, chatCommand);
	}

	private static ChatResponse reply(String content, ChatCommand message) {
		//@formatter:off
		return new ChatResponse(new ChatBuilder()
			.reply(message)
			.append(content)
		);
		//@formatter:on
	}

	private static String random(String... choices) {
		Random random = new Random();
		int index = random.nextInt(choices.length);
		return choices[index];
	}
}
