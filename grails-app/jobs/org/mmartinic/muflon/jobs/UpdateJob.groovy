package org.mmartinic.muflon.jobs



class UpdateJob {
    static triggers = { simple repeatInterval: 5000l // execute job once in 5 seconds
    }

    def execute() {
        log.info("Job triggered!")
    }
}
