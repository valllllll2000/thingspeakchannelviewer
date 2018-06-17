package com.vaxapp.thingspeakviewer.domain


data class DomainResponse (
        val channel: DomainChannel,
        val feeds: List<DomainFeed>
)

data class DomainChannel (
        val field1:  String,
        val field2:  String,
        val description: String,
        val updated_at: String
)

data class DomainFeed(
        val field1: String,
        val field2: String
)