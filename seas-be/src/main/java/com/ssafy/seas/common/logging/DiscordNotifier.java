package com.ssafy.seas.common.logging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;

@Component
public class DiscordNotifier {

	public final int MAX_LENGTH = 2000;
	private final WebhookClient webhookClient;

	public DiscordNotifier(@Value("${logging.discord.webhook-url}") String webhookUrl) {

		this.webhookClient = new WebhookClientBuilder(webhookUrl).build();
	}

	public void notify(String message) {
		webhookClient.send(message);
	}
}
