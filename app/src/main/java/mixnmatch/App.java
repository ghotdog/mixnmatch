package mixnmatch;


import mixnmatch.services.Youtube;
import mixnmatch.services.Spotify;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class App {


	public static void main(String[] args) throws IOException, GeneralSecurityException, ParseException, SpotifyWebApiException {

		//import playlists from spotify to youtube music
		Spotify.exportPlaylists();

		Youtube yt = new Youtube();
		yt.importPlaylists();

	}

}