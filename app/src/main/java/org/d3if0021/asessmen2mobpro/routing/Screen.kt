package org.d3if3062.mobpro1.routing

sealed class Screen (val route: String){

    data object Home: Screen("mainScreen")

    data object DetailScreen: Screen("detailScreen/{id}"){
        fun withId(id: Long): String{
            val routeWithId = route.replace("{id}", id.toString())
            println("Id included in route: $routeWithId")
            return routeWithId
        }
    }
    data object AddData: Screen("addData")

}