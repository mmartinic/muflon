package org.mmartinic.muflon.parser;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mmartinic.muflon.model.Episode;
import org.mmartinic.muflon.model.Show;

public class MyEpisodesHTTPClient implements Serializable {

	private static final long serialVersionUID = -6884602411077537947L;

	private static final String MY_EPISODES_COOKIE = "";
	private static final String MY_EPISODES_URL = "http://www.myepisodes.com/";
	private static final String LOGIN_LOCATION = "login.php";
	private static final String MANAGE_SHOWS_LOCATION = "shows.php?type=manage";
	private static final String EPISODES_BY_SHOW_LOCATION = "views.php?type=epsbyshow&showid=";

	private final DefaultHttpClient httpClient;

	public MyEpisodesHTTPClient() {
		httpClient = new DefaultHttpClient();
	}

	/**
	 * Returns all my shows
	 * 
	 * @return all my shows
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<Show> getAllShows() throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(MY_EPISODES_URL + MANAGE_SHOWS_LOCATION);
		httpGet.setHeader("Cookie", MY_EPISODES_COOKIE);
		System.out.println("Executing request: " + httpGet.getURI());
		HttpResponse httpResponse = httpClient.execute(httpGet);
		String responseBody = getResponseAsString(httpResponse);
		httpGet.abort();

		List<Show> shows = new ArrayList<Show>();
		Pattern p = Pattern.compile("<option value=\"(.+?)\">(.+?)</option>");
		Matcher m = p.matcher(responseBody);
		while (m.find()) {
			Show show = new Show();
			show.setId(Long.parseLong(StringUtils.trim(m.group(1))));
			show.setName(StringUtils.trim(m.group(2)));
			shows.add(show);
		}
		return shows;
	}

	/**
	 * Returns all episodes for provided show ID
	 * 
	 * @param showId
	 *            show ID
	 * @return all episodes for given show ID
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<Episode> getAllEpisodesForShow(Long showId) throws ClientProtocolException, IOException, ParseException {
		HttpGet httpGet = new HttpGet(MY_EPISODES_URL + EPISODES_BY_SHOW_LOCATION + showId.toString());
		httpGet.setHeader("Cookie", MY_EPISODES_COOKIE);
		System.out.println("Executing request: " + httpGet.getURI());
		HttpResponse httpResponse = httpClient.execute(httpGet);
		String responseBody = getResponseAsString(httpResponse);
		httpGet.abort();

		List<Episode> episodes = new ArrayList<Episode>();
		// replace all new lines with spaces
		responseBody = responseBody.replaceAll("\\s+", " ");
		Pattern p = Pattern
				.compile("<td class=\"date\"><a href=.*?>(.+?)</a></td> <td class=\"showname\">(.+?)</td> <td class=\"longnumber\">(.+?)</td> <td class=\"epname\"><a href.*?>(.+?)</a></td>");
		Matcher m = p.matcher(responseBody);
		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MMM-yyyy");
		while (m.find()) {
			String airDate = StringUtils.trim(m.group(1));
			String showName = StringUtils.trim(m.group(2));
			String seasonAndEpisode = StringUtils.trim(m.group(3));
			Integer season = Integer.parseInt(seasonAndEpisode.split("x")[0]);
			Integer episodeNumber = Integer.parseInt(seasonAndEpisode.split("x")[1]);
			String episodeName = StringUtils.trim(m.group(4));

			Show show = new Show();
			show.setId(showId);
			show.setName(showName);
			Episode episode = new Episode();
			episode.getEpisodeKey().setShow(show);
			episode.getEpisodeKey().setSeasonNumber(season);
			episode.getEpisodeKey().setEpisodeNumber(episodeNumber);
			episode.setAirDate(dateTimeFormatter.parseLocalDate(airDate));
			episode.setName(episodeName);
			episodes.add(episode);
		}
		return episodes;
	}

	/**
	 * Logs in user with provided username and password and returns cookie
	 * 
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @return cookie
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String login(String username, String password) throws ClientProtocolException, IOException {
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		formParams.add(new BasicNameValuePair("username", username));
		formParams.add(new BasicNameValuePair("password", password));
		formParams.add(new BasicNameValuePair("action", "Login"));

		HttpContext localContext = new BasicHttpContext();
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
		HttpPost httpPost = new HttpPost(MY_EPISODES_URL + LOGIN_LOCATION);
		httpPost.setEntity(entity);
		System.out.println("Executing request: " + httpPost.getURI());
		HttpResponse httpResponse = httpClient.execute(httpPost, localContext);
		httpPost.abort();

		Header[] headers = httpResponse.getHeaders("Set-Cookie");
		String cookie = "";
		for (Header header : headers) {
			cookie += header.getName() + "=" + header.getValue() + "; ";
		}
		return cookie;
	}

	/**
	 * When HttpClient instance is no longer needed, shut down the connection manager to ensure immediate deallocation of all system resources
	 */
	public void shutdown() {
		httpClient.getConnectionManager().shutdown();
	}

	private String getResponseAsString(HttpResponse httpResponse) throws ClientProtocolException, IOException {
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		return responseHandler.handleResponse(httpResponse);
	}
}
