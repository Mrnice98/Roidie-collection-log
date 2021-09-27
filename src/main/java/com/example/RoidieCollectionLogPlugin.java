package com.example;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import javax.sound.sampled.*;
import java.io.IOException;

@Slf4j
@PluginDescriptor
(
	name = "Roidie Collection Log",
	description = "Roidie says- Oh there it is, thanks. when you get a collection log addition",
	tags = {"Roidie", "Collection log","there it is", "sounds", "announce"}
)

public class RoidieCollectionLogPlugin extends Plugin
{
	@Inject
	private Client client;

	Clip clip;
	AudioInputStream audioInputStream;

	@Override
	protected void startUp() throws Exception
	{

	}

	@Override
	protected void shutDown() throws Exception
	{
		clip.stop();
		clip.close();
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage) throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		String message = chatMessage.getMessage();
		if(message.equals("!There it is") || ((chatMessage.getType().equals(ChatMessageType.GAMEMESSAGE)) && message.contains("New item added to your collection log:")))
		{
			playAudio();
		}
	}


	public void playAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		if(clip != null && clip.isActive())
		{
			return;
		}
		// create AudioInputStream object
		audioInputStream = AudioSystem.getAudioInputStream(RoidieCollectionLogPlugin.class.getResourceAsStream("/Roidie.wav"));
		// create clip reference
		clip = AudioSystem.getClip();
		// open audioInputStream to the clip
		clip.open(audioInputStream);

		clip.start();
	}

}
