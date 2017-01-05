package com.ajayb.jerseychunked;

import org.glassfish.jersey.server.ChunkedOutput;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by ajayb on 1/4/17.
 */
@Path("/async")
public class AsyncChunkedService {

	@GET
	public Response getChunkedResponse() {
		ChunkedOutput<String> output = new ChunkedOutput<>(String.class);

		new Thread(() -> {
			try {
				String chunk = "Message";

				for (int i = 0; i < 10; i++) {
					output.write(chunk + "#" + i);
					Thread.sleep(10);
				}
			} catch (IOException | InterruptedException e) {
				// IOException thrown when writing the
				// chunks of response: should be handled
			} finally {
				try {
					output.close();
				} catch (IOException e) {
					//not logging the exception
				}
				// simplified: IOException thrown from
				// this close() should be handled here...
			}
		}).start();


		// the output will be probably returned even before
		// a first chunk is written by the new thread
		return Response.ok().entity(output).build();
	}

}
