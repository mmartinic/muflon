package org.mmartinic.muflon.parser;

import java.io.Serializable;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mmartinic.muflon.model.Episode;
import org.mmartinic.muflon.model.Show;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RSSParser implements Serializable {

    private static final long serialVersionUID = 3823571360336453906L;

    private static final Log log = LogFactory.getLog(RSSParser.class);

    private static final String MY_EPISODES_RSS_LINK = "http://www.myepisodes.com/rss.php?feed=mylist&uid=%s&pwdmd5=%s";

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MMM-yyyy");

    private final String myEpisodesUsername;
    private final String pwdmd5;

    public RSSParser(String myEpisodesUsername, String pwdmd5) {
        this.myEpisodesUsername = myEpisodesUsername;
        this.pwdmd5 = pwdmd5;
    }

    /**
     * Retrieves and parses all episodes from RSS feed
     * 
     * @return all episodes from RSS feed
     */
    public List<Episode> getEpisodesFromRSSFeed() {
        List<Episode> episodes = new ArrayList<Episode>();
        try {
            URL feedUrl = new URL(String.format(MY_EPISODES_RSS_LINK, myEpisodesUsername, pwdmd5));
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            for (Object object : feed.getEntries()) {
                Episode episode = parseEntry((SyndEntryImpl) object);
                if (episode != null) {
                    episodes.add(episode);
                }
            }
        }
        catch (Exception e) {
            log.error(e, e);
        }
        return episodes;
    }

    /**
     * Parses one RSS feed entry
     * 
     * @param syndEntryImpl
     *            entry
     * @return episode object parsed from entry
     * @throws ParseException
     */
    private Episode parseEntry(SyndEntryImpl syndEntryImpl) throws ParseException {

        String uri = syndEntryImpl.getUri();
        String[] items = uri.split("-");

        String title = syndEntryImpl.getTitle();
        Pattern p = Pattern.compile("\\[(.+?)\\]\\[(.+?)\\]\\[(.+?)\\]\\[(.+?)\\]");
        Matcher m = p.matcher(title);
        boolean found = m.find();

        Episode episode;
        if (found) {
            episode = new Episode();
            Show show = new Show();
            show.setId(Long.parseLong(items[0]));
            show.setName(StringUtils.trim(m.group(1)));
            episode.getEpisodeKey().setShow(show);
            episode.getEpisodeKey().setSeasonNumber(Integer.parseInt(items[1]));
            episode.getEpisodeKey().setEpisodeNumber(Integer.parseInt(items[2]));
            episode.setName(StringUtils.trim(m.group(3)));
            episode.setAirDate(dateTimeFormatter.parseLocalDate(StringUtils.trim(m.group(4))));

            return episode;
        }
        else {
            episode = null;
        }
        return episode;

    }
}
