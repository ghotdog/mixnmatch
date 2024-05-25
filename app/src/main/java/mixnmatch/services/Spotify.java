package mixnmatch.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Arrays;

public class Spotify {

	private static final String CLIENT_SECRETS = "/spotify_client_secret.json"; // Path to your client secrets file
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8081/Callback");
	
	private static GoogleClientSecrets getResources() throws IOException {
		InputStream in = Spotify.class.getResourceAsStream(CLIENT_SECRETS);
		assert in != null;
		// using google api to load spotify secrets is cursed but pretty
		return GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
	}
	
	public static SpotifyApi authorize() {
		try {

			GoogleClientSecrets clientSecrets = getResources();

			SpotifyApi spotifyApi = new SpotifyApi.Builder()
					.setClientId(clientSecrets.getDetails().getClientId())
					.setClientSecret(clientSecrets.getDetails().getClientSecret())
					.setRedirectUri(redirectUri)
					.build();

			ClientCredentialsRequest clientCredentialsRequest = spotifyApi
					.clientCredentials()
					.build();

			ClientCredentials clientCredentials = clientCredentialsRequest.execute();

			// Set access token for further "spotifyApi" object usage
			spotifyApi.setAccessToken(clientCredentials.getAccessToken());
			
			return spotifyApi;

		} catch (IOException | SpotifyWebApiException | ParseException e) {
			System.out.println("Error: " + e.getMessage());
		}
        return null;
    }
	
	public static void exportPlaylists() throws IOException, ParseException, SpotifyWebApiException {
		
		GoogleClientSecrets clientSecrets = getResources();
		SpotifyApi spotifyApi = authorize();
        assert spotifyApi != null;
        GetListOfUsersPlaylistsRequest getListOfUsersPlaylistsRequest = spotifyApi
				.getListOfUsersPlaylists(clientSecrets
				.getDetails()
				.get("user_id").toString())
				.build();

		final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfUsersPlaylistsRequest.execute();

		Arrays.stream(playlistSimplifiedPaging.getItems())
				.map(PlaylistSimplified::getName)
				.forEach(System.out::println);
		
		//export playlists with contents to a file
		
	}

	public void importPlaylists() {
	}
}
