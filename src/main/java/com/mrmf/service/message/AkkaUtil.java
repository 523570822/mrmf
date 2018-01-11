package com.mrmf.service.message;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.actor.Terminated;
import scala.concurrent.Future;

public class AkkaUtil {
	private static ActorSystem system;

	public static ActorSystem defaultActorSystem() {
		if (system == null) {
			Config conf = ConfigFactory.load();
			system = ActorSystem.create("mrmf", conf);
		}
		return system;
	}

	public static void shutdown() {
		if (system != null) {
			Future<Terminated> termFuture = system.terminate();
			while (!termFuture.isCompleted()) {
			}
		}
	}

}
