package com.nadikarim.submision1.data.model.stories

data class StoriesResponse(
    val error: Boolean,
    val listStory: ArrayList<Story>,
    val message: String
)