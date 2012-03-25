package org.mmartinic.muflon.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.joda.time.LocalDate;

@Entity
@Table(name = "episodes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Episode implements Serializable {

    private static final long serialVersionUID = -1000975580285590848L;

    private EpisodeKey episodeKey = new EpisodeKey();
    private String name;
    private LocalDate airDate;

    public Episode() {
        super();
    }

    public Episode(EpisodeKey episodeKey, String name, LocalDate airDate) {
        super();
        this.episodeKey = episodeKey;
        this.name = name;
        this.airDate = airDate;
    }

    @EmbeddedId
    public EpisodeKey getEpisodeKey() {
        return episodeKey;
    }

    public void setEpisodeKey(EpisodeKey episodeKey) {
        this.episodeKey = episodeKey;
    }

    @Column(name = "name", length = 255, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "air_date", nullable = false)
    public LocalDate getAirDate() {
        return airDate;
    }

    public void setAirDate(LocalDate airDate) {
        this.airDate = airDate;
    }

    @Transient
    public Show getShow() {
        return episodeKey.getShow();
    }

    public void setShow(Show show) {
        episodeKey.setShow(show);
    }

    @Transient
    public Integer getSeasonNumber() {
        return episodeKey.getSeasonNumber();
    }

    public void setSeasonNumber(Integer seasonNumber) {
        episodeKey.setSeasonNumber(seasonNumber);
    }

    @Transient
    public Integer getEpisodeNumber() {
        return episodeKey.getEpisodeNumber();
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        episodeKey.setEpisodeNumber(episodeNumber);
    }

    @Embeddable
    public static class EpisodeKey implements Serializable {

        private static final long serialVersionUID = -599289068633445871L;

        private Show show;
        private Integer seasonNumber;
        private Integer episodeNumber;

        public EpisodeKey() {
            super();
        }

        public EpisodeKey(Show show, Integer seasonNumber, Integer episodeNumber) {
            super();
            this.show = show;
            this.seasonNumber = seasonNumber;
            this.episodeNumber = episodeNumber;
        }

        @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
        @JoinColumn(name = "show_id", referencedColumnName = "id", nullable = false)
        public Show getShow() {
            return show;
        }

        public void setShow(Show show) {
            this.show = show;
        }

        @Column(name = "season_number", nullable = false)
        public Integer getSeasonNumber() {
            return seasonNumber;
        }

        public void setSeasonNumber(Integer seasonNumber) {
            this.seasonNumber = seasonNumber;
        }

        @Column(name = "episode_number", nullable = false)
        public Integer getEpisodeNumber() {
            return episodeNumber;
        }

        public void setEpisodeNumber(Integer episodeNumber) {
            this.episodeNumber = episodeNumber;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((episodeNumber == null) ? 0 : episodeNumber.hashCode());
            result = prime * result + ((seasonNumber == null) ? 0 : seasonNumber.hashCode());
            result = prime * result + ((show == null) ? 0 : show.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            EpisodeKey other = (EpisodeKey) obj;
            if (episodeNumber == null) {
                if (other.episodeNumber != null)
                    return false;
            } else if (!episodeNumber.equals(other.episodeNumber))
                return false;
            if (seasonNumber == null) {
                if (other.seasonNumber != null)
                    return false;
            } else if (!seasonNumber.equals(other.seasonNumber))
                return false;
            if (show == null) {
                if (other.show != null)
                    return false;
            } else if (!show.equals(other.show))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "EpisodeKey [show=" + show + ", seasonNumber=" + seasonNumber + ", episodeNumber=" + episodeNumber + "]";
        }

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((airDate == null) ? 0 : airDate.hashCode());
        result = prime * result + ((episodeKey == null) ? 0 : episodeKey.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Episode other = (Episode) obj;
        if (airDate == null) {
            if (other.airDate != null)
                return false;
        } else if (!airDate.equals(other.airDate))
            return false;
        if (episodeKey == null) {
            if (other.episodeKey != null)
                return false;
        } else if (!episodeKey.equals(other.episodeKey))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Episode [episodeKey=" + episodeKey + ", name=" + name + ", airDate=" + airDate + "]";
    }
}
