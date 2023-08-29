package com.micrometer.demo;

import java.time.Duration;

import io.micrometer.common.lang.Nullable;
import io.micrometer.elastic.ElasticConfig;

public class ElasticConfiguration {

	public ElasticConfig configure() {
		return new ElasticConfig() {
			@Override
			@Nullable
			public String get(String k) {
				return null;
			}

			@Override
			public String host() {
				return "https://localhost:9200";
			}

			@Override
			public String userName() {
				return "elastic";
			}

			@Override
			public String password() {
				return "cbs*Kc-MPYk*OP7D-a0t";
			}

			@Override
			public String index() {
				return "micrometer-database-v1-606";
			}

			@Override
			public boolean autoCreateIndex() {
				return false;
			}

			@Override
			public Duration step() {
				return Duration.ofSeconds(30);
			}
		};
	}
}
