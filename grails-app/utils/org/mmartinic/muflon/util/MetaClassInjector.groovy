package org.mmartinic.muflon.util

import org.joda.time.LocalDate;

class MetaClassInjector {

    static def init() {
        LocalDate.metaClass.minus = {int days ->
            return delegate.minusDays(days)
        }

        LocalDate.metaClass.plus = {int days ->
            return delegate.plusDays(days)
        }
    }
}
