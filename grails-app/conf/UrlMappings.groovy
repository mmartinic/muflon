class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?"{ constraints { // apply constraints here
            } }

        "/"(controller:"episode")
        "500"(view:'/error')
    }
}
