package com.ajayb.jerseychunked;


import org.glassfish.jersey.client.ChunkedInput;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

/**
 * Created by ajayb on 1/4/17.
 */
public class AsyncChunkedServiceClient {

	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();

		ChunkedInput<String> response = client
				.target("http://localhost:8080/server/api/async")
				.request()
				.get(new GenericType<ChunkedInput<String>>() {
				});


		String chunk;
		while ((chunk = response.read()) != null) {
			System.out.println("chunk: " + chunk);
		}
	}
}
