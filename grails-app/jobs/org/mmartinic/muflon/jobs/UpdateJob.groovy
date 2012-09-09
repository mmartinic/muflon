package org.mmartinic.muflon.jobs

import org.mmartinic.muflon.User
import org.mmartinic.muflon.parser.Updater


class UpdateJob {

    Updater updater

    static triggers = {
        //every day at 04:00
        cron name: 'UpdateJobTrigger', cronExpression: "0 0 4 * * ?"
    }

    def execute() {
        log.info("Update job triggered")
        def users = User.list(max:1)
        if (users) {
            User user = users[0]
            if (user.isLoggedInToMyEpisodes()) {
                updater.partialUpdate(user.myEpisodesUsername, user.myEpisodesPwdmd5, user.myEpisodesCookie)
                log.info("Update job finished")
            }
            else {
                log.warn("User not logged in to My episodes")
            }
        }
        else {
            log.warn("User not found in DB")
        }
    }
}
