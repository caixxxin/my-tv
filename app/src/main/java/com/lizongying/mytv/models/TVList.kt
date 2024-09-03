package com.lizongying.mytv.models

import com.lizongying.mytv.R

object TVList {
    val list: Map<String, List<TV>> by lazy {
        setup()
    }

    private fun setup(): Map<String, List<TV>> {
        var list = mapOf(
            "港澳台" to listOf(
                TV(
                    0,
                    "凤凰卫视资讯台",
                    "",
                    listOf(),
                    "港澳台",
                    "http://c1.fengshows-cdn.com/a/2021_22/79dcc3a9da358a3.png",
                    "7c96b084-60e1-40a9-89c5-682b994fb680",
                    "",
                    ProgramType.F,
                    false,
                    mustToken = false,
                    0.7F
                ),
                TV(
                    0,
                    "凤凰卫视中文台",
                    "",
                    listOf(),
                    "港澳台",
                    "http://c1.fengshows-cdn.com/a/2021_22/ede3d9e09be28e5.png",
                    "f7f48462-9b13-485b-8101-7b54716411ec",
                    "",
                    ProgramType.F,
                    false,
                    mustToken = false,
                    0.7F
                ),
                TV(
                    0,
                    "凤凰卫视香港台",
                    "",
                    listOf(),
                    "港澳台",
                    "http://c1.fengshows-cdn.com/a/2021_23/325d941090bee17.png",
                    "15e02d92-1698-416c-af2f-3e9a872b4d78",
                    "",
                    ProgramType.F,
                    false,
                    mustToken = false,
                    0.7F
                ),
            ),
        )

        val array = arrayOf("央视", "地方")
//        list = list.filterKeys { it in array }

        val listNew = mutableMapOf<String, List<TV>>()
        var id = 0
        list.forEach { (k, v) ->
            val group = mutableListOf<TV>()
            v.forEach { v1 ->
                if (!v1.mustToken) {
                    v1.id = id
                    v1.needToken = false
                    id++
                    group.add(v1)
                }
            }
            if (group.size > 0) {
                listNew[k] = group
            }
        }
        return listNew
    }
}